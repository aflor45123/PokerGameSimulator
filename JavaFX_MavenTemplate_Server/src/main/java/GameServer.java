import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {

    private final int port;
    private final ServerEventListener listener;

    private final ExecutorService acceptExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService clientExecutor = Executors.newCachedThreadPool();

    private final Map<UUID, ClientHandler> clients = new ConcurrentHashMap<>();

    private volatile boolean running = false;
    private ServerSocket serverSocket;

    public GameServer(int port, ServerEventListener listener) {
        this.port = port;
        this.listener = listener;
    }

    /**
     * Starts the server on a background thread.
     */
    public void startAsync() {
        if (running) {
            return;
        }
        running = true;

        acceptExecutor.submit(() -> {
            try {
                serverSocket = new ServerSocket(port);

                // Just to show in the log that the server really started
                listener.onEvent(ServerEvent.info("Server started on port " + port));

                while (running) {
                    Socket socket = serverSocket.accept();
                    UUID id = UUID.randomUUID();
                    String clientName = "Client-" + id.toString().substring(0, 5);

                    ClientHandler handler = new ClientHandler(id, clientName, socket);
                    clients.put(id, handler);

                    listener.onEvent(ServerEvent.join(id, clientName, clients.size()));
                    clientExecutor.submit(handler);
                }
            } catch (BindException be) {
                listener.onEvent(ServerEvent.error("Port " + port + " already in use."));
            } catch (IOException ioe) {
                // If running is false, this is probably because we called stopAsync()
                if (running) {
                    listener.onEvent(ServerEvent.error("Server I/O error: " + ioe.getMessage()));
                }
            } finally {
                running = false;
                closeServerSocket();
            }
        });
    }

    /**
     * Stops the server and all client handlers.
     */
    public void stopAsync() {
        running = false;

        // Closing the server socket will cause accept() to throw and exit the loop
        closeServerSocket();

        // Disconnect all clients
        for (ClientHandler handler : clients.values()) {
            handler.shutdown();
        }
        clients.clear();

        // Shut down executors
        acceptExecutor.shutdownNow();
        clientExecutor.shutdownNow();

        listener.onEvent(ServerEvent.info("Server stopped."));
    }

    private void closeServerSocket() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Simple per-client handler. For now it just echoes String messages,
     * which works with your current ClientConnection class.
     */
    private class ClientHandler implements Runnable {

        private final UUID id;
        private final String name;
        private final Socket socket;

        private ObjectOutputStream out;
        private ObjectInputStream in;
        private volatile boolean alive = true;

        ClientHandler(UUID id, String name, Socket socket) {
            this.id = id;
            this.name = name;
            this.socket = socket;
        }

        @Override
        public void run() {
            try (Socket s = socket) {
                // IMPORTANT: same order as the client
                out = new ObjectOutputStream(s.getOutputStream());
                out.flush();
                in = new ObjectInputStream(s.getInputStream());

                // Simple greeting so you can see something on the client
                send("WELCOME " + name + "!");
                
                while (alive && !s.isClosed()) {
                    Object obj;
                    try {
                        obj = in.readObject();
                    } catch (EOFException eof) {
                        break; // client closed connection
                    }

                    if (obj == null) {
                        break;
                    }

                    String msg = obj.toString();
                    listener.onEvent(ServerEvent.info("Received from " + name + ": " + msg));

                    // For now: echo to client
                    send("ECHO: " + msg);
                }
            } catch (Exception e) {
                if (running) {
                    listener.onEvent(ServerEvent.error("Client " + name + " error: " + e.getMessage()));
                }
            } finally {
                shutdown();
            }
        }

        void send(String msg) {
            try {
                if (out != null) {
                    out.writeObject(msg);
                    out.flush();
                }
            } catch (IOException e) {
                listener.onEvent(ServerEvent.error("Failed to send to " + name + ": " + e.getMessage()));
            }
        }

        void shutdown() {
            alive = false;
            try {
                if (in != null) in.close();
            } catch (IOException ignored) {
            }
            try {
                if (out != null) out.close();
            } catch (IOException ignored) {
            }
            try {
                if (!socket.isClosed()) socket.close();
            } catch (IOException ignored) {
            }

            // Remove client and notify UI
            if (clients.remove(id) != null) {
                listener.onEvent(ServerEvent.left(id, name, clients.size()));
            }
        }
    }
}

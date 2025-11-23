import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {

    private final int port;
    private final ServerEventListener listener;

    private volatile boolean running = false;
    private ServerSocket serverSocket;

    private ExecutorService acceptorPool;
    private ExecutorService clientPool;
    private final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();

    public GameServer(int port, ServerEventListener listener) {
        this.port = port;
        this.listener = listener;
    }

    public synchronized void startAsync() {
        if (running) {
            return;   // already running
        }
        running = true;

        acceptorPool = Executors.newSingleThreadExecutor();
        clientPool = Executors.newCachedThreadPool();

        acceptorPool.submit(new Runnable() {
            @Override
            public void run() {
                acceptLoop();
            }
        });
    }

    private void acceptLoop() {
        try (ServerSocket ss = new ServerSocket(port)) {
            serverSocket = ss;

            // Server status message to GUI
            listener.onEvent(
                    ServerEvent.serverMessage("Server listening on port " + port, clients.size())
            );

            while (running) {
                Socket socket = ss.accept();
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);

                listener.onEvent(ServerEvent.join(handler.id, handler.name, clients.size()));
                clientPool.submit(handler);
            }
        } catch (IOException e) {
            if (running) {
                listener.onEvent(ServerEvent.error("Server error: " + e.getMessage()));
            }
        } finally {
            running = false;
        }
    }

    public synchronized void stopAsync() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();  // unblocks accept()
            }
        } catch (IOException ignored) {}

        if (acceptorPool != null) {
            acceptorPool.shutdownNow();
        }
        if (clientPool != null) {
            clientPool.shutdownNow();
        }

        for (ClientHandler ch : clients) {
            ch.close();
        }
        clients.clear();
    }

    // ================== INNER CLIENT HANDLER ==================

    private class ClientHandler implements Runnable {
        private final UUID id = UUID.randomUUID();
        private final String name = "Client-" + id.toString().substring(0, 4);

        private final Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // IMPORTANT: create ObjectOutputStream first, then ObjectInputStream
                out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                in = new ObjectInputStream(socket.getInputStream());

                // simple welcome message
                send("Connected to 3-Card Poker server on port " + port);

                while (running && !socket.isClosed()) {
                    Object obj = in.readObject();
                    if (obj == null) {
                        break;
                    }

                    String msg = obj.toString();

                    // Log to GUI
                    listener.onEvent(ServerEvent.message(id, name, clients.size(), msg));

                    // For now, just echo back to sender
                    send("Server echo: " + msg);
                }
            } catch (EOFException eof) {
                // client closed connection
            } catch (IOException e) {
                listener.onEvent(ServerEvent.error("Client IO error: " + e.getMessage()));
            } catch (ClassNotFoundException e) {
                listener.onEvent(ServerEvent.error("Client sent unknown object: " + e.getMessage()));
            } finally {
                close();
                clients.remove(this);
                listener.onEvent(ServerEvent.left(id, name, clients.size()));
            }
        }

        void send(String msg) {
            try {
                if (out != null) {
                    out.writeObject(msg);
                    out.flush();
                }
            } catch (IOException ignored) {}
        }

        void close() {
            try {
                if (in != null) in.close();
            } catch (IOException ignored) {}
            try {
                if (out != null) out.close();
            } catch (IOException ignored) {}
            try {
                if (socket != null && !socket.isClosed()) socket.close();
            } catch (IOException ignored) {}
        }
    }
}

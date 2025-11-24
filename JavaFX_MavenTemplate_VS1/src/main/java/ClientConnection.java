import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Consumer;

public class ClientConnection extends Thread {

    private final String host;
    private final int port;
    private Consumer<String> onMessage;   // <-- NOT final anymore

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private volatile boolean running = true;

    public ClientConnection(String host, int port, Consumer<String> onMessage) {
        this.host = host;
        this.port = port;
        this.onMessage = onMessage;
    }

    // NEW: allow controllers to change where messages go
    public synchronized void setOnMessage(Consumer<String> onMessage) {
        this.onMessage = onMessage;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(host, port);
            // Same order as on the server: output stream first, then input stream
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            while (running && !socket.isClosed()) {
                Object obj = in.readObject();
                if (obj == null) break;
                String msg = obj.toString();

                Consumer<String> handler = this.onMessage;
                if (handler != null) {
                    handler.accept(msg);
                }
            }
        } catch (Exception e) {
            Consumer<String> handler = this.onMessage;
            if (handler != null) {
                handler.accept("Connection error: " + e.getMessage());
            }
        } finally {
            close();
        }
    }

    public void send(String msg) {
        try {
            if (out != null) {
                out.writeObject(msg);
                out.flush();
            }
        } catch (IOException e) {
            Consumer<String> handler = this.onMessage;
            if (handler != null) {
                handler.accept("Send failed: " + e.getMessage());
            }
        }
    }

    public void close() {
        running = false;
        try { if (in != null) in.close(); } catch (IOException ignored) {}
        try { if (out != null) out.close(); } catch (IOException ignored) {}
        try { if (socket != null && !socket.isClosed()) socket.close(); } catch (IOException ignored) {}
    }
}

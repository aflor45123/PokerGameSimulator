import java.util.UUID;
import java.util.concurrent.*;

public class GameServer {

    private final int port;
    private final ServerEventListener listener;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public GameServer(int port, ServerEventListener listener) {
        this.port = port;
        this.listener = listener;
    }

    public void startAsync() {
        executor.submit(() -> {
            try {
                Thread.sleep(1000);
                listener.onEvent(ServerEvent.join(UUID.randomUUID(), "Client1", 1));
            } catch (Exception e) {
                listener.onEvent(ServerEvent.error("Server error"));
            }
        });
    }

    public void stopAsync() {
        executor.shutdownNow();
    }
}

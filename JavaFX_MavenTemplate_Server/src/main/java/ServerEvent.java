import java.util.UUID;

public class ServerEvent {

    private final ServerEventType type;
    private final UUID clientId;
    private final String clientName;
    private final int connectedCount;
    private final String message;

    public ServerEvent(
            ServerEventType type,
            UUID clientId,
            String clientName,
            int connectedCount,
            String message
    ) {
        this.type = type;
        this.clientId = clientId;
        this.clientName = clientName;
        this.connectedCount = connectedCount;
        this.message = message;
    }

    public ServerEventType type() { return type; }
    public UUID clientId() { return clientId; }
    public String clientName() { return clientName; }
    public int connectedCount() { return connectedCount; }
    public String message() { return message; }

    // Factory helpers used in GameServer / controller

    public static ServerEvent join(UUID id, String name, int count) {
        return new ServerEvent(ServerEventType.CLIENT_JOIN, id, name, count, null);
    }

    public static ServerEvent left(UUID id, String name, int count) {
        return new ServerEvent(ServerEventType.CLIENT_LEFT, id, name, count, null);
    }

    public static ServerEvent error(String msg) {
        return new ServerEvent(ServerEventType.SERVER_ERROR, null, null, 0, msg);
    }

    public static ServerEvent info(String msg) {
        return new ServerEvent(ServerEventType.ROUND_RESULT, null, null, 0, msg);
    }
}

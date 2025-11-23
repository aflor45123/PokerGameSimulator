import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

// existing class header...
public class ServerDashboardController implements ServerEventListener {

    @FXML private Label statusLabel;
    @FXML private Label portLabel;
    @FXML private Label clientCountLabel;
    @FXML private ListView<String> eventList;
    @FXML private CheckBox autoscrollCheck;

    // NEW: matches fx:id="logLevelChoice" in FXML
    @FXML private ChoiceBox<String> logLevelChoice;

    private GameServer server;
    private int initialPort = 5000;
    private boolean autoStart = false;

    public void setInitialPort(int p) { this.initialPort = p; }
    public void setAutoStart(boolean a) { this.autoStart = a; }

    @FXML
    public void initialize() {
        // optional: populate log levels so the ChoiceBox is not empty
        if (logLevelChoice != null) {
            logLevelChoice.getItems().addAll("INFO", "DEBUG", "WARN", "ERROR");
            logLevelChoice.getSelectionModel().selectFirst();
        }

        if (autoStart) {
            Platform.runLater(this::startServer);
        }
    }

    @FXML
    public void startServer() {
        server = new GameServer(initialPort, this);
        server.startAsync();
        statusLabel.setText("ONLINE");
        portLabel.setText(String.valueOf(initialPort));
    }

    @FXML
    public void stopServer() {
        if (server != null) server.stopAsync();
        statusLabel.setText("OFFLINE");
    }

    @FXML
    public void handleClearFeed() {
        eventList.getItems().clear();
    }

    // NEW: matches onAction="#handleSaveLogs" in FXML
    @FXML
    public void handleSaveLogs() {
        // For now just log a message; you can later add FileChooser + file writing.
        log("Save Logs clicked (not implemented yet).");
    }

    private void log(String msg) {
        eventList.getItems().add(msg);
        if (autoscrollCheck.isSelected()) {
            eventList.scrollTo(eventList.getItems().size() - 1);
        }
    }

    @Override
    public void onEvent(ServerEvent event) {
        Platform.runLater(() -> {
            switch (event.type()) {

                case CLIENT_JOIN:
                    clientCountLabel.setText(String.valueOf(event.connectedCount()));
                    log("JOIN — " + event.clientName());
                    break;

                case CLIENT_LEFT:
                    clientCountLabel.setText(String.valueOf(event.connectedCount()));
                    log("LEFT — " + event.clientName());
                    break;

                case MESSAGE:
                    String who = event.clientName() != null ? event.clientName() : "SERVER";
                    log(who + ": " + event.message());
                    break;

                case SERVER_ERROR:
                    log("ERROR — " + event.message());
                    break;

                default:
                    log(event.type() + " — " + event.clientName());
                    break;
            }
        });
    }
}

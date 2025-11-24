import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ServerDashboardController implements ServerEventListener {

    @FXML private Label statusLabel;
    @FXML private Label portLabel;
    @FXML private Label clientCountLabel;
    @FXML private ListView<String> eventList;
    @FXML private CheckBox autoscrollCheck;
    @FXML private ChoiceBox<String> logLevelChoice;

    @FXML private Button startBtn;
    @FXML private Button stopBtn;

    private GameServer server;
    private int initialPort = 5000;
    private boolean autoStart = false;

    public void setInitialPort(int p) { this.initialPort = p; }
    public void setAutoStart(boolean a) { this.autoStart = a; }

    @FXML
    public void initialize() {
        if (logLevelChoice != null) {
            logLevelChoice.getItems().addAll("INFO", "DEBUG", "WARN", "ERROR");
            logLevelChoice.getSelectionModel().selectFirst();
        }

        // Initial button states
        if (startBtn != null && stopBtn != null) {
            startBtn.setDisable(false);
            stopBtn.setDisable(true);
        }

        if (autoStart) {
            Platform.runLater(this::startServer);
        }
    }

    @FXML
    public void startServer() {
        if (server != null) {
            return;
        }

        server = new GameServer(initialPort, this);
        server.startAsync();

        statusLabel.setText("ONLINE");
        statusLabel.getStyleClass().remove("status-offline");
        statusLabel.getStyleClass().add("status-online");

        portLabel.setText(String.valueOf(initialPort));

        if (startBtn != null && stopBtn != null) {
            startBtn.setDisable(true);
            stopBtn.setDisable(false);
        }

        log("Server starting on port " + initialPort + " ...");
    }

    @FXML
    public void stopServer() {
        if (server != null) {
            server.stopAsync();
            server = null;
        }

        statusLabel.setText("OFFLINE");
        statusLabel.getStyleClass().remove("status-online");
        statusLabel.getStyleClass().add("status-offline");

        if (startBtn != null && stopBtn != null) {
            startBtn.setDisable(false);
            stopBtn.setDisable(true);
        }

        clientCountLabel.setText("0");
        log("Server stopped.");
    }

    @FXML
    public void handleClearFeed() {
        eventList.getItems().clear();
    }

    @FXML
    public void handleSaveLogs() {
        log("Save Logs clicked (not implemented yet).");
    }

    private void log(String msg) {
        Platform.runLater(() -> {
            eventList.getItems().add(msg);
            if (autoscrollCheck.isSelected()) {
                eventList.scrollTo(eventList.getItems().size() - 1);
            }
        });
    }

    @Override
    public void onEvent(final ServerEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                switch (event.type()) {
                    case CLIENT_JOIN:
                        clientCountLabel.setText(String.valueOf(event.connectedCount()));
                        log("JOIN: " + event.clientName() + " (total clients: " + event.connectedCount() + ")");
                        break;

                    case CLIENT_LEFT:
                        clientCountLabel.setText(String.valueOf(event.connectedCount()));
                        log("LEFT: " + event.clientName() + " (total clients: " + event.connectedCount() + ")");
                        break;

                    case SERVER_ERROR:
                        log("ERROR: " + event.message());
                        break;

                    case ROUND_RESULT:
                    case ROUND_STARTED:
                        // For now treat these as info/log messages
                        String msg = (event.message() != null) ? event.message() : event.type().name();
                        log(msg);
                        break;

                    default:
                        // Just in case new enum values are added later
                        log("EVENT: " + event.type() + " " +
                            (event.message() != null ? event.message() : ""));
                        break;
                }
            }
        });
    }
}

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class GamePlayController implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private StackPane card1;

    @FXML
    private StackPane card2;

    @FXML
    private StackPane card3;

    @FXML
    private StackPane card4;

    @FXML
    private StackPane card5;

    @FXML
    private StackPane card6;

    @FXML
    private Button dealButton;

    @FXML
    private Button welcomeBack;

    @FXML
    private ListView<String> gameLog;   // IMPORTANT: wired to FXML

    private boolean newLookSet = false;

    // SHARED connection (same for all rounds)
    private static ClientConnection sharedConnection;
    private static String sharedHost;
    private static int sharedPort;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // nothing required here yet
    }

    // Called by WelcomeController (first time) and by Results screen (Play Again)
    public void initConnection(String host, int port) {

        Consumer<String> handler = message ->
                Platform.runLater(() -> {
                    if (gameLog != null) {
                        gameLog.getItems().add(message);
                    }
                });

        if (sharedConnection == null) {
            // First time: create and start the client thread
            sharedHost = host;
            sharedPort = port;
            sharedConnection = new ClientConnection(host, port, handler);
            sharedConnection.start();
        } else {
            // Connection already exists: just redirect messages to this screen's gameLog
            sharedConnection.setOnMessage(handler);
        }
    }

    // Used by Results screen to know if a session exists
    public static boolean hasActiveConnection() {
        return sharedConnection != null;
    }

    public static String getSharedHost() {
        return sharedHost;
    }

    public static int getSharedPort() {
        return sharedPort;
    }

    private ClientConnection getConnection() {
        return sharedConnection;
    }
    
    public static void closeSharedConnection() {
        if (sharedConnection != null) {
            sharedConnection.close();   // closes socket & streams
            sharedConnection = null;
            sharedHost = null;
            sharedPort = 0;
        }
    }

    // optional: send a test or command to server
    public void sendTestMessage(String text) {
        if (sharedConnection != null) {
            sharedConnection.send(text);
        }
    }

    public void settle(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/results.fxml"));
        Parent root3 = loader.load();
        root3.getStylesheets().add("/styles/results.css");
        root.getScene().setRoot(root3);
    }

    public void backToWelcome(ActionEvent e) throws IOException {
        // Close connection when leaving to welcome
        closeSharedConnection();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/welcome.fxml"));
        Parent rootA = loader.load();
        rootA.getStylesheets().add("/styles/welcome.css");
        root.getScene().setRoot(rootA);
    }

    public void getNewLook(ActionEvent e) throws IOException {
        root.getStylesheets().clear();
        if (!newLookSet) {
            root.getStylesheets().add("/styles/newLook.css");
        } else {
            root.getStylesheets().add("/styles/gamePlay.css");
        }
        newLookSet = !newLookSet;
    }

    public void startDeal() {
        // Local client-side card visuals (this will later be driven by server messages)
        Deck deck1 = new Deck();
        deck1.shuffle();

        Card cardA = deck1.draw();
        Card cardB = deck1.draw();
        Card cardC = deck1.draw();
        Card cardD = deck1.draw();
        Card cardE = deck1.draw();
        Card cardF = deck1.draw();

        String path1 = cardA.toString();
        String path2 = cardB.toString();
        String path3 = cardC.toString();
        String path4 = cardD.toString();
        String path5 = cardE.toString();
        String path6 = cardF.toString();

        card1.setStyle(
            "-fx-background-image: url('/cards/" + path1 + ".jpg');" +
            "-fx-background-size: cover;" +
            "-fx-background-repeat: no-repeat;" +
            "-fx-border-color: Cyan;" +
            "-fx-border-width: 2;"
        );

        card2.setStyle(
            "-fx-background-image: url('/cards/" + path2 + ".jpg');" +
            "-fx-background-size: cover;" +
            "-fx-background-repeat: no-repeat;" +
            "-fx-border-color: Cyan;" +
            "-fx-border-width: 2;"
        );

        card3.setStyle(
            "-fx-background-image: url('/cards/" + path3 + ".jpg');" +
            "-fx-background-size: cover;" +
            "-fx-background-repeat: no-repeat;" +
            "-fx-border-color: Cyan;" +
            "-fx-border-width: 2;"
        );

        card4.setStyle(
            "-fx-background-image: url('/cards/" + path4 + ".jpg');" +
            "-fx-background-size: cover;" +
            "-fx-background-repeat: no-repeat;" +
            "-fx-border-color: Cyan;" +
            "-fx-border-width: 2;"
        );

        card5.setStyle(
            "-fx-background-image: url('/cards/" + path5 + ".jpg');" +
            "-fx-background-size: cover;" +
            "-fx-background-repeat: no-repeat;" +
            "-fx-border-color: Cyan;" +
            "-fx-border-width: 2;"
        );

        card6.setStyle(
            "-fx-background-image: url('/cards/" + path6 + ".jpg');" +
            "-fx-background-size: cover;" +
            "-fx-background-repeat: no-repeat;" +
            "-fx-border-color: Cyan;" +
            "-fx-border-width: 2;"
        );
    }
}

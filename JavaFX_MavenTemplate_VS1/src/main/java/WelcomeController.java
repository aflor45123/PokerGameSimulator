import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.InetSocketAddress;
import java.net.Socket;

import javafx.scene.control.Alert;



public class WelcomeController implements Initializable {
	
	@FXML
	private BorderPane root;
	
	@FXML
	private BorderPane root2;
	
	@FXML
	private BorderPane root3;
	
	@FXML
	private Rectangle rect;
	
	@FXML
	private Circle circ;
	
	@FXML
	private StackPane stackpane;
	
	@FXML
	private Text title;
	
	@FXML
	private Text inputText;
	
	@FXML
	private ListView<String> gameLog;
	
	@FXML
	private ListView<String> anteList;
	
	@FXML
	private ListView<String> pairPlusList;
	
	@FXML
	private ListView<String> playList;
	
	@FXML
	private ListView<String> results;
	
	@FXML
	private Button playGame;
	
	@FXML
	private Button gameResults;
	
	
	@FXML
	private TextField importNum;
	
	@FXML
	private TextField ipAddress;
	
	@FXML
	private TextField pairPlusWager;
	
	@FXML
	private TextField anteWager;
	
	@FXML
	private TextField playWager;
	
	
		
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			
			
		
		if (gameLog != null) {
			ObservableList<String> emptySlots = FXCollections.observableArrayList();
		
			for (int i = 0; i < 5; i++) {
				emptySlots.add("");
			}
		
			gameLog.setItems(emptySlots);
		}
		
		if (anteList != null) {
			ObservableList<String> emptySlots = FXCollections.observableArrayList();
		
			for (int i = 0; i < 5; i++) {
				emptySlots.add("");
			}
		
			anteList.setItems(emptySlots);
		}
		
		if (pairPlusList != null) {
			ObservableList<String> emptySlots = FXCollections.observableArrayList();
		
			for (int i = 0; i < 5; i++) {
				emptySlots.add("");
			}
		
			pairPlusList.setItems(emptySlots);
		}
		
		if (playList != null) {
			ObservableList<String> emptySlots = FXCollections.observableArrayList();
		
			for (int i = 0; i < 5; i++) {
				emptySlots.add("");
			}
		
			playList.setItems(emptySlots);
		}
		
		if (results != null) {
			ObservableList<String> emptySlots = FXCollections.observableArrayList();
		
			for (int i = 0; i < 5; i++) {
				emptySlots.add("");
			}
		
			results.setItems(emptySlots);
		}
	}
	
	public void startRound(ActionEvent e) throws IOException {

        // 1. Read raw text from the fields
        String ipText = (ipAddress != null) ? ipAddress.getText().trim() : "";
        String portText = (importNum != null) ? importNum.getText().trim() : "";

        // 2. Basic "both fields required" validation
        if (ipText.isEmpty() || portText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Information");
            alert.setHeaderText("IP address and port are required");
            alert.setContentText("Please enter:\n\nIP: 127.0.0.1\nPort: 5000");
            alert.showAndWait();
            return;
        }

        // 3. Validate exact IP and port
        if (!"127.0.0.1".equals(ipText)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid IP Address");
            alert.setHeaderText("IP address must be 127.0.0.1");
            alert.setContentText("You entered: " + ipText + "\n\nPlease use 127.0.0.1 to connect.");
            alert.showAndWait();
            return;
        }

        int port;
        try {
            port = Integer.parseInt(portText);
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Port");
            alert.setHeaderText("Port must be a number");
            alert.setContentText("Please enter 5000 as the port.");
            alert.showAndWait();
            return;
        }

        if (port != 5000) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Port");
            alert.setHeaderText("Port must be 5000");
            alert.setContentText("You entered: " + port + "\n\nPlease use port 5000 to connect.");
            alert.showAndWait();
            return;
        }

        // 4. Try a quick connection test BEFORE changing scenes
        try (Socket testSocket = new Socket()) {
            testSocket.connect(new InetSocketAddress(ipText, port), 2000);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection Failed");
            alert.setHeaderText("Could not connect to server");
            alert.setContentText(
                    "Please make sure the server is running on:\n\n" +
                    "IP: 127.0.0.1\nPort: 5000\n\nDetails: " + ex.getMessage()
            );
            alert.showAndWait();
            return; // do NOT switch scenes
        }

        // 5. Only if everything is valid and connection test succeeded, load gameplay
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gamePlay.fxml"));
        Parent root2 = loader.load();

        GamePlayController controller = loader.getController();
        controller.initConnection(ipText, port);

        root2.getStylesheets().add("/styles/gamePlay.css");
        root.getScene().setRoot(root2);
    }
	
	
	
	
	
}

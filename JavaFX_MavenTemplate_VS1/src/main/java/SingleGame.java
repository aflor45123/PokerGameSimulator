import java.awt.desktop.SystemSleepEvent;
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
import javafx.application.Platform;




public class SingleGame implements Initializable {
	
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
	private Button welcomeBack;
	
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
	
	@FXML
	private StackPane card1;
	
	@FXML
	private StackPane card2;
	
	@FXML
	private StackPane card3;
	
	private boolean newLookSet = false;
	
	private ClientConnection connection; 
	
	
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
	
	public void startRound(ActionEvent e) throws IOException{
		
		// 1. Read host + port from welcome screen text fields
	    String host = "127.0.0.1";
	    if (ipAddress != null && !ipAddress.getText().trim().isEmpty()) {
	        host = ipAddress.getText().trim();
	    }

	    int port = 0;
	    if (importNum != null && !importNum.getText().trim().isEmpty()) {
	        try {
	            port = Integer.parseInt(importNum.getText().trim());
	        } catch (NumberFormatException ex) {
	            port = 5000; // default / fallback
	        }
	    } else {
	        port = 5000;
	    }

	    // 2. Load the gamePlay scene
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gamePlay.fxml"));
	    Parent root2 = loader.load();

	    // 3. Get the new controller instance for gamePlay.fxml
	    SingleGame gameController = loader.getController();

	    // 4. Connect this new controller to the server
	    gameController.connectToServer(host, port);

	    // 5. Apply stylesheet and swap the scene root
	    root2.getStylesheets().add("/styles/gamePlay.css");
	    root.getScene().setRoot(root2);
        
        
        /* THIS NEEDS TO BE FIXED
        
        // Create deck of shuffled cards
        Deck deck1 = new Deck();
		deck1.shuffle();
		
		Card cardA = deck1.draw();
		Card cardB = deck1.draw();
		Card cardC = deck1.draw();
		
		String path = cardA.toString();
		
		card1.setStyle(
			"-fx-background-image: url('/cards/" + path + ".jpg');" +
			"-fx-background-size: cover;" +
			"-fx-background-repeat: no-repeat;" +
			"-fx-border-color: Cyan;" +
			"-fx-border-width: 2;"
		);
		
		System.out.println(path);*/
		
		//URL url = getClass().getResource("/cards/" + path + ".jpg");
		//System.out.println(url); // Should not be null
        
        
		
	
	}
	
	public void settle(ActionEvent e) throws IOException{
		
		//Get instance of the loader class
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/results.fxml"));
        Parent root3 = loader.load(); // Load view into parent
        
        //GameServer myctr = loader.getController(); // Get controller created by FXMLLoader
        root3.getStylesheets().add("/styles/results.css"); // Set style
        
        root.getScene().setRoot(root3); 
        
    

		
		
		
		
		
		
		
	}
	
	
	public void backToWelcome(ActionEvent e) throws IOException{
		
		// Get instance of the loader class
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/welcome.fxml"));
		Parent rootA = loader.load(); // Load view into parent
				
		//GameServer myctr = loader.getController(); // Get controller created by FXMLLoader
		rootA.getStylesheets().add("/styles/welcome.css"); // Set style
		        
		root.getScene().setRoot(rootA);
	}
	
	public void getNewLook(ActionEvent e) throws IOException{
		
		root.getStylesheets().clear();
		
		if (newLookSet == false) {
			root.getStylesheets().add("/styles/newLook.css");
		}
		else {
			root.getStylesheets().add("/styles/gamePlay.css");
		}
		
		newLookSet = !newLookSet;
	}
	
	public void connectToServer(String host, int port) {
	    connection = new ClientConnection(host, port, message -> {
	        Platform.runLater(() -> {
	            if (gameLog != null) {
	                gameLog.getItems().add(message);
	            }
	        });
	    });
	    connection.start();
	}

	public void sendTestMessage(ActionEvent e) {
	    if (connection != null) {
	        connection.send("Hello from client!");
	    }
	}
	
	
}

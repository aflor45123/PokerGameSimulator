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
	
	private boolean newLookSet = false;
	
	
	
	
	
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
		
		//Get instance of the loader class
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gamePlay.fxml"));
        Parent root2 = loader.load(); // Load view into parent
        
        //GameServer myctr = loader.getController(); // Get controller created by FXMLLoader
        root2.getStylesheets().add("/styles/gamePlay.css"); // Set style
        
        root.getScene().setRoot(root2);   
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
	
	
}

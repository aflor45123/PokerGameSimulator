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
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class GameServer implements Initializable{
	
	@FXML
	private BorderPane root;
	
	@FXML
	private BorderPane root2;
	
	@FXML
	private TextField textField;
	
	@FXML
	private Rectangle rect;
	
	@FXML
	private StackPane stackPane;
	
	@FXML
	private ListView<String> clients;
	
	@FXML
	private ListView<String> serverLog;
	
	@FXML
	private ListView<String> gameInfo;
	
	@FXML
	private TextField putText;
	
	@FXML
	private Text text;
	
	@FXML
	private Text subTitle;
	
	@FXML
	private Text inputText;
	
	@FXML
	private Button createButton;
	
	@FXML
	private Button introButton;	

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if (serverLog != null) {
			ObservableList<String> emptySlots = FXCollections.observableArrayList();
		
			for (int i = 0; i < 10; i++) {
				emptySlots.add("");
			}
		
			serverLog.setItems(emptySlots);
		}
		
		if (serverLog != null) {
			ObservableList<String> emptySlots = FXCollections.observableArrayList();
		
			for (int i = 0; i < 8; i++) {
				emptySlots.add("");
			}
		
			clients.setItems(emptySlots);
		}
		
		if (gameInfo != null) {
			ObservableList<String> emptySlots = FXCollections.observableArrayList();
		
			for (int i = 0; i < 10; i++) {
				emptySlots.add("");
			}
		
			gameInfo.setItems(emptySlots);
		}
		
	}
	
	
	// Creates server and sets Server Information scene
	public void createServer(ActionEvent e) throws IOException {
		
        //Get instance of the loader class
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Myfxml2.fxml"));
        Parent root2 = loader.load(); // Load view into parent
        
        //GameServer myctr = loader.getController(); // Get controller created by FXMLLoader
        root2.getStylesheets().add("/styles/style2.css"); // Set style
        
        root.getScene().setRoot(root2);   
	}
	
	// Switches back to Create a Server scene after original server has shutdown
	public void backToStart(ActionEvent e) throws IOException {
		
		// Get instance of the loader class
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Myfxml.fxml"));
		Parent root = loader.load(); // Load view into parent
		
		//GameServer myctr = loader.getController(); // Get controller created by FXMLLoader
        root.getStylesheets().add("/styles/style1.css"); // Set style
        
		root2.getScene().setRoot(root);
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
}

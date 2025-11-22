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


public class SingleGame implements Initializable {
	
	@FXML
	private BorderPane root;
	
	@FXML
	private BorderPane root2;
	
	@FXML
	private BorderPane root3;
	
	@FXML
	private TextField importNum;
	
	@FXML
	private TextField ipAddress;
	
	@FXML
	private Rectangle rect;
	
	@FXML
	private StackPane stackpane;
	
	@FXML
	private Text inputText;
	
	@FXML
	private Button playGame;
	
	@FXML
	private Button gameResults;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
}

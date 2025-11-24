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
	
	private boolean newLookSet = false;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
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
				
		//GameServer mctr = loader.getController(); // Get controller created by FXMLLoader
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

	public void startDeal() {
		
		// Create deck of shuffled cards
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

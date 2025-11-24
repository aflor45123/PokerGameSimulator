import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
				
			
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/welcome.fxml"));
			primaryStage.setTitle("3 Card Poker");
			Scene s1 = new Scene(root, 900, 600);
			s1.getStylesheets().add("/styles/welcome.css");
			primaryStage.setScene(s1);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}

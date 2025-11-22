import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ServerIntroController {

    @FXML private TextField portField;
    @FXML private CheckBox autoStartCheckbox;

    @FXML
    private void initialize() {
        portField.setText("5000");
    }

    @FXML
    private void handleStart(ActionEvent e) {
        int port;
        try {
            port = Integer.parseInt(portField.getText());
        } catch (Exception ex) {
            port = 5000;
        }

        try {
            Stage stage = (Stage) portField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/server_dashboard.fxml"));
            Pane root = loader.load();

            ServerDashboardController controller = loader.getController();
            controller.setInitialPort(port);
            controller.setAutoStart(autoStartCheckbox.isSelected());

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/server.css").toExternalForm());
            stage.setScene(scene);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleExit(ActionEvent e) {
        ((Stage) portField.getScene().getWindow()).close();
    }
}

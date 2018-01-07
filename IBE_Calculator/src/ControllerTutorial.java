import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class ControllerTutorial {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button closeTut;

    @FXML
    void closeTut(ActionEvent event) {
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
        assert closeTut != null : "fx:id=\"closeTut\" was not injected: check your FXML file 'TutorialScreen.fxml'.";

    }
}

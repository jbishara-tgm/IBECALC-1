import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ControllerSplashScreen {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView logoStart;

    @FXML
    void initialize() {
        assert logoStart != null : "fx:id=\"logoStart\" was not injected: check your FXML file 'LoadingScreen.fxml'.";

    }
}

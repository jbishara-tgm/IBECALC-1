import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerLoadScreen {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView ibe_logo;

    @FXML
    private ProgressIndicator progressIndicator;

    public ControllerLoadScreen(){
    	
    }
    
    @FXML
    void initialize() {

        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'IBE_LoadScreen.fxml'.";
        assert ibe_logo != null : "fx:id=\"ibe_logo\" was not injected: check your FXML file 'IBE_LoadScreen.fxml'.";
        assert progressIndicator != null : "fx:id=\"progressIndicator\" was not injected: check your FXML file 'IBE_LoadScreen.fxml'.";
    }
}

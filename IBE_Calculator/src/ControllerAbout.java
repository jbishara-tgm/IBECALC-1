import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class ControllerAbout {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button close;

    @FXML
    private Hyperlink hyperlinkInfra;

    @FXML
    private ImageView logo;

    @FXML
    private TextArea textFieldAbout;
    
    @FXML
    private Label copyright;

	URI uri;
    
    @FXML
    void ahref(ActionEvent event) {
    	try {
			try {
				uri = new URI("http://www.oebb.at/infrastruktur/de/_p_3_0_fuer_Kunden_Partner/index.jsp");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(Desktop.isDesktopSupported()){
				Desktop.getDesktop().browse(uri);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	hyperlinkInfra.setVisited(true);
    }

    @FXML
    void closeAbout(ActionEvent event) {
    	//schlieﬂt alle fenster
    	//Platform.exit();
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
        assert close != null : "fx:id=\"close\" was not injected: check your FXML file 'About.fxml'.";
        assert hyperlinkInfra != null : "fx:id=\"hyperlinkInfra\" was not injected: check your FXML file 'About.fxml'.";
        assert logo != null : "fx:id=\"logo\" was not injected: check your FXML file 'About.fxml'.";
        assert textFieldAbout != null : "fx:id=\"textFieldAbout\" was not injected: check your FXML file 'About.fxml'.";
        assert copyright != null : "fx:id=\"copyright\" was not injected: check your FXML file 'About.fxml'.";

    }
}

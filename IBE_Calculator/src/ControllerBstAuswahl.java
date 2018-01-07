import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class ControllerBstAuswahl {
	//ControllerMain cm = new ControllerMain();
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button okButton;

    @FXML
    private ListView<String> bstList;

    @FXML
    private Button search;

    @FXML
    private TextField textFieldBst;
    
    static String bstcode;
    static ArrayList hst_search = new ArrayList();
    static ArrayList hst = new ArrayList();
    private ControllerMain cm;
    private String vbtitle;
    
    public ControllerBstAuswahl() {

    }
    
    public ControllerBstAuswahl(ControllerMain cm, String vb) {
    	this.cm = cm;
    	this.vbtitle = vb;
    }
    
    @FXML
    void closeBstAuswahl(ActionEvent event) {
    	/*
    	 * closes the gui-window
    	 */
    	bstcode = getHst();
    	String bstcode2[] = bstcode.split("-  ");
    	
    	if(vbtitle.equals("von")){
    		this.cm.getDB640von().setText(bstcode2[1]);
    	}else{
    		this.cm.getDB640bis().setText(bstcode2[1]);
    	}
    	//ControllerMain.DB640von.setText(von);
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private String getHst() {
    	/*
    	 * holt sich die ausgewählte Hst
    	 */
    	String hst_ausg = "";
		ObservableList<String> hst_obs;
		hst_obs = bstList.getSelectionModel().getSelectedItems();
		
		for(String h: hst_obs){
			hst_ausg = h;
		}
		System.out.println(hst_ausg);
		return hst_ausg;
	}

	@FXML
    void searchForBst(ActionEvent event) {
		/*
		 * searches for a specific station in the listView
		 */
		System.out.println("Suche nach Bst in der Liste");
		boolean found = false;
		String search = textFieldBst.getText().toLowerCase();
		hst_search.clear();
		
		for (int i = 0;i < hst.size(); i++){
			if(hst.get(i).toString().toLowerCase().contains(search)){
				hst_search.add(hst.get(i));
				found = true;
			}
		}
		
		if(found){
			bstList.getItems().clear();
			bstList.getItems().addAll(hst_search);
		}else{
			bstList.getItems().clear();
			bstList.getItems().add("Keine Betriebsstelle gefunden");
		}
    }

    @FXML
    void initialize() {
    	//adds all names of the stations (short name and long name) into the listView
    	switch(this.cm.getFp_per()){
    	case "2017":
    		hst = ExcelHandler.hst_c17;
    		break;
    	case "2018V1":
    		hst = ExcelHandler.hst_c18;
    		break;
    	case "2018V2":
    		hst = ExcelHandler.hst_c18;
    	}
    	bstList.getItems().addAll(hst);

        assert okButton != null : "fx:id=\"okButton\" was not injected: check your FXML file 'Bst-Auswahl.fxml'.";
        assert bstList != null : "fx:id=\"bstList\" was not injected: check your FXML file 'Bst-Auswahl.fxml'.";
        assert search != null : "fx:id=\"search\" was not injected: check your FXML file 'Bst-Auswahl.fxml'.";
        assert textFieldBst != null : "fx:id=\"textFieldBst\" was not injected: check your FXML file 'Bst-Auswahl.fxml'.";

    }
}

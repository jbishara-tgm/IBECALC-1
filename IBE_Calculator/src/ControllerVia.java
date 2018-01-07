import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControllerVia {
	
	private boolean bearbeitung;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textFieldbst;

    @FXML
    private ListView<String> listBst;

    @FXML
    private Button buttonSearch;

    @FXML
    private Label labelBstCode;

    @FXML
    private Button buttonVia;

    @FXML
    private Button buttonHalte;

    @FXML
    private ListView<String> listVia = new ListView<>();

    @FXML
    private Button buttonBstLöschen;

    @FXML
    private TextArea textAreaHinweis;

    @FXML
    private Button buttonSpeichern;
    
    ArrayList hst_search = new ArrayList();
    ArrayList hst = new ArrayList();
    
    ArrayList vias = new ArrayList();
    ArrayList halte = new ArrayList();
    ArrayList route = new ArrayList();
    ObservableList<String> viaUhalte = FXCollections.observableArrayList();
    //List<String> viaUhalte = new ObservableList();
    private ControllerMain cm;

    public ControllerVia(){

    }
    
    public ControllerVia(ControllerMain c){
    	/*
    	 * wird aufgerufen falls der Button "Bst-Codes hinzufügen" gedrückt wurde
    	 */
    	this.cm = c;
    	this.viaUhalte = FXCollections.observableArrayList();
    	this.bearbeitung = false;
    }
    
    
    public ControllerVia(ControllerMain c,String str) {
    	/*
    	 * wird aufgerufen falls der Button "Bst-Codes bearbeiten" gedrückt wurde
    	 */
    	this.cm = c;
    	this.bearbeitung = true;
    	this.viaUhalte = FXCollections.observableArrayList();
    	this.viaUhalte = this.cm.getListVia().getItems();
    	for(String kek : viaUhalte){
    		this.listVia.getItems().add(kek);
    	}
	}
    
	@FXML
    void addHalte(ActionEvent event) {
		/*
		 * fügt die ausgewählte Hst der Liste als Halte hinzu
		 */
    	System.out.println("adde Halte");
    	String[] halt = listBst.getSelectionModel().getSelectedItems().toString().split(" -  ");
    	String bst = halt[1].substring(0, halt[1].length()-1);

    	halte.add(bst);
    	viaUhalte.add(bst+ " - Halt");
    	route.add(bst);
    	
    	String hst = (String) halte.get(halte.size()-1);
    	System.out.println(hst);
    	listVia.getItems().add(hst + " - Halt");
    }

    @FXML
    void addVia(ActionEvent event) {
    	/*
    	 * fügt die ausgewählte Hst der Liste als Via hinzu
    	 */
    	System.out.println("adde Via");
    	String[] via = listBst.getSelectionModel().getSelectedItems().toString().split(" -  ");
    	String bst = via[1].substring(0, via[1].length()-1);
    	
    	vias.add(bst);
    	viaUhalte.add(bst);
    	route.add(bst);
    	
    	String hst = (String) vias.get(vias.size()-1);
    	System.out.println(hst);
    	listVia.getItems().add(hst);
    }

    @FXML
    void deleteBst(ActionEvent event) {
    	/*
    	 * löscht ausgewählte Hst aus der Liste
    	 */
    	String löschen = "";
    	String zlöschen = listVia.getSelectionModel().getSelectedItem();
    	if(zlöschen.contains(" - Halt")){
    		String[] eintrag = zlöschen.split(" - ");
    		löschen = eintrag[0];
    		halte.remove(löschen);
    	}else{
    		löschen = zlöschen;
    		vias.remove(löschen);
    	}
    	route.remove(löschen);
    	viaUhalte.remove(löschen);
    	listVia.getItems().remove(listVia.getSelectionModel().getSelectedItem());
    }

    @FXML
    void exitWindow(ActionEvent event) {
    	/*
    	 * falls diese GUI geschlossen wird, wird die Liste dem Controller Main übermittelt und dort angezeigt
    	 */
    	System.out.println("items in viaUhalte part 1");
    	for(int i = 0; i < viaUhalte.size(); i++){
    		System.out.println(viaUhalte.get(i));
    	}
    	if (bearbeitung){
    		this.cm.setListVia(viaUhalte);
    	} else {
    		this.cm.getListVia().getItems().addAll(viaUhalte);
    	}

    	this.cm.setHalte(halte);
    	this.cm.setVias(vias);
    	this.cm.setViauHalte(viaUhalte);
    	this.cm.setRoute(route);
    	System.out.println("items in viaUhalte part 2");
    	for(int i = 0; i < viaUhalte.size(); i++){
    		System.out.println(viaUhalte.get(i));
    	}
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void searchBst(ActionEvent event) {
    	/*
		 * searches for a specific station in the listView
		 */
		System.out.println("Suche nach Bst in der Liste");
		boolean found = false;
		String search = textFieldbst.getText().toLowerCase();
		hst_search.clear();
		
		for (int i = 0;i < hst.size(); i++){
			if(hst.get(i).toString().toLowerCase().contains(search)){
				hst_search.add(hst.get(i));
				found = true;
			}
		}
		
		if(found){
			listBst.getItems().clear();
			listBst.getItems().addAll(hst_search);
		}else{
			listBst.getItems().clear();
			listBst.getItems().add("Keine Betriebsstelle gefunden");
		}
    }

    @FXML
    void initialize() {
    	
    	/*
    	 * entsprechend der ausgewählten Fahrplanperiode wird die jeweilige Liste der verfügbaren Hst angezeigt
    	 */
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
    	listBst.getItems().addAll(hst);
    	
    	this.listVia.getItems().addAll(viaUhalte);
    	
        assert textFieldbst != null : "fx:id=\"textFieldbst\" was not injected: check your FXML file 'ViaBst-Auswahl.fxml'.";
        assert listBst != null : "fx:id=\"listBst\" was not injected: check your FXML file 'ViaBst-Auswahl.fxml'.";
        assert buttonSearch != null : "fx:id=\"buttonSearch\" was not injected: check your FXML file 'ViaBst-Auswahl.fxml'.";
        assert labelBstCode != null : "fx:id=\"labelBstCode\" was not injected: check your FXML file 'ViaBst-Auswahl.fxml'.";
        assert buttonVia != null : "fx:id=\"buttonVia\" was not injected: check your FXML file 'ViaBst-Auswahl.fxml'.";
        assert buttonHalte != null : "fx:id=\"buttonHalte\" was not injected: check your FXML file 'ViaBst-Auswahl.fxml'.";
        assert listVia != null : "fx:id=\"listVia\" was not injected: check your FXML file 'ViaBst-Auswahl.fxml'.";
        assert buttonBstLöschen != null : "fx:id=\"buttonBstLöschen\" was not injected: check your FXML file 'ViaBst-Auswahl.fxml'.";
        assert textAreaHinweis != null : "fx:id=\"textAreaHinweis\" was not injected: check your FXML file 'ViaBst-Auswahl.fxml'.";
        assert buttonSpeichern != null : "fx:id=\"buttonSpeichern\" was not injected: check your FXML file 'ViaBst-Auswahl.fxml'.";

    }
}

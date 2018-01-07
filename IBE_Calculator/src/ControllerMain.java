import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class ControllerMain {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane AnchorPaneMain;

    @FXML
    private SplitPane SplitPaneHorizontal;

    @FXML
    private AnchorPane AnchorPaneLeft;

    @FXML
    private SplitPane AnchorPaneVertical;

    @FXML
    private AnchorPane AnchorPaneVonBis;

    @FXML 
    public  TextField DB640von;

    @FXML 
    public  TextField DB640bis;

    @FXML
    private Button kürzesteRoute;

    @FXML
    private Button BstAuswahlVon;

    @FXML
    private Button BstAuswahlBis;

    @FXML
    private AnchorPane AnchorPaneTable;

    @FXML
    private TableView<Route> routeTable = new TableView<>();

    @FXML
    private TableColumn tableStation = new TableColumn("Station");

    @FXML
    private TableColumn tableKm = new TableColumn("km");

    @FXML
    private TableColumn tableAchse = new TableColumn("Achse");

    @FXML
    private TableColumn tableAnmerkung = new TableColumn("Anmerkung");

    @FXML
    private AnchorPane AnchorPaneRight;

    @FXML
    private Button excelExport;

    @FXML
    private Button refreshButton;

    @FXML
    private TextField textFieldEntgeldGes;

    @FXML
    private TextField textFieldEntgeltpT;

    @FXML
    private TextField textFieldVerkehrsanr;

    @FXML
    private TextField textFieldHalte;

    @FXML
    private TextField textFieldGBTKM;

    @FXML
    private TextField textFieldTfzFak;

    @FXML
    private TextField textFieldZugkm;

    //@FXML
    //private TextField textFieldVerkehrstage;

    /*@FXML
    private TextField textFieldVerkehrsart;*/

    @FXML
    private TextField textFieldZuggew;

    @FXML
    private TextField textFieldZugnr;

    @FXML
    private TextField textFieldKm;

    @FXML
    private Label labelKm;
    
    @FXML
    private Label labelEnt;

    @FXML
    private Label labelZugnr;

    @FXML
    private Label labelZugkm;

    @FXML
    private Label labelTfzFak;

    @FXML
    private Label labelTfzKat;

    @FXML
    private Label labelTfz;

    @FXML
    private Label labelGBTKM;

    @FXML
    private Label labelHalte;

    @FXML
    private Label labelVerkehrsanr;

    @FXML
    private Label labelEntgeldpT;

    @FXML
    private Label labelEntgeldGes;

    //@FXML
    //private Label labelVerkehrstage;

    @FXML
    private Label labelVerkehrssart;

    @FXML
    private Label labelZugklasse;

    @FXML
    private Label labelZuggew;

    //@FXML
    //private TextArea textAreaHalte;

    //@FXML 
    //public static TextArea textAreaVia;

    @FXML
    private TextArea meldungen;
    
    @FXML
    private Button viaHinzu;

    /*@FXML
    private Button halteHinzu;*/

    @FXML
    private Button deleteAllVias;
    
    @FXML
    private Button deleteVia;
    
    @FXML
    private Button viaBearbeiten;

    //@FXML
    //private Button deleteAllHalte;

    @FXML
    private Button ButtoncalculateIBE;

    //@FXML
    //private TextField textFieldViaHinzu;

    //@FXML
    //private TextField textFieldHalteHinzu;
    
    @FXML
    private TextField anzahlTfz;

    @FXML
    private ComboBox<String> dropDownZugklasse;
    
    @FXML
    private ChoiceBox<String> verkehrsart;

    @FXML
    private ChoiceBox<String> dropDownTfzKat;

    @FXML
    private ComboBox<String> dropDownTfz;

    @FXML
    private ImageView logos;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menuFile;

    @FXML
    private Menu menuAuflösung;
    
    @FXML
    private MenuItem menuItemOpen;

    @FXML
    private MenuItem menuItemSave;

    @FXML
    private MenuItem menuItemClose;
    
    @FXML
    private CheckMenuItem menuItemAufl1;
    
    @FXML
    private CheckMenuItem menuItemAufl2;

    /*@FXML
    private Menu menuEdit;

    @FXML
    private MenuItem MenuItemDelete;*/

    @FXML
    private Menu menuFahrplanper;

    @FXML
    private CheckMenuItem menuItemFahrplan2017;

    @FXML
    private CheckMenuItem menuItemFahrplan2018V1;

    @FXML
    private CheckMenuItem menuItemFahrplan2018V2;

    @FXML
    private Menu menuHilfe;
    
    @FXML
    private MenuItem menuItemTut;
    
    @FXML
    private MenuItem menuItemAbout;
    
    @FXML
    private Label selectedFahrplan;
    
    @FXML
    private Label labelVias;
    
    @FXML
    private Label selectedVersion;
    
    @FXML
    private Label labelAnzTfz;
    
    @FXML
    private ListView<String> listVias;

    ArrayList<String> vias = new ArrayList<String>();
    ArrayList<String> halte = new ArrayList<String>();
    
    static Set hstName = Collections.synchronizedSet(new HashSet());
    
    int anzTfz = 1;
	static TreeMap tfzN_G = new TreeMap();
	static TreeMap tfzN_K = new TreeMap();
	static ArrayList<String> tfzName = new ArrayList();
	
	static ArrayList zugklasse = new ArrayList();
    String klass;
    double zugGewMin;

	private ObservableList<String> viaUndHalte;

	private ArrayList<String> route;
	
	private String fp_per = "";
	
	private String aufl = "";
	
	Dijkstra dj;
	ProdKat2017 pk17;
	ProdKat2018V1 pk18V1;
	ProdKat2018V2 pk18V2;

	private boolean r_ber = false;
	private boolean ibe_ber = false;
    
    public ControllerMain(String str){
    	/*
    	 * initialisiert ein Dijkstra-Objekt
    	 * 
    	 * setzt den Auflösungsparameter auf die korrekte Auflösung
    	 */
    	dj = new Dijkstra(this);
    	aufl = str;
    }

    @FXML
    void addVia(ActionEvent event) {
    	/*
    	 * opens the Bst-Auswahl gui for train station selections
    	 */
    	System.out.println("Open Via-Bst-Auswahl.fxml for Via/Halte hinzufügen");
    	try{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViaBst-Auswahl.fxml"));
    		//setzt den Controller dieser GUI
    		loader.setController(new ControllerVia(this));
    		Parent root1 = (Parent)loader.load();
    		Stage stage = new Stage();
    		
    		stage.setTitle("Vias und Halte hinzufügen");
    		stage.setScene(new Scene(root1));
    		stage.setResizable(false);
    		stage.show();
    	}catch(Exception e){
    		System.out.println("Cant load new window");
    	}
    }
    
    @FXML
    void configureVia(ActionEvent event) {
    	/*
    	 * opens the Bst-Auswahl gui for train station selections
    	 */
    	System.out.println("Open Via-Bst-Auswahl.fxml for Via/Halte bearbeiten");
    	try{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViaBst-Auswahl.fxml"));
    		//setzt den Controller dieser GUI
    		loader.setController(new ControllerVia(this,""));
    		Parent root1 = (Parent)loader.load();
    		Stage stage = new Stage();
    		
    		stage.setTitle("Vias und Halte bearbeiten");
    		stage.setScene(new Scene(root1));
    		stage.setResizable(false);
    		stage.show();
    	}catch(Exception e){
    		e.printStackTrace();
    		System.out.println("Cant load new window");
    	}
    }

    @FXML
    void calculateIBE(ActionEvent event) {
    	/*
    	 * calculates the IBE if all critereas are fulfilled.
    	 */
    	meldungen.clear();
    	
    	boolean criteria = true;
    	
    	try{
    		if(textFieldZuggew.getText().isEmpty()){
    			meldungen.insertText(0,"- Zuggewicht ist ein Pflichtfeld.\n");
    			System.out.println("Nummer 1");
    			criteria = false;
    		}
    		
    		anzTfz = Integer.parseInt(anzahlTfz.getText());
        	if((textFieldZuggew.getText().isEmpty() == false)){
        		if(Integer.parseInt(textFieldZuggew.getText()) < zugGewMin*anzTfz){
        			meldungen.insertText(0, "- Angegebenes Zuggewicht kleiner als das Gewicht des/r angegebenen Triebfahrzeugs/e ("+ zugGewMin*anzTfz +" to).\n");
        			System.out.println("Nummer 2");
        			criteria = false;
        		}
        	}
        	
        	
    	}catch(NumberFormatException e){
    		meldungen.insertText(0, "- Ungültige Eingabe in Zuggewicht (nur ganzzahlige Zahlen).\n");
    		System.out.println("Nummer 3");
    		criteria = false;
    	}
    	try{
    		if(textFieldZugnr.getText().isEmpty() == false){
    			Integer.parseInt(textFieldZugnr.getText());
    			System.out.println("Nummer 4");
    		}
    	}catch(NumberFormatException e){
    		meldungen.insertText(0, "- Ungültige Eingabe in Zugnummer (nur ganzzahlige Zahlen erlaubt).\n");
    		System.out.println("Nummer 5");
    		criteria = false;
    	}
    	if(textFieldZugnr.getText().length() > 5){
    		meldungen.insertText(0, "- Ungültige Eingabe in Zugnummer (max. 5 Ziffern).\n");
    		System.out.println("Nummer 6");
    		criteria = false;
    	}
    	try{
    		if(anzahlTfz.getText().isEmpty() == false){
    			Integer.parseInt(anzahlTfz.getText());
    			System.out.println("Nummer 7");
    			//criteria = false;
    		}
    	}catch(NumberFormatException e){
    		meldungen.insertText(0, "- Ungültige Eingabe in Anzahl d. Tfz (nur ganzzahlige Zahlen).\n");
    		System.out.println("Nummer 8");
    		criteria = false;
    	}
    	//TODO meldungen für tfz,tfz-kat und verkehrsart ist pflichtfeld schöner machen
    	if(dropDownTfz.getSelectionModel().getSelectedItem().toString().equals(" ")){
    		meldungen.insertText(0, "- Triebfahrzeug ist ein Pflichtfeld.\n");
    		System.out.println("Nummer 9");
    		criteria = false;
    	}
    	if(verkehrsart.getSelectionModel().getSelectedItem().toString().equals(" ")){
    		if(menuItemFahrplan2018V1.isSelected()){
    			meldungen.insertText(0, "- Marktsegment ist ein Pflichtfeld.\n");
    			System.out.println("Nummer 10");
    			criteria = false;
    		}else{
    			meldungen.insertText(0, "- Verkehrsart ist ein Pflichtfeld.\n");
    			System.out.println("Nummer 11");
    			criteria = false;
    		}
    	}
    	if(dropDownTfzKat.getSelectionModel().getSelectedItem().toString().equals(" ")){
    		meldungen.insertText(0, "- Tfz-Kategorie ist ein Pflichtfeld.\n");
    		System.out.println("Nummer 12");
    		criteria = false;
    	}
    	if(DB640von.getText().equals("")){
    		meldungen.insertText(0, "- Bst-Von ist ein Pflichtfeld.\n");
    		System.out.println("Nummer 13");
    		criteria = false;
    	}
    	if(DB640bis.getText().equals("")){
    		meldungen.insertText(0, "- Bst-Bis ist ein Pflichtfeld.\n");
    		System.out.println("Nummer 14");
    		criteria = false;
    	}
    	if(!DB640von.getText().equals("")) {
    		if(hstName.toString().toLowerCase().contains(DB640von.getText().toLowerCase()) == false){
    			meldungen.insertText(0, "- Angegebener Bst-Code bei VON Bst-Code ist ungültig.\n");
    			System.out.println("Nummer 15");
    			criteria = false;
    		}
    	}
    	if(!DB640bis.getText().equals("")) {
    		if(hstName.toString().toLowerCase().contains(DB640bis.getText().toLowerCase()) == false){
    			meldungen.insertText(0, "- Angegebener Bst-Code bei BIS Bst-Code ist ungültig.\n");
    			System.out.println("Nummer 16");
    			criteria = false;
    		}
    	}
    	if(!r_ber){
    		meldungen.insertText(0, "- Route muss zuerst berechnet werden.\n");
    		System.out.println("Nummer 17");
			criteria = false;
    	}
    	if(criteria){
    		System.out.println("berechne IBE");
    		BigDecimal zuggew = new BigDecimal(textFieldZuggew.getText());
    		
    		//erstellt das Richtige Produkt-Katalog für die IBE-Berechnung
    		switch(getMenuItemFahrplan()){
    		case "2017": 	pk17 = new ProdKat2017(this,dj.getWegstrecke(), zuggew, halte);
    						break;
    		case "2018V1": 	pk18V1 = new ProdKat2018V1(this,dj.getWegstrecke().get("Ges"), zuggew, halte);
    						break;
    		case "2018V2": 	pk18V2 = new ProdKat2018V2(this,dj.getWegstrecke(), zuggew, halte);
    						break;
    		}
    		//setzt den Parameter für die IBE-Berechnung auf true (wird für den Excel-Export benötigt)
    		ibe_ber = true;
    	}
    }

	@FXML
    void closeProgram(ActionEvent event) {
    	/*
    	 * closes the program
    	 */
    	Platform.exit();
    	System.exit(0);
    }

    @FXML
    void exportToExcel(ActionEvent event) {
    	/*
    	 * writes the data in an ecxel file and saves it if the criterias are fulfilled
    	 */
    	
    	//wenn es sich um einen Reisezug handelt, werden die Bst für Von und Bis in die Halte-Liste hinzugefügt
    	if((this.getVerkehrsart().contains("RZ")) || (this.getVerkehrsart().contains("PV")) || (this.getVerkehrsart().contains("PNV"))){
    		ArrayList<String> help = new ArrayList();
    		
    		help.add(this.getDB640von().getText());
    		for(int i = 0; i < this.halte.size(); i++){
    			help.add(this.halte.get(i));
    		}
    		help.add(this.getDB640bis().getText());
    		
    		this.halte.clear();
    		this.halte = help;
    	}
    	
    	//überprüft die Kriterien
    	boolean criteria = true;
    	if(!r_ber){
    		meldungen.insertText(0, "- Route muss zuerst berechnet werden.\n");
			criteria = false;
    	}
    	if(!ibe_ber){
    		meldungen.insertText(0, "- IBE muss zuerst berechnet werden.\n");
			criteria = false;
    	}
    	
    	//erstellt ein neues ExcelHandler Objekt und übergibt den ControllerMain
    	if(criteria){
    		new ExcelHandler(this);
    	}
    }

    @FXML
    void openAbout(ActionEvent event){
    	/*
    	 * opens the about page
    	 */
    	System.out.println("Open About");
    	try{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
    		Parent root1 = (Parent)loader.load();
    		Stage stage = new Stage();
    		
    		stage.setTitle("About");
    		stage.setScene(new Scene(root1));
    		stage.setResizable(false);
    		stage.show();
    	}catch(Exception e){
    		System.out.println("Cant load new window");
    	}
    }
    
    @FXML
    void openTut(ActionEvent event){
    	/*
    	 * opens the tutorial page
    	 */
    	System.out.println("Open Tutorial");
    	try{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("TutorialScreen.fxml"));
    		Parent root1 = (Parent)loader.load();
    		Stage stage = new Stage();
    		
    		stage.setTitle("Tutorial");
    		stage.setScene(new Scene(root1));
    		stage.setResizable(false);
    		stage.show();
    	}catch(Exception e){
    		System.out.println("Cant load new window");
    		e.printStackTrace();
    	}
    }

    @FXML
    void refreshIBE(ActionEvent event) {
    	/*
    	 * berechnet die kürzeste Route und berechnet den IBE neu
    	 */
    	shortestPath(null);
    	calculateIBE(null);
    }

    @FXML
    void setDB640Bis(ActionEvent event) {
    	/*
    	 * opens the bst-auswahl gui for station selection
    	 */
    	try{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Bst-Auswahl.fxml"));
    		loader.setController(new ControllerBstAuswahl(this, "bis"));
    		Parent root1 = (Parent)loader.load();
    		Stage stage = new Stage();
    		
    		stage.setTitle("Bst-Code BIS");
    		stage.setScene(new Scene(root1));
    		stage.setResizable(false);
    		stage.show();
    	}catch(Exception e){
    		System.out.println("Cant load new window");
    		e.printStackTrace();
    	}
    }

    @FXML
    void setDB640Von(ActionEvent event) {
    	/*
    	 * opens the bst-auswahl gui for station selection
    	 */
    	System.out.println("Open BstAuswahl for DB640VON");
    	try{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Bst-Auswahl.fxml"));
    		loader.setController(new ControllerBstAuswahl(this, "von"));
    		Parent root1 = (Parent)loader.load();
    		Stage stage = new Stage();
    		
    		stage.setTitle("Bst-Code VON");
    		stage.setScene(new Scene(root1));
    		stage.show();
    	}catch(Exception e){
    		System.out.println("Cant load new window");
    		e.printStackTrace();
    	}
    }

    @FXML
    void shortestPath(ActionEvent event) {
    	/*
    	 * berechnet die kürzeste Route falls die Kriterien erfüllt werden
    	 */
    	boolean criteria = true;
    	meldungen.clear();
    	if(DB640von.getText().equals("")){
    		meldungen.insertText(0, "- Bst-Von ist ein Pflichtfeld.\n");
    		criteria = false;
    	}
    	if(DB640bis.getText().equals("")){
    		meldungen.insertText(0, "- Bst-Bis ist ein Pflichtfeld.\n");
    		criteria = false;
    	}
    	if(!DB640von.getText().equals("")) {
    		if(hstName.toString().toLowerCase().contains(DB640von.getText().toLowerCase()) == false){
    			meldungen.insertText(0, "- Angegebener Bst-Code bei VON Bst-Code ist ungültig.\n");
    			criteria = false;
    		}
    	}
    	if(!DB640bis.getText().equals("")) {
    		if(hstName.toString().toLowerCase().contains(DB640bis.getText().toLowerCase()) == false){
    			meldungen.insertText(0, "- Angegebener Bst-Code bei BIS Bst-Code ist ungültig.\n");
    			criteria = false;
    		}
    	}
    	//erstellt das richtige Objekt für die Routenberechnung und stellt den Routenparameter für den ExcelExport
    	if(criteria){
    		System.out.println("Berechne kürzesten Weg");
    		switch(fp_per){
    		case "2017":
    			routeTable.getItems().clear();
    			dj.init17(DB640von.getText(),DB640bis.getText(),route);
    			r_ber = true;
    			break;
    		case "2018V1":
    			dj.init18(DB640von.getText(),DB640bis.getText(),route);
    			r_ber = true;
    			break;
    		case "2018V2":
    			dj.init18(DB640von.getText(),DB640bis.getText(),route);
    			r_ber = true;
    			break;
    		}
    	}
    }

    @FXML
    void switchToFahrplan2017(ActionEvent event) {
    	/*
    	 * switches the timetable and sets the right datas for this timetable
    	 */
    	if(menuItemFahrplan2017.isSelected()){
    		menuItemFahrplan2018V1.setSelected(false);
    		menuItemFahrplan2018V2.setSelected(false);
    	}
    	
    	hstName = ExcelHandler.hstName17;
    	tfzN_G = ExcelHandler.tfzN_G17;
    	tfzN_K = ExcelHandler.tfzN_K17;
    	tfzName = ExcelHandler.tfzName17;
    	zugklasse = ExcelHandler.zugklasse17;
    	
    	labelVerkehrssart.setText("Verkehrsart");
    	verkehrsart.setItems(FXCollections.observableArrayList(" ","RZ - Reisezug","GZ - Güterzug", "EWFV - Einzelwagenfernverk.", "EWNV - Einzelwagennahverk.", "DZ - Dienstzug"));
    	verkehrsart.getSelectionModel().select(" ");
    	
    	fp_per = "2017";
    	
    	meldungen.clear();
    	
    	writeLabelFahrplan();
    }

    @FXML
    void switchToFahrplan2018V1(ActionEvent event) {
    	/*
    	 * switches the timetable and sets the right datas for this timetable
    	 */
    	if(menuItemFahrplan2018V1.isSelected()){
    		menuItemFahrplan2017.setSelected(false);
    		menuItemFahrplan2018V2.setSelected(false);
    	}
    	
    	hstName = ExcelHandler.hstName18;
    	tfzN_G = ExcelHandler.tfzN_G18;
    	tfzN_K = ExcelHandler.tfzN_K18;
    	tfzName = ExcelHandler.tfzName18;
    	zugklasse = ExcelHandler.zugklasse18;
    	
    	labelVerkehrssart.setText("Marktsegment");
    	verkehrsart.setItems(FXCollections.observableArrayList(" ","1.1.*.a Eig. PV","1.1.*.b Gem. PV-FV", "1.1.*.c PNV stark", "1.1.*.d PNV schwach", 
    			"1.1.*.e GV man.","1.1.*.f GV n. man.","1.1.*.g Dienstzug"));
    	verkehrsart.getSelectionModel().select(" ");
    	
    	fp_per = "2018V1";
    	
    	meldungen.clear();
    	
    	writeLabelFahrplan();
    }

    @FXML
    void switchToFahrplan2018V2(ActionEvent event) {
    	/*
    	 * switches the timetable and sets the right datas for this timetable
    	 */
    	if(menuItemFahrplan2018V2.isSelected()){
    		menuItemFahrplan2017.setSelected(false);
    		menuItemFahrplan2018V1.setSelected(false);
    	}
    	
    	hstName = ExcelHandler.hstName18;
    	tfzN_G = ExcelHandler.tfzN_G18;
    	tfzN_K = ExcelHandler.tfzN_K18;
    	tfzName = ExcelHandler.tfzName18;
    	zugklasse = ExcelHandler.zugklasse18;
    	
    	labelVerkehrssart.setText("Verkehrsart");
    	verkehrsart.setItems(FXCollections.observableArrayList(" ","RZ - Reisezug","GZ - Güterzug", "EWFV - Einzelwagenfernverk.", "EWNV - Einzelwagennahverk.", "DZ - Dienstzug"));
    	verkehrsart.getSelectionModel().select(" ");
    	
    	fp_per = "2018V2";
    	
    	meldungen.clear();
    	
    	writeLabelFahrplan();
    }
    
    @FXML
    void selectTfzParam(ActionEvent event) {
    	/*
    	 * selects the classification of the selected traction unit
    	 */
    	String str = dropDownTfz.getValue();
    	zugGewMin = (double) tfzN_G.get(str);
    	//System.out.println(zugGewMin);
    	klass = (String) tfzN_K.get(str);
    	dropDownTfzKat.setValue(klass);
    }
    
    @FXML
    void checkGew(ActionEvent event) {
    	/*
    	 * checks the weight
    	 */
    	anzTfz = Integer.parseInt(anzahlTfz.getText());
    	//if (anzTfz == null)
    	if(Integer.parseInt(textFieldZuggew.getText()) < zugGewMin*anzTfz){
    		//System.out.println("Gewicht zu wenig");
    		meldungen.clear();
    		meldungen.insertText(0, "- Angegebenes Zuggewicht kleiner als das Gewicht des/r angegebenen Triebfahrzeugs/e ("+ zugGewMin*anzTfz +" to).\n");
    	}else{
    		meldungen.setText(null);
    	}
    }
    
    @FXML
    void changeAufl1(ActionEvent event) {
    	/*
    	 * wechselt die Auflösung (öffnet neues Fenster und schließt das jetzige)
    	 */
    	menuItemAufl2.setSelected(false);
    	System.out.println("Open Main GUI in other resolution");
    	try{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("IBECalc-Main_1024_768.fxml"));
    		loader.setController(new ControllerMain("1024x768"));
    		Parent root1 = (Parent)loader.load();
    		Stage stage = new Stage();
    		
    		Platform.setImplicitExit(false);
    		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
    			@Override
    			public void handle(WindowEvent event) {
    				Platform.exit();
    			}
    		});
    		stage.setResizable(false);
    		stage.setTitle("IBE-Calculator");
    		stage.setScene(new Scene(root1));
    		stage.show();
    	}catch(Exception e){
    		System.out.println("Cant load new window");
    		e.printStackTrace();
    	}
    	labelKm.getScene().getWindow().hide();
    }
    
    @FXML
    void changeAufl2(ActionEvent event) {
    	/*
    	 * wechselt die Auflösung (öffnet neues Fenster und schließt das jetzige)
    	 */
    	menuItemAufl1.setSelected(false);
    	System.out.println("Open Main GUI in other resolution");

    	try{
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("IBECalc-Main.fxml"));
    		loader.setController(new ControllerMain("1800x900"));
    		Parent root1 = (Parent)loader.load();
    		Stage stage = new Stage();
    		
    		Platform.setImplicitExit(false);
    		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
    			@Override
    			public void handle(WindowEvent event) {
    				Platform.exit();
    			}
    		});
    		stage.setResizable(false);
    		stage.setTitle("IBE-Calculator");
    		stage.setScene(new Scene(root1));
    		stage.show();
    	}catch(Exception e){
    		System.out.println("Cant load new window");
    		e.printStackTrace();
    	}
    	labelKm.getScene().getWindow().hide();
    }
    
    @FXML
    void initialize() {

    	
    	//setzt die richtige Auswahl für die Auflösung
    	//SplitPane wird adjustiert
    	switch(aufl){
    	case "1024x768": menuItemAufl1.setSelected(true);
    	menuItemAufl2.setSelected(false);
    	AnchorPaneLeft.maxWidthProperty().bind(SplitPaneHorizontal.widthProperty().multiply(0.456));
    	AnchorPaneRight.maxWidthProperty().bind(SplitPaneHorizontal.widthProperty().multiply(0.544));
    	break;
    	case "1800x900": menuItemAufl2.setSelected(true);
    	menuItemAufl1.setSelected(false);
    	AnchorPaneLeft.maxWidthProperty().bind(SplitPaneHorizontal.widthProperty().multiply(0.455));
    	AnchorPaneRight.maxWidthProperty().bind(SplitPaneHorizontal.widthProperty().multiply(0.545));
    	break;
    	}
    	
    	//startet das Programm mit der Fahrplanperiode entsprechend des Kalenderjahres
    	Calendar date = new GregorianCalendar();
    	int year = date.get(Calendar.YEAR);
    	switch(year){
    	case 2017: menuItemFahrplan2017.setSelected(true);
    				switchToFahrplan2017(null);
    				break;
    	case 2018: menuItemFahrplan2018V1.setSelected(true);
    				switchToFahrplan2018V1(null);
    				break;
    	case 2019: break;
    	case 2020: break;
    	}
    	
    	/*
    	 * setzt die Route entsprechend der Klasse Route
    	 */
    	tableStation.setCellValueFactory(
    			new PropertyValueFactory<Route, String>("stat"));
    	
    	tableKm.setCellValueFactory(
    			new PropertyValueFactory<Route, String>("km"));
    	
    	tableAchse.setCellValueFactory(
    			new PropertyValueFactory<Route, String>("achse"));
    	
    	tableAnmerkung.setCellValueFactory(
    			new PropertyValueFactory<Route, String>("anmerkung"));
    	
    	/*
    	 * befüllt die Drop-Downs
    	 */
    	dropDownTfzKat.setItems(FXCollections.observableArrayList(" ","A","B","C"));
    	//sorts the names of the units
    	tfzName.sort(null);
    	//sets the items for the dropdown
    	dropDownTfz.setItems(FXCollections.observableArrayList(tfzName));
    	////sets the items for the dropdown
    	zugklasse.sort(null);
    	dropDownZugklasse.setItems(FXCollections.observableArrayList(zugklasse));
    	
    	verkehrsart.getSelectionModel().select(" ");
    	dropDownTfz.getSelectionModel().select(" ");
    	dropDownTfzKat.getSelectionModel().select(" ");
    	//DB640von.setText("kek");
    	//DB640bis.setText("lel");
    	String str = "ayy";
    	//testChangeTxt(str);
    	
    	assert AnchorPaneMain != null : "fx:id=\"AnchorPaneMain\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert SplitPaneHorizontal != null : "fx:id=\"SplitPaneHorizontal\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert AnchorPaneLeft != null : "fx:id=\"AnchorPaneLeft\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert AnchorPaneVertical != null : "fx:id=\"AnchorPaneVertical\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert AnchorPaneVonBis != null : "fx:id=\"AnchorPaneVonBis\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert DB640von != null : "fx:id=\"DB640von\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert DB640bis != null : "fx:id=\"DB640bis\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert kürzesteRoute != null : "fx:id=\"kürzesteRoute\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert BstAuswahlVon != null : "fx:id=\"BstAuswahlVon\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert BstAuswahlBis != null : "fx:id=\"BstAuswahlBis\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert AnchorPaneTable != null : "fx:id=\"AnchorPaneTable\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert routeTable != null : "fx:id=\"routeTable\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert tableStation != null : "fx:id=\"tableStation\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert tableKm != null : "fx:id=\"tableKm\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert tableAchse != null : "fx:id=\"tableAchse\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert tableAnmerkung != null : "fx:id=\"tableAnmerkung\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert AnchorPaneRight != null : "fx:id=\"AnchorPaneRight\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert excelExport != null : "fx:id=\"excelExport\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert refreshButton != null : "fx:id=\"refreshButton\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert textFieldEntgeldGes != null : "fx:id=\"textFieldEntgeldGes\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert textFieldEntgeltpT != null : "fx:id=\"textFieldEntgeltpT\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert textFieldVerkehrsanr != null : "fx:id=\"textFieldVerkehrsanr\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert textFieldHalte != null : "fx:id=\"textFieldHalte\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert textFieldGBTKM != null : "fx:id=\"textFieldGBTKM\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert textFieldTfzFak != null : "fx:id=\"textFieldTfzFak\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert textFieldZugkm != null : "fx:id=\"textFieldZugkm\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert textFieldZuggew != null : "fx:id=\"textFieldZuggew\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert textFieldZugnr != null : "fx:id=\"textFieldZugnr\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert textFieldKm != null : "fx:id=\"textFieldKm\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelKm != null : "fx:id=\"labelKm\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelEnt != null : "fx:id=\"labelKm\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelZugnr != null : "fx:id=\"labelZugnr\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelZugkm != null : "fx:id=\"labelZugkm\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelTfzFak != null : "fx:id=\"labelTfzFak\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelTfzKat != null : "fx:id=\"labelTfzKat\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelTfz != null : "fx:id=\"labelTfz\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelGBTKM != null : "fx:id=\"labelGBTKM\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelHalte != null : "fx:id=\"labelHalte\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelVerkehrsanr != null : "fx:id=\"labelVerkehrsanr\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelEntgeldpT != null : "fx:id=\"labelEntgeldpT\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelEntgeldGes != null : "fx:id=\"labelEntgeldGes\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelVerkehrssart != null : "fx:id=\"labelVerkehrssart\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelZugklasse != null : "fx:id=\"labelZugklasse\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelZuggew != null : "fx:id=\"labelZuggew\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert viaHinzu != null : "fx:id=\"viaHinzu\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert deleteVia != null : "fx:id=\"deleteVia\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert ButtoncalculateIBE != null : "fx:id=\"ButtoncalculateIBE\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert dropDownTfzKat != null : "fx:id=\"dropDownTfzKat\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert logos != null : "fx:id=\"logo\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert selectedFahrplan != null : "fx:id=\"selectedFahrplan\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert selectedVersion != null : "fx:id=\"selectedVersion\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert meldungen != null : "fx:id=\"meldungen\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert dropDownTfz != null : "fx:id=\"dropDownTfz\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert anzahlTfz != null : "fx:id=\"anzahlTfz\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelAnzTfz != null : "fx:id=\"labelAnzTfz\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert verkehrsart != null : "fx:id=\"verkehrsart\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert dropDownZugklasse != null : "fx:id=\"dropDownZugklasse\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert listVias != null : "fx:id=\"listVias\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert labelVias != null : "fx:id=\"labelVias\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert deleteAllVias != null : "fx:id=\"deleteAllVias\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert viaBearbeiten != null : "fx:id=\"viaBearbeiten\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert menuBar != null : "fx:id=\"menuBar\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert menuFile != null : "fx:id=\"menuFile\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert menuItemOpen != null : "fx:id=\"menuItemOpen\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert menuItemSave != null : "fx:id=\"menuItemSave\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert menuItemClose != null : "fx:id=\"menuItemClose\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert menuFahrplanper != null : "fx:id=\"menuFahrplanper\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert menuItemFahrplan2017 != null : "fx:id=\"menuItemFahrplan2017\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert menuItemFahrplan2018V1 != null : "fx:id=\"menuItemFahrplan2018V1\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert menuItemFahrplan2018V2 != null : "fx:id=\"menuItemFahrplan2018V2\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert menuHilfe != null : "fx:id=\"menuHilfe\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert menuItemTut != null : "fx:id=\"menuItemTut\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
        assert menuItemAbout != null : "fx:id=\"menuItemAbout\" was not injected: check your FXML file 'IBECalc-Main.fxml'.";
    }
    

	private void writeLabelFahrplan() {
		/*
		 * schreibt das Label entsprechend der ausgewählten Fahrplanperiode
		 */
    	if(menuItemFahrplan2017.isSelected()){
    		selectedFahrplan.setText("Fahrplan: 2017");
    		selectedVersion.setText("Version: 1");
    	}
    	if(menuItemFahrplan2018V1.isSelected()){
    		selectedFahrplan.setText("Fahrplan: 2018");
    		selectedVersion.setText("Version: 1");
    	}
    	if(menuItemFahrplan2018V2.isSelected()){
    		selectedFahrplan.setText("Fahrplan: 2018");
    		selectedVersion.setText("Version: 2");
    	}
	}

	/*
	 * Getter und Setter Methoden dieser Klasse
	 */
	
	public String getMenuItemFahrplan(){
    	String fp = "";
    	
    	if(menuItemFahrplan2017.isSelected()){
    		fp = "2017";
    	}
    	if(menuItemFahrplan2018V1.isSelected()){
    		fp = "2018V1";
    	}
    	if(menuItemFahrplan2018V2.isSelected()){
    		fp = "2018V2";
    	}
    	return fp;
    }
	
	public String getVerkehrsart(){
		
		return verkehrsart.getSelectionModel().getSelectedItem();
		//return verkehrsart.getValue();
	}
	
	public String getTfzKat(){
		return this.dropDownTfzKat.getSelectionModel().getSelectedItem();
		//return dropDownTfzKat.getValue();
	}
	
	public TextField getDB640von(){
		
		return DB640von;
		//return verkehrsart.getValue();
	}
	
	public Button getViaHinzu(){
		
		return this.viaHinzu;
		//return verkehrsart.getValue();
	}
	
	public TextField getTextFieldKm(){
		
		return textFieldKm;
		//return verkehrsart.getValue();
	}
	
	public TextField getTextFieldGBTKM(){
		
		return textFieldGBTKM;
		//return verkehrsart.getValue();
	}
	
	public TextField getTextFieldHalte(){
		
		return textFieldHalte;
		//return verkehrsart.getValue();
	}
	
	public void setListVia(ObservableList<String> doList) {
		this.listVias = new ListView<String>(doList);
	}
	public TextField getTextFieldEntgeldGes(){
		
		return textFieldEntgeldGes;
		//return verkehrsart.getValue();
	}
	
	public TextField getTextFieldZugKm(){
		
		return textFieldZugkm;
		//return verkehrsart.getValue();
	}
	
	public TextField getTextFieldTfzFak(){
		
		return textFieldTfzFak;
		//return verkehrsart.getValue();
	}
	
	public TextField getTextFieldZugnr(){
		
		return textFieldZugnr;
		//return verkehrsart.getValue();
	}
	
	public String getChoiceBoxZugklasse(){
		
		return dropDownZugklasse.getValue();
		//return verkehrsart.getValue();
	}
	
	public String getChoiceBoxTfz(){
		
		return dropDownTfz.getValue();
		//return verkehrsart.getValue();
	}
	
	public String getZuggew(){
		
		return textFieldZuggew.getText();
		//return verkehrsart.getValue();
	}
	
	public String getAnzTfz(){
		
		return anzahlTfz.getText();
		//return verkehrsart.getValue();
	}

	public TextField getDB640bis(){
		
		return DB640bis;
		//return verkehrsart.getValue();
	}
	
	public String getFp_per(){
		
		return fp_per;
		//return verkehrsart.getValue();
	}
	
	public ListView<String> getListVia(){
		
		return listVias;
		//return verkehrsart.getValue();
	}


	public void setHalte(ArrayList halte2) {
		this.halte = halte2;
		System.out.println("Halte in Controller Main");
		for(int i = 0; i < this.halte.size(); i++){
			System.out.println("Halt in cm: " + halte.get(i));
		}
	}
	
	public ArrayList<String> getHalte() {
		return this.halte;
	}
	
	public void setVias(ArrayList via2) {
		this.vias = via2;
	}

	public void setViauHalte(ObservableList<String> viaUhalte) {
		this.viaUndHalte = viaUhalte;
		System.out.println("Vias und Halte in Controller Main");
		for(int i = 0; i < viaUndHalte.size(); i++){
			System.out.println(viaUndHalte.get(i));
		}
	}


	public void setRoute(ArrayList route) {
		this.route = route;
	}
	
	public ArrayList<String> getRoute() {
		return this.route;
	}
	
	public TableView<Route> getRouteTable(){
		return routeTable;
	}
	
	public TableColumn getTCBstCode(){
		return this.tableStation;
	}
	
	public TableColumn getTCBez(){
		return this.tableAnmerkung;
	}
	
	public TableColumn getTCKm(){
		return this.tableKm;
	}
	
	public TableColumn getTCAchse(){
		return this.tableAchse;
	}

	/*public static void refreshHst(String hst_ausg) {
		String test = hst_ausg;
		DB640von.setText(test);
	}*/
}

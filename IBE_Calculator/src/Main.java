import java.io.IOException;

import javafx.application.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;

public class Main extends Application{
	
	public static Boolean isSplashLoaded = false;
	public static Boolean data_fin = false;
	public static Stage parentWindow;
	Stage stage = new Stage();
	
	@Override
	public void start(final Stage primaryStage) throws Exception{
		/*
		 * öffnet den Loading Screen und startet die Dateneinlesung
		 */
		try{
			parentWindow = primaryStage;
			
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("IBE_LoadScreen.fxml"));
    		loader.setController(new ControllerLoadScreen());
    		Parent root1 = (Parent)loader.load();
    		
    		//wenn die GUI angezeigt wird, wird die Dateneinlesung gestartet
    		stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
    			@Override
    			public void handle(WindowEvent window){
    				Platform.runLater(new Runnable(){
    					@Override
    					public void run(){
    						readData();
    						data_fin = true;
    					}
    				});
    			}
    		});
    		
    		//wird der Close-Button gefrückt, schließt sich das gesamte Programm, nicht nur das Fenster
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

	}
	
	
	public void readData() {
		/*
		 * erstellt ein ExcelHandler Objekt und ruft alle Methoden auf
		 * die Daten von den Excel-Files einlesen.
		 * 
		 * Wenn diese Daten fertig eingelesen wurden, wird die Main-Maske angezeigt.
		 */
		
		//ExcelHandler-Objekt wird erstellt
	    ExcelHandler eh = new ExcelHandler();
	    	
	    //Methoden, welche Daten einlesen werden aufgerufen
	    eh.readExcelHst17();
		eh.readExcelHst18();
			
		eh.readExcelTfzAll18();
		eh.readExcelTfzName18();
		eh.readExcelTfzGew18();
		eh.readExcelTfzKlass18();
		eh.readExcelZugklasse18();
			
		eh.readExcelTfzAll17();
		eh.readExcelTfzName17();
		eh.readExcelTfzGew17();
		eh.readExcelTfzKlass17();
		eh.readExcelZugklasse17();
			
		eh.readStatPr17();
		eh.readStatPr18();
			
		eh.readBtkm17();
		eh.readBtkm18V2();
			
		eh.readKatPr17();
		eh.readKatPr18();
			
		eh.readVerkehrsanreiz17();
			
		eh.readKmPr18V1();
			
		eh.readBstn17();
		eh.readBstn18();
			
		eh.readStrkat18V2();
		eh.readStrkat17();
			
		eh.readBstVerbindungen17();
		eh.readBstVerbindungen18();
		
		data_fin = true;
		
		//wenn die Daten eingelesen wurden öffnet sich die Main-Maske
		if(data_fin){
			FXMLLoader loader = new FXMLLoader(getClass().getResource("IBECalc-Main_1024_768.fxml"));
    		loader.setController(new ControllerMain("1024x768"));
    		Parent root1;
			try {
				root1 = (Parent)loader.load();
				stage.getScene().setRoot(root1);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
	}


	public static void main(String[] args){
		//startet das Programm
		launch(args);
	}
}

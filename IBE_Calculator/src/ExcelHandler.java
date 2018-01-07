import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.scene.GroupBuilder;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelHandler {
	String von;
	String bis;
	InputStream file;
	
	static ArrayList hst_k17 = new ArrayList();
	static ArrayList hst_l17 = new ArrayList();
	static ArrayList hst_c17 = new ArrayList();
	static Set hstName17 = Collections.synchronizedSet(new HashSet());
	static TreeMap<String,String> hst_tm17 = new TreeMap();
	
	static ArrayList hst_k18 = new ArrayList();
	static ArrayList hst_l18 = new ArrayList();
	static ArrayList hst_c18 = new ArrayList();
	static Set hstName18 = Collections.synchronizedSet(new HashSet());
	static TreeMap<String,String> hst_tm18 = new TreeMap();
	
	//TFZ 2018
	static ArrayList tfz18 = new ArrayList();
	static ArrayList<String> tfzName18 = new ArrayList();
	static ArrayList tfzGew18 = new ArrayList();
	static TreeMap tfzN_G18 = new TreeMap();
	static ArrayList tfzKlass18 = new ArrayList();
	static TreeMap tfzN_K18 = new TreeMap();
	
	//TFZ 2017
	static ArrayList tfz17 = new ArrayList();
	static ArrayList<String> tfzName17 = new ArrayList();
	static ArrayList tfzGew17 = new ArrayList();
	static TreeMap tfzN_G17 = new TreeMap();
	static ArrayList tfzKlass17 = new ArrayList();
	static TreeMap tfzN_K17 = new TreeMap();
	
	static ArrayList zugklasse18 = new ArrayList();
	static ArrayList zugklasse17 = new ArrayList();
	
	static TreeMap<String,BigDecimal> stationen17 = new TreeMap();
	static TreeMap<String,BigDecimal> stationen18 = new TreeMap();
	
	//Daten vom Produktkatalog 2017
	static TreeMap<String,BigDecimal> pk17RZ = new TreeMap();
	static TreeMap<String,BigDecimal> pk17GZ = new TreeMap();
	static TreeMap<String,BigDecimal> pk17EWFV = new TreeMap();
	static TreeMap<String,BigDecimal> pk17DZ = new TreeMap();
	static TreeMap<String,BigDecimal> pk17EWNV = new TreeMap();
	static BigDecimal pk17Btk;
	
	//Daten vom Produktkatalog 2018V1
	static TreeMap<String,BigDecimal> pk18V1Btkm = new TreeMap();
	static TreeMap<String,BigDecimal> pk18V1Zugkm = new TreeMap();
	
	static TreeMap<String,BigDecimal> pk17Kat = new TreeMap();
	static TreeMap<String,BigDecimal> pk18Kat = new TreeMap();
	
	//Daten vom Produktkatalog 2018 V2
	static TreeMap<String,BigDecimal> pk18V2RZ = new TreeMap();
	static TreeMap<String,BigDecimal> pk18V2GZ = new TreeMap();
	static TreeMap<String,BigDecimal> pk18V2EWFV = new TreeMap();
	static TreeMap<String,BigDecimal> pk18V2DZ = new TreeMap();
	static TreeMap<String,BigDecimal> pk18V2EWNV = new TreeMap();
	static BigDecimal pk18V2Btk;
	
	//werden nicht verwendet
	TreeMap<String,BigDecimal> stationpr17 = new TreeMap();
	static TreeMap<String,BigDecimal> stationpr18 = new TreeMap();
	
	static TreeMap<String,BigDecimal> verkAnreiz17 = new TreeMap();
	
	static TreeMap<String, String> strKat17 = new TreeMap();
	static TreeMap<String,String> strKat18 = new TreeMap();
	
	static TreeMap<String,BigDecimal> bstnFR1_17 = new TreeMap();
	static TreeMap<String,BigDecimal> bstnFR2_17 = new TreeMap();
	static TreeMap<String,BigDecimal> bstn17 = new TreeMap();
	static ArrayList bstnBST17 = new ArrayList();
	
	static TreeMap<String,BigDecimal> bstnFR1_18 = new TreeMap();
	static TreeMap<String,BigDecimal> bstnFR2_18 = new TreeMap();
	static TreeMap<String,BigDecimal> bstn18 = new TreeMap();
	static ArrayList bstnBST18 = new ArrayList();
	
	private ControllerMain cm;
	
	public ExcelHandler() {

	}
	
	public ExcelHandler(ControllerMain c) {
		this.cm = c;
		switch(this.cm.getFp_per()){
		case "2017": 	saveFile17();
		break;
		case "2018V1": 	saveFile18V1();
		break;
		case "2018V2": 	saveFile18V2();
		break;
		}
	}

	public void saveFile17() {
		/*
		 * speichert den ExcelExport.
		 */
		this.von = this.cm.getDB640von().getText();
		this.bis = this.cm.getDB640bis().getText();
		TreeMap<String,BigDecimal> statpr = new TreeMap();
		BigDecimal btk = null;
		statpr = stationen17;
		btk = pk17Btk;

		
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFSheet sheet = workbook.createSheet("IBE " + von + " - " + bis);
		
		
		//schreibt Route in Excel-File
		Map<Integer,Object[]> dataRoute = new TreeMap<Integer, Object[]>();
		dataRoute.put(1,new Object[]{"BST-Code","Bezeichnung","Entf in km","Achse"});
		//System.out.println("größe excel data " + this.cm.dj.excelData.size());
		for(int i = 0; i < this.cm.dj.excelData.size(); i++){
			dataRoute.put(i+2,new Object[]{this.cm.getTCBstCode().getCellData(i),this.cm.getTCBez().getCellData(i),
					this.cm.getTCKm().getCellData(i),this.cm.getTCAchse().getCellData(i)});
		}
		
		Set<Integer> keysetRoute = dataRoute.keySet();
		
		//schreibt Halte in Excel-File
		Map<Integer,Object[]> dataHalte = new TreeMap<Integer, Object[]>();
		dataHalte.put(1,new Object[]{"Halte","Preis in €"});
		System.out.println(this.cm.getHalte().size());
		int x;
		for(x = 0; x < this.cm.getHalte().size(); x++){
			dataHalte.put(x+2,new Object[]{this.cm.getHalte().get(x),statpr.get(this.cm.getHalte().get(x)).toString()});
			System.out.println(this.cm.getHalte().get(x) + " - " + statpr.get(this.cm.getHalte().get(x)).toString());
		}
		dataHalte.put(x+2, new Object[]{"Summe",this.cm.getTextFieldHalte().getText()});
		
		Set<Integer> keysetHalte = dataHalte.keySet();
		
		//schreibt Achsen+Kosten in Excel-File
		Map<Integer,Object[]> dataAchsen = new TreeMap<Integer, Object[]>();
		dataAchsen.put(1,new Object[]{"Achse","km","Kosten in €"});
		dataAchsen.put(2,new Object[]{"Westachse",this.cm.pk17.getWA()[0],this.cm.pk17.getWA()[1]});
		dataAchsen.put(3,new Object[]{"Brennerachse",this.cm.pk17.getBR()[0],this.cm.pk17.getBR()[1]});
		dataAchsen.put(4,new Object[]{"Sonst. internat. Achsen",this.cm.pk17.getIA()[0],this.cm.pk17.getIA()[1]});
		dataAchsen.put(5,new Object[]{"Sonst. Kernnetz",this.cm.pk17.getKN()[0],this.cm.pk17.getKN()[1]});
		dataAchsen.put(6,new Object[]{"Ergänzungsnetz",this.cm.pk17.getEN()[0],this.cm.pk17.getEN()[1]});
		dataAchsen.put(7,new Object[]{"Gesamt",this.cm.pk17.getGes()[0],this.cm.pk17.getGes()[1]});
		
		Set<Integer> keysetAchsen = dataAchsen.keySet();
		
		//schreibt GBTKM in Excel-File
		Map<Integer,Object[]> dataBTKM = new TreeMap<Integer, Object[]>();
		dataBTKM.put(1,new Object[]{"Zuggewicht in to","btkm","Preis in €"});
		dataBTKM.put(2, new Object[]{this.cm.getZuggew(),btk.setScale(6, BigDecimal.ROUND_UP).toString(),this.cm.getTextFieldGBTKM().getText()});
		
		Set<Integer> keysetBTKM = dataBTKM.keySet();
		
		//schreibt Tfz-Zu/Abschlag in Excel-File
		Map<Integer,Object[]> dataTfz = new TreeMap<Integer, Object[]>();
		dataTfz.put(1,new Object[]{"Tfz","Tfz-Faktor","Entgelt pro Zugkm","Zu/Abschlag in €"});
		dataTfz.put(2, new Object[]{this.cm.getChoiceBoxTfz(),this.cm.getTfzKat(),this.cm.getTextFieldTfzFak().getText(),this.cm.pk17.getEntgTfz()});
				
		Set<Integer> keysetTfz = dataTfz.keySet();
		
		//schreibt IBE-Teil und Gesamtergebnis in Excel-File
		Map<Integer,Object[]> dataIBE = new TreeMap<Integer, Object[]>();
		dataIBE.put(1,new Object[]{"Komponente","Preis in €"});
		dataIBE.put(2,new Object[]{"IBE Zugkm", this.cm.pk17.getGes()[1]});
		dataIBE.put(3,new Object[]{"IBE GBTKM", this.cm.getTextFieldGBTKM().getText()});
		dataIBE.put(4,new Object[]{"IBE Halte", this.cm.getTextFieldHalte().getText()});
		dataIBE.put(5,new Object[]{"IBE Zu/Abschlag", this.cm.pk17.getEntgTfz()});
		dataIBE.put(6,new Object[]{"Gesamt", this.cm.getTextFieldEntgeldGes().getText()});
				
		Set<Integer> keysetIBE = dataIBE.keySet();
		
		//schreibt Leitweg in Excel-File
		Map<Integer,Object[]> dataLW = new TreeMap<Integer, Object[]>();
		dataLW.put(1,new Object[]{"Leitweg",""});
		dataLW.put(2,new Object[]{this.cm.getDB640von().getText(),hst_tm17.get(this.cm.getDB640von().getText())} );
		int y = 3;
		try{
			for(x = 0; x < this.cm.getRoute().size(); x++){
				dataLW.put(y,new Object[]{this.cm.getRoute().get(x).toString(),hst_tm17.get(this.cm.getRoute().get(x).toString())});
				y++;
			}
		}catch(Exception e){
			
		}
		dataLW.put(y,new Object[]{this.cm.getDB640bis().getText(),hst_tm17.get(this.cm.getDB640bis().getText())} );
						
		Set<Integer> keysetLW = dataLW.keySet();
		
		//schreibt Verkehrsart in Excel-File
		Map<Integer,Object[]> dataVK = new TreeMap<Integer, Object[]>();
		dataVK.put(1,new Object[]{"Verkehrsart"});
		dataVK.put(2,new Object[]{this.cm.getVerkehrsart()});
		
		Set<Integer> keysetVK = dataVK.keySet();
		
		//schreibt Zugnummer in Excel-File
		Map<Integer,Object[]> dataZugnr = new TreeMap<Integer, Object[]>();
		dataZugnr.put(1,new Object[]{"Zugnummer"});
		dataZugnr.put(2,new Object[]{this.cm.getTextFieldZugnr().getText()});
				
		Set<Integer> keysetZugnr = dataZugnr.keySet();
		
		//schreibt Zugklasse in Excel-File
		Map<Integer,Object[]> dataZugk = new TreeMap<Integer, Object[]>();
		dataZugk.put(1,new Object[]{"Zugklasse"});
		dataZugk.put(2,new Object[]{this.cm.getChoiceBoxZugklasse()});
						
		Set<Integer> keysetZugk = dataZugk.keySet();
		
		int rownum = 0;
		Row[] rows = new Row[500];
		for(int i = 0; i < 500; i++){
			rows[i] = sheet.createRow(rownum++);
		}
		
		int numb = 0;
		for(Integer key : keysetRoute){
			Object[] objArr = dataRoute.get(key);
			int cellnum = 0;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 17;
		for(Integer key : keysetHalte){
			Object[] objArr = dataHalte.get(key);
			int cellnum = 5;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 0;
		for(Integer key : keysetAchsen){
			Object[] objArr = dataAchsen.get(key);
			int cellnum = 5;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 9;
		for(Integer key : keysetBTKM){
			Object[] objArr = dataBTKM.get(key);
			int cellnum = 5;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 13;
		for(Integer key : keysetTfz){
			Object[] objArr = dataTfz.get(key);
			int cellnum = 5;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 0;
		for(Integer key : keysetIBE){
			Object[] objArr = dataIBE.get(key);
			int cellnum = 10;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 0;
		for(Integer key : keysetVK){
			Object[] objArr = dataVK.get(key);
			int cellnum = 13;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 4;
		for(Integer key : keysetZugnr){
			Object[] objArr = dataZugnr.get(key);
			int cellnum = 13;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 8;
		for(Integer key : keysetZugk){
			Object[] objArr = dataZugk.get(key);
			int cellnum = 13;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 12;
		for(Integer key : keysetLW){
			Object[] objArr = dataLW.get(key);
			int cellnum = 13;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		try{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialFileName(von + "-" + bis);
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Excel File", "*.xlsx"));
			File file = fileChooser.showSaveDialog(null);
			
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
			System.out.println("Daten in Excel geschrieben");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void saveFile18V2() {
		/*
		 * speichert den ExcelExport.
		 */
		this.von = this.cm.getDB640von().getText();
		this.bis = this.cm.getDB640bis().getText();
		TreeMap<String,BigDecimal> statpr = new TreeMap();
		BigDecimal btk = null;
		statpr = stationen18;
		btk = pk18V2Btk;

		
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFSheet sheet = workbook.createSheet("IBE " + von + " - " + bis);
		
		
		//schreibt Route in Excel-File
		Map<Integer,Object[]> dataRoute = new TreeMap<Integer, Object[]>();
		dataRoute.put(1,new Object[]{"BST-Code","Bezeichnung","Entf in km","Achse"});
		//System.out.println("größe excel data " + this.cm.dj.excelData.size());
		for(int i = 0; i < this.cm.dj.excelData.size(); i++){
			dataRoute.put(i+2,new Object[]{this.cm.getTCBstCode().getCellData(i),this.cm.getTCBez().getCellData(i),
					this.cm.getTCKm().getCellData(i),this.cm.getTCAchse().getCellData(i)});
		}
		
		Set<Integer> keysetRoute = dataRoute.keySet();
		
		//schreibt Halte in Excel-File
		Map<Integer,Object[]> dataHalte = new TreeMap<Integer, Object[]>();
		dataHalte.put(1,new Object[]{"Halte","Preis in €"});
		System.out.println(this.cm.getHalte().size());
		int x;
		for(x = 0; x < this.cm.getHalte().size(); x++){
			dataHalte.put(x+2,new Object[]{this.cm.getHalte().get(x),statpr.get(this.cm.getHalte().get(x)).toString()});
			System.out.println(this.cm.getHalte().get(x) + " - " + statpr.get(this.cm.getHalte().get(x)).toString());
		}
		dataHalte.put(x+2, new Object[]{"Summe",this.cm.getTextFieldHalte().getText()});
		
		Set<Integer> keysetHalte = dataHalte.keySet();
		
		//schreibt Achsen+Kosten in Excel-File
		Map<Integer,Object[]> dataAchsen = new TreeMap<Integer, Object[]>();
		dataAchsen.put(1,new Object[]{"Achse","km","Kosten in €"});
		dataAchsen.put(2,new Object[]{"Westachse",this.cm.pk18V2.getWA()[0],this.cm.pk18V2.getWA()[1]});
		dataAchsen.put(3,new Object[]{"Brennerachse",this.cm.pk18V2.getBR()[0],this.cm.pk18V2.getBR()[1]});
		dataAchsen.put(4,new Object[]{"Sonst. internat. Achsen",this.cm.pk18V2.getIA()[0],this.cm.pk18V2.getIA()[1]});
		dataAchsen.put(5,new Object[]{"Sonst. Kernnetz",this.cm.pk18V2.getKN()[0],this.cm.pk18V2.getKN()[1]});
		dataAchsen.put(6,new Object[]{"Ergänzungsnetz",this.cm.pk18V2.getEN()[0],this.cm.pk18V2.getEN()[1]});
		dataAchsen.put(7,new Object[]{"Gesamt",this.cm.pk18V2.getGes()[0],this.cm.pk18V2.getGes()[1]});
		
		Set<Integer> keysetAchsen = dataAchsen.keySet();
		
		//schreibt GBTKM in Excel-File
		Map<Integer,Object[]> dataBTKM = new TreeMap<Integer, Object[]>();
		dataBTKM.put(1,new Object[]{"Zuggewicht in to","btkm","Preis in €"});
		dataBTKM.put(2, new Object[]{this.cm.getZuggew(),btk.setScale(6, BigDecimal.ROUND_UP).toString(),this.cm.getTextFieldGBTKM().getText()});
		
		Set<Integer> keysetBTKM = dataBTKM.keySet();
		
		//schreibt Tfz-Zu/Abschlag in Excel-File
		Map<Integer,Object[]> dataTfz = new TreeMap<Integer, Object[]>();
		dataTfz.put(1,new Object[]{"Tfz","Tfz-Faktor","Entgelt pro Zugkm","Zu/Abschlag in €"});
		dataTfz.put(2, new Object[]{this.cm.getChoiceBoxTfz(),this.cm.getTfzKat(),this.cm.getTextFieldTfzFak().getText(),this.cm.pk18V2.getEntgTfz()});
				
		Set<Integer> keysetTfz = dataTfz.keySet();
		
		//schreibt IBE-Teil und Gesamtergebnis in Excel-File
		Map<Integer,Object[]> dataIBE = new TreeMap<Integer, Object[]>();
		dataIBE.put(1,new Object[]{"Komponente","Preis in €"});
		dataIBE.put(2,new Object[]{"IBE Zugkm", this.cm.pk18V2.getGes()[1]});
		dataIBE.put(3,new Object[]{"IBE GBTKM", this.cm.getTextFieldGBTKM().getText()});
		dataIBE.put(4,new Object[]{"IBE Halte", this.cm.getTextFieldHalte().getText()});
		dataIBE.put(5,new Object[]{"IBE Zu/Abschlag", this.cm.pk18V2.getEntgTfz()});
		dataIBE.put(6,new Object[]{"Gesamt", this.cm.getTextFieldEntgeldGes().getText()});
				
		Set<Integer> keysetIBE = dataIBE.keySet();
		
		//schreibt Leitweg in Excel-File
		Map<Integer,Object[]> dataLW = new TreeMap<Integer, Object[]>();
		dataLW.put(1,new Object[]{"Leitweg",""});
		dataLW.put(2,new Object[]{this.cm.getDB640von().getText(),hst_tm18.get(this.cm.getDB640von().getText())} );
		int y = 3;
		try{
			for(x = 0; x < this.cm.getRoute().size(); x++){
				dataLW.put(y,new Object[]{this.cm.getRoute().get(x).toString(),hst_tm18.get(this.cm.getRoute().get(x).toString())});
				y++;
			}
		}catch(Exception e){
			
		}
		dataLW.put(y,new Object[]{this.cm.getDB640bis().getText(),hst_tm18.get(this.cm.getDB640bis().getText())} );
						
		Set<Integer> keysetLW = dataLW.keySet();
		
		//schreibt Verkehrsart in Excel-File
		Map<Integer,Object[]> dataVK = new TreeMap<Integer, Object[]>();
		dataVK.put(1,new Object[]{"Verkehrsart"});
		dataVK.put(2,new Object[]{this.cm.getVerkehrsart()});
		
		Set<Integer> keysetVK = dataVK.keySet();
		
		//schreibt Zugnummer in Excel-File
		Map<Integer,Object[]> dataZugnr = new TreeMap<Integer, Object[]>();
		dataZugnr.put(1,new Object[]{"Zugnummer"});
		dataZugnr.put(2,new Object[]{this.cm.getTextFieldZugnr().getText()});
				
		Set<Integer> keysetZugnr = dataZugnr.keySet();
		
		//schreibt Zugklasse in Excel-File
		Map<Integer,Object[]> dataZugk = new TreeMap<Integer, Object[]>();
		dataZugk.put(1,new Object[]{"Zugklasse"});
		dataZugk.put(2,new Object[]{this.cm.getChoiceBoxZugklasse()});
						
		Set<Integer> keysetZugk = dataZugk.keySet();
		
		int rownum = 0;
		Row[] rows = new Row[500];
		for(int i = 0; i < 500; i++){
			rows[i] = sheet.createRow(rownum++);
		}
		
		int numb = 0;
		for(Integer key : keysetRoute){
			Object[] objArr = dataRoute.get(key);
			int cellnum = 0;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 17;
		for(Integer key : keysetHalte){
			Object[] objArr = dataHalte.get(key);
			int cellnum = 5;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 0;
		for(Integer key : keysetAchsen){
			Object[] objArr = dataAchsen.get(key);
			int cellnum = 5;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 9;
		for(Integer key : keysetBTKM){
			Object[] objArr = dataBTKM.get(key);
			int cellnum = 5;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 13;
		for(Integer key : keysetTfz){
			Object[] objArr = dataTfz.get(key);
			int cellnum = 5;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 0;
		for(Integer key : keysetIBE){
			Object[] objArr = dataIBE.get(key);
			int cellnum = 10;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 0;
		for(Integer key : keysetVK){
			Object[] objArr = dataVK.get(key);
			int cellnum = 13;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 4;
		for(Integer key : keysetZugnr){
			Object[] objArr = dataZugnr.get(key);
			int cellnum = 13;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 8;
		for(Integer key : keysetZugk){
			Object[] objArr = dataZugk.get(key);
			int cellnum = 13;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 12;
		for(Integer key : keysetLW){
			Object[] objArr = dataLW.get(key);
			int cellnum = 13;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		try{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialFileName(von + "-" + bis);
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Excel File", "*.xlsx"));
			File file = fileChooser.showSaveDialog(null);
			
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
			System.out.println("Daten in Excel geschrieben");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void saveFile18V1(){
		this.von = this.cm.getDB640von().getText();
		this.bis = this.cm.getDB640bis().getText();
		TreeMap<String,BigDecimal> statpr = new TreeMap();
		TreeMap<String,BigDecimal> btkm = null;
		TreeMap<String,BigDecimal> zugkm = null;
		statpr = stationen18;
		btkm = pk18V1Btkm;
		zugkm = pk18V1Zugkm;
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFSheet sheet = workbook.createSheet("IBE " + von + " - " + bis);
		
		//schreibt Route in Excel-File
		Map<Integer,Object[]> dataRoute = new TreeMap<Integer, Object[]>();
		dataRoute.put(1,new Object[]{"BST-Code","Bezeichnung","Entf in km","Achse"});
		//System.out.println("größe excel data " + this.cm.dj.excelData.size());
		for(int i = 0; i < this.cm.dj.excelData.size(); i++){
			dataRoute.put(i+2,new Object[]{this.cm.getTCBstCode().getCellData(i),this.cm.getTCBez().getCellData(i),
					this.cm.getTCKm().getCellData(i),this.cm.getTCAchse().getCellData(i)});
		}
				
		Set<Integer> keysetRoute = dataRoute.keySet();
				
		//schreibt Halte in Excel-File
		Map<Integer,Object[]> dataHalte = new TreeMap<Integer, Object[]>();
		dataHalte.put(1,new Object[]{"Halte","Preis in €"});
		System.out.println(this.cm.getHalte().size());
		int x;
		for(x = 0; x < this.cm.getHalte().size(); x++){
			dataHalte.put(x+2,new Object[]{this.cm.getHalte().get(x),statpr.get(this.cm.getHalte().get(x)).toString()});
			System.out.println(this.cm.getHalte().get(x) + " - " + statpr.get(this.cm.getHalte().get(x)).toString());
		}
		dataHalte.put(x+2, new Object[]{"Summe",this.cm.getTextFieldHalte().getText()});
				
		Set<Integer> keysetHalte = dataHalte.keySet();
		
		//schreibt GBTKM in Excel-File
		Map<Integer,Object[]> dataBTKM = new TreeMap<Integer, Object[]>();
		dataBTKM.put(1,new Object[]{"Zuggewicht in to","btkm","Preis in €"});
		dataBTKM.put(2, new Object[]{this.cm.getZuggew(),btkm.get(this.cm.getVerkehrsart()).setScale(6, BigDecimal.ROUND_UP).toString(),this.cm.getTextFieldGBTKM().getText()});
		
		Set<Integer> keysetBTKM = dataBTKM.keySet();
		
		//schreibt GBTKM in Excel-File
		Map<Integer,Object[]> dataZugkm = new TreeMap<Integer, Object[]>();
		dataZugkm.put(1,new Object[]{"Kilometer","zugkm","Preis in €"});
		dataZugkm.put(2, new Object[]{this.cm.getTextFieldKm().getText(),btkm.get(this.cm.getVerkehrsart()).setScale(6, BigDecimal.ROUND_UP).toString(),this.cm.getTextFieldZugKm().getText()});
		
		Set<Integer> keysetZugkm = dataZugkm.keySet();
		
		//schreibt Tfz-Zu/Abschlag in Excel-File
		Map<Integer,Object[]> dataTfz = new TreeMap<Integer, Object[]>();
		dataTfz.put(1,new Object[]{"Tfz","Tfz-Faktor","Entgelt pro Zugkm","Zu/Abschlag in €"});
		dataTfz.put(2, new Object[]{this.cm.getChoiceBoxTfz(),this.cm.getTfzKat(),this.cm.getTextFieldTfzFak().getText(),this.cm.pk18V1.getEntgTfz()});
						
		Set<Integer> keysetTfz = dataTfz.keySet();
				
		//schreibt IBE-Teil und Gesamtergebnis in Excel-File
		Map<Integer,Object[]> dataIBE = new TreeMap<Integer, Object[]>();
		dataIBE.put(1,new Object[]{"Komponente","Preis in €"});
		dataIBE.put(2,new Object[]{"IBE Zugkm", this.cm.pk18V1.getGes()[1]});
		dataIBE.put(3,new Object[]{"IBE GBTKM", this.cm.getTextFieldGBTKM().getText()});
		dataIBE.put(4,new Object[]{"IBE Halte", this.cm.getTextFieldHalte().getText()});
		dataIBE.put(5,new Object[]{"IBE Zu/Abschlag", this.cm.pk18V1.getEntgTfz()});
		dataIBE.put(6,new Object[]{"Gesamt", this.cm.getTextFieldEntgeldGes().getText()});
						
		Set<Integer> keysetIBE = dataIBE.keySet();
				
		//schreibt Leitweg in Excel-File
		Map<Integer,Object[]> dataLW = new TreeMap<Integer, Object[]>();
		dataLW.put(1,new Object[]{"Leitweg",""});
		dataLW.put(2,new Object[]{this.cm.getDB640von().getText(),hst_tm18.get(this.cm.getDB640von().getText())} );
		int y = 3;
		try{
			for(x = 0; x < this.cm.getRoute().size(); x++){
				dataLW.put(y,new Object[]{this.cm.getRoute().get(x).toString(),hst_tm18.get(this.cm.getRoute().get(x).toString())});
				y++;
			}
		}catch(Exception e){
					
		}
		dataLW.put(y,new Object[]{this.cm.getDB640bis().getText(),hst_tm18.get(this.cm.getDB640bis().getText())} );
								
		Set<Integer> keysetLW = dataLW.keySet();
				
		//schreibt Verkehrsart in Excel-File
		Map<Integer,Object[]> dataVK = new TreeMap<Integer, Object[]>();
		dataVK.put(1,new Object[]{"Verkehrsart"});
		dataVK.put(2,new Object[]{this.cm.getVerkehrsart()});
				
		Set<Integer> keysetVK = dataVK.keySet();
				
		//schreibt Zugnummer in Excel-File
		Map<Integer,Object[]> dataZugnr = new TreeMap<Integer, Object[]>();
		dataZugnr.put(1,new Object[]{"Zugnummer"});
		dataZugnr.put(2,new Object[]{this.cm.getTextFieldZugnr().getText()});
						
		Set<Integer> keysetZugnr = dataZugnr.keySet();
				
		//schreibt Zugklasse in Excel-File
		Map<Integer,Object[]> dataZugk = new TreeMap<Integer, Object[]>();
		dataZugk.put(1,new Object[]{"Zugklasse"});
		dataZugk.put(2,new Object[]{this.cm.getChoiceBoxZugklasse()});
								
		Set<Integer> keysetZugk = dataZugk.keySet();
		
		int rownum = 0;
		Row[] rows = new Row[500];
		for(int i = 0; i < 500; i++){
			rows[i] = sheet.createRow(rownum++);
		}
		
		int numb = 0;
		for(Integer key : keysetRoute){
			Object[] objArr = dataRoute.get(key);
			int cellnum = 0;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 12;
		for(Integer key : keysetHalte){
			Object[] objArr = dataHalte.get(key);
			int cellnum = 5;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 0;
		for(Integer key : keysetZugkm){
			Object[] objArr = dataZugkm.get(key);
			int cellnum = 5;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 4;
		for(Integer key : keysetBTKM){
			Object[] objArr = dataBTKM.get(key);
			int cellnum = 5;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 8;
		for(Integer key : keysetTfz){
			Object[] objArr = dataTfz.get(key);
			int cellnum = 5;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 0;
		for(Integer key : keysetIBE){
			Object[] objArr = dataIBE.get(key);
			int cellnum = 10;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 0;
		for(Integer key : keysetVK){
			Object[] objArr = dataVK.get(key);
			int cellnum = 13;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 4;
		for(Integer key : keysetZugnr){
			Object[] objArr = dataZugnr.get(key);
			int cellnum = 13;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 8;
		for(Integer key : keysetZugk){
			Object[] objArr = dataZugk.get(key);
			int cellnum = 13;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		numb = 12;
		for(Integer key : keysetLW){
			Object[] objArr = dataLW.get(key);
			int cellnum = 13;
			for(Object obj : objArr){
				Cell cell = rows[numb].createCell(cellnum++);
				if(obj instanceof String){
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}	
			}
			numb++;
		}
		
		try{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialFileName(von + "-" + bis);
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Excel File", "*.xlsx"));
			File file = fileChooser.showSaveDialog(null);
			
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
			System.out.println("Daten in Excel geschrieben");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*public static void openFile() {
		FileChooser fileChooser = new FileChooser();
		// fileChooser.setInitialFileName(von + "-" + bis);
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Excel Files", "*.xlsx"),
				new ExtensionFilter("All Files", "*.*"));
		File file = fileChooser.showOpenDialog(null);
	}*/

	public void readExcelHst17() {
		/*
		 * 
		 * reads all stations from an excel file and writes them in a collection
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("DB6402017_2.xlsx");

			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:

						if ((cell.getStringCellValue().equals("DB64_NAME"))
								|| (cell.getStringCellValue().equals("DB64_KURZ"))
								|| (cell.getStringCellValue().equals("Kombination"))) {
							// System.out.println("unnedig");
						} else {
							try{
								String kurz = cell.getStringCellValue();
								String lang = cellIterator.next().getStringCellValue();

								hst_k17.add(kurz);
								hst_l17.add(lang);
								hst_c17.add(kurz + " -  " + lang);
								hstName17.add(lang);
							}catch(Exception e){
								
							}
						}
					}
				}
			}
			file.close();
			
			for(int i = 0; i < hst_k17.size();i++){
				String kname = (String) hst_k17.get(i);
				String lname = (String) hst_l17.get(i);
				
				hst_tm17.put(lname,kname);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		/*Set set = hst_tm17.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + " - " + me.getValue());
		}*/
	}
	
	public void readExcelHst18() {
		/*
		 * 
		 * reads all stations from an excel file and writes them in a collection
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("DB6402018_2.xlsx");

			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:

						if ((cell.getStringCellValue().equals("DB64_NAME"))
								|| (cell.getStringCellValue().equals("DB64_KURZ"))
								|| (cell.getStringCellValue().equals("Kombination"))) {
							// System.out.println("unnedig");
						} else {
							try{
								// System.out.println(cell.getStringCellValue() + "
								// - " + cellIterator.next().getStringCellValue());
								String kurz = cell.getStringCellValue();
								String lang = cellIterator.next().getStringCellValue();
								// hst.put(cell.getStringCellValue(),
								// cellIterator.next().getStringCellValue());
								// hst.put(kurz,lang);
								// System.out.println(kurz + " - " + lang);
								hst_k18.add(kurz);
								hst_l18.add(lang);
								hst_c18.add(kurz + " -  " + lang);
								hstName18.add(lang);
							}catch(Exception e){
								
							}
						}
					}
				}
			}
			file.close();
			
			for(int i = 0; i < hst_k18.size();i++){
				String kname = (String) hst_k18.get(i);
				String lname = (String) hst_l18.get(i);
				
				hst_tm18.put(lname,kname);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*Set set = hst_tm18.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + " - " + me.getValue());
		}*/
	}

	public void readExcelTfzAll18() {
		/*
		 * reads all traction-units from an excel file and writes them in a collection
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2018.xls");

			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(1);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if ((cell.getStringCellValue().equals("Triebfahrzeugreihe"))
								|| (cell.getStringCellValue().equals("Bewertungsziffer (=d)"))
								|| (cell.getStringCellValue().equals("Klassifizierung"))
								|| (cell.getStringCellValue().equals("Gewicht"))) {

						} else {
							// System.out.println(cell.getStringCellValue());
							tfz18.add(cell.getStringCellValue());
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						// System.out.println(cell.getNumericCellValue());
						tfz18.add(cell.getNumericCellValue());
						break;
					}
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readExcelTfzAll17() {
		/*
		 * reads all traction-units from an excel file and writes them in a collection
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2017.xls");

			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(1);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if ((cell.getStringCellValue().equals("Triebfahrzeugreihe"))
								|| (cell.getStringCellValue().equals("Bewertungsziffer (=d)"))
								|| (cell.getStringCellValue().equals("Klassifizierung"))
								|| (cell.getStringCellValue().equals("Gewicht"))) {

						} else {
							// System.out.println(cell.getStringCellValue());
							tfz17.add(cell.getStringCellValue());
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						// System.out.println(cell.getNumericCellValue());
						tfz17.add(cell.getNumericCellValue());
						break;
					}
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readExcelTfzName18() {
		/*
		 * reads all traction units names an writes them in a collection.
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2018.xls");

			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(1);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				Cell cell = cellIterator.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					if ((cell.getStringCellValue().equals("Triebfahrzeugreihe"))
							|| (cell.getStringCellValue().equals("Bewertungsziffer (=d)"))
							|| (cell.getStringCellValue().equals("Klassifizierung"))
							|| (cell.getStringCellValue().equals("Gewicht"))) {

					} else {
						// System.out.println(cell.getStringCellValue());
						tfzName18.add(cell.getStringCellValue());
					}
					break;
				case Cell.CELL_TYPE_NUMERIC:
					// System.out.println(cell.getNumericCellValue());
					tfzName18.add(String.valueOf((int) cell.getNumericCellValue()));
					break;
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tfzName18.add(" ");
	}
	
	public void readExcelTfzName17() {
		/*
		 * reads all traction units names an writes them in a collection.
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2017.xls");

			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(1);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				Cell cell = cellIterator.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					if ((cell.getStringCellValue().equals("Triebfahrzeugreihe"))
							|| (cell.getStringCellValue().equals("Bewertungsziffer (=d)"))
							|| (cell.getStringCellValue().equals("Klassifizierung"))
							|| (cell.getStringCellValue().equals("Gewicht"))) {

					} else {
						// System.out.println(cell.getStringCellValue());
						tfzName17.add(cell.getStringCellValue());
					}
					break;
				case Cell.CELL_TYPE_NUMERIC:
					// System.out.println(cell.getNumericCellValue());
					tfzName17.add(String.valueOf((int) cell.getNumericCellValue()));
					break;
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tfzName17.add(" ");
	}

	public void readExcelTfzGew18() {
		/*
		 * reads all traction units weight and writes them in a collection.
		 * then puts the weight (value) and the name (key) of the traction unit in a colelction (TreeMap)
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2018.xls");

			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(1);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						if (cell.getColumnIndex() == 3) {
							tfzGew18.add(cell.getNumericCellValue());
							break;
						}
					case Cell.CELL_TYPE_STRING:
						break;
					}
				}
			}
			file.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < tfzGew18.size(); i++) {
			String name = (String) tfzName18.get(i);
			Double gew = (Double) tfzGew18.get(i);
			// System.out.println(name);
			// System.out.println(tfzGew.get(i));
			tfzN_G18.put(name, gew);
		}

		/*Set set = tfzN_G.entrySet();
		Iterator i = set.iterator();

		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			System.out.println(me.getKey() + " - " + me.getValue() + " to");
		}*/

	}
	
	public void readExcelTfzGew17() {
		/*
		 * reads all traction units weight and writes them in a collection.
		 * then puts the weight (value) and the name (key) of the traction unit in a colelction (TreeMap)
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2017.xls");

			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(1);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						if (cell.getColumnIndex() == 3) {
							tfzGew17.add(cell.getNumericCellValue());
							break;
						}
					case Cell.CELL_TYPE_STRING:
						break;
					}
				}
			}
			file.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < tfzGew17.size(); i++) {
			String name = (String) tfzName17.get(i);
			Double gew = (Double) tfzGew17.get(i);
			// System.out.println(name);
			// System.out.println(tfzGew.get(i));
			tfzN_G17.put(name, gew);
		}

		/*Set set = tfzN_G.entrySet();
		Iterator i = set.iterator();

		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			System.out.println(me.getKey() + " - " + me.getValue() + " to");
		}*/

	}


	public void readExcelTfzKlass18() {
		/*
		 * reads the traction units classification (A,B,C) and puts them in a collection with the name of the traction unit as the key
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2018.xls");;

			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(1);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if(cell.getColumnIndex() == 2){
							if(cell.getStringCellValue().equals("Klassifizierung")){
								
							}else{
								tfzKlass18.add(cell.getStringCellValue());
								break;
							}
						}
					}
				}
			}
			file.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i < tfzKlass18.size();i++){
			String name = (String) tfzName18.get(i);
			String klass =  (String) tfzKlass18.get(i);
			//System.out.println(name);
			//System.out.println(tfzGew.get(i));
			tfzN_K18.put(name,klass);
		}
		
		/*Set set = tfzN_K.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + " - " + me.getValue());
		}*/
		//System.out.println(tfzKlass.size());
		//System.out.println(tfzName.size());
	}
	
	public void readExcelTfzKlass17() {
		/*
		 * reads the traction units classification (A,B,C) and puts them in a collection with the name of the traction unit as the key
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2017.xls");

			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(1);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if(cell.getColumnIndex() == 2){
							if(cell.getStringCellValue().equals("Klassifizierung")){
								
							}else{
								tfzKlass17.add(cell.getStringCellValue());
								break;
							}
						}
					}
				}
			}
			file.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i < tfzKlass17.size();i++){
			String name = (String) tfzName17.get(i);
			String klass =  (String) tfzKlass17.get(i);
			//System.out.println(name);
			//System.out.println(tfzGew.get(i));
			tfzN_K17.put(name,klass);
		}
		
		/*Set set = tfzN_K.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + " - " + me.getValue());
		}*/
		//System.out.println(tfzKlass.size());
		//System.out.println(tfzName.size());
	}
	
	public void readExcelZugklasse17() {
		/*
		 * reads all train classes in a collection from an excel file
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2017.xls");

			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				Cell cell = cellIterator.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					if ((cell.getStringCellValue().equals("Zugklasse"))){

					} else {
						// System.out.println(cell.getStringCellValue());
						zugklasse17.add(cell.getStringCellValue());
					}
					break;
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readExcelZugklasse18() {
		/*
		 * reads all train classes in a collection from an excel file
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2018.xls");

			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				Cell cell = cellIterator.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					if ((cell.getStringCellValue().equals("Zugklasse"))){

					} else {
						// System.out.println(cell.getStringCellValue());
						zugklasse18.add(cell.getStringCellValue());
					}
					break;
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readBtkm17(){
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2017.xls");

			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(2);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						if(cell.getColumnIndex() == 7){
								pk17Btk =  new BigDecimal(cell.getNumericCellValue());
						}
					}
				}
			}
			file.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(pk17Btk);
	}
	
	public void readBtkm18V2(){
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2018.xls");
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(2);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						if(cell.getColumnIndex() == 7){
								pk18V2Btk = new BigDecimal(cell.getNumericCellValue());
						}
					}
				}
			}
			file.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(pk18V2Btk);
	}
	
	public void readKatPr17(){
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2017.xls");
			ArrayList kat = new ArrayList();
			ArrayList pr = new ArrayList();
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(2);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if(cell.getColumnIndex() == 0){
								kat.add(cell.getStringCellValue());
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if(cell.getColumnIndex() == 1){
							pr.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}
					}
				}
			}
			file.close();
			for(int i = 0; i < kat.size();i++){
				String name = (String) kat.get(i);
				BigDecimal preise =  (BigDecimal) pr.get(i);
				//System.out.println(name);
				//System.out.println(tfzGew.get(i));
				pk17Kat.put(name,preise);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*Set set = pk17Kat.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + " - " + me.getValue());
		}*/
	}
	
	public void readKatPr18(){
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2018.xls");
			ArrayList kat = new ArrayList();
			ArrayList pr = new ArrayList();
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(2);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if(cell.getColumnIndex() == 0){
								kat.add(cell.getStringCellValue());
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if(cell.getColumnIndex() == 1){
							pr.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}
					}
				}
			}
			file.close();
			for(int i = 0; i < kat.size();i++){
				String name = (String) kat.get(i);
				BigDecimal preise = (BigDecimal) pr.get(i);
				//System.out.println(name);
				//System.out.println(tfzGew.get(i));
				pk18Kat.put(name,preise);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*Set set = pk18V2Kat.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + " - " + me.getValue());
		}*/
	}
	
	public void readStatPr17() {
		/*
		 * 
		 * reads all prices for the stations from an excel file and writes them in a collection
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2017.xls");
			ArrayList hst = new ArrayList();
			ArrayList pr = new ArrayList();
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(3);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:

						if ((cell.getStringCellValue().equals("BSC")) || (cell.getStringCellValue().equals("Entgeltsatz"))) {

						} else {
							hst.add(cell.getStringCellValue());
							//System.out.print(hst + " - ");
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						pr.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						//System.out.println(pr);
						break;
					}
				}
			}
			file.close();
			
			for(int i = 0; i < hst.size(); i++){
				String stat = (String) hst.get(i);
				BigDecimal preis = (BigDecimal) pr.get(i);
				
				stationen17.put(stat, preis);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*Set set = stationen17.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + " - " + me.getValue());
		}*/
	}
	
	public TreeMap<String, BigDecimal> getStatPr17(){
		return this.stationen17;
	}
	
	public void readStatPr18() {
		/*
		 * 
		 * reads all prices for the stations from an excel file and writes them in a collection
		 */
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2018.xls");
			ArrayList hst = new ArrayList();
			ArrayList pr = new ArrayList();
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(3);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:

						if ((cell.getStringCellValue().equals("BSC")) || (cell.getStringCellValue().equals("Entgeltsatz"))) {

						} else {
							hst.add(cell.getStringCellValue());
							//System.out.print(hst + " - ");
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						pr.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						//System.out.println(pr);
						break;
					}
				}
			}
			file.close();
			
			for(int i = 0; i < hst.size(); i++){
				String stat = (String) hst.get(i);
				BigDecimal preis = (BigDecimal) pr.get(i);
				
				stationen18.put(stat, preis);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*Set set = stationen18.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + " - " + me.getValue());
		}*/
	}
	
	public void readVerkehrsanreiz17() {
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2017.xls");
			ArrayList halte = new ArrayList();
			ArrayList pr = new ArrayList();
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(4);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if(cell.getColumnIndex() == 0){
							halte.add(cell.getStringCellValue());
							break;
						}
					case Cell.CELL_TYPE_NUMERIC:
						if(cell.getColumnIndex() == 1){
							pr.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}
					}
				}
			}
			file.close();
			
			for(int i = 0; i < halte.size();i++){
				String name = (String) halte.get(i);
				BigDecimal preise =  (BigDecimal) pr.get(i);
				//System.out.println(name);
				//System.out.println(tfzGew.get(i));
				verkAnreiz17.put(name,preise);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*Set set = verkAnreiz17.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + " - " + me.getValue());
		}*/
	}
	
	public void readStrkat18V2(){
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2018.xls");
			ArrayList strkat = new ArrayList();
			ArrayList prRZ = new ArrayList();
			ArrayList prGZ = new ArrayList();
			ArrayList prEWFV = new ArrayList();
			ArrayList prDZ = new ArrayList();
			ArrayList prEWNV = new ArrayList();
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(5);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if(cell.getColumnIndex() == 1){
							if((cell.getStringCellValue().equals("Streckenkategorie")) || (cell.getStringCellValue().equals("Erklärung"))
									|| (cell.getStringCellValue().equals("Zugkm-Preis RZ")) || (cell.getStringCellValue().equals("Zugkm-Preis GZ/DZ"))
									|| (cell.getStringCellValue().equals("Zugkm-Preis EWFV")) || (cell.getStringCellValue().equals("Zugkm-Preis GV-NV"))){
								
							}else{
								switch(cell.getStringCellValue()){
								case "Westachse": strkat.add("WA");
										break;
								case "Brenner": strkat.add("BR");
										break;
								case "Sonstige internat. Achsen": strkat.add("IA");
										break;
								case "Sonstiges Kernnetz": strkat.add("KN");
										break;
								case "Ergänzungsnetz": strkat.add("EN");
										break;
								}
							}
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if(cell.getColumnIndex() == 3){
							prRZ.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}
						if(cell.getColumnIndex() == 4){
							prGZ.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
							prDZ.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}
						if(cell.getColumnIndex() == 5){
							prEWFV.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}
						if(cell.getColumnIndex() == 6){
							prEWNV.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}
					}
				}
			}
			file.close();
			
			for(int i = 0; i < strkat.size();i++){
				String name = (String) strkat.get(i);
				BigDecimal preiseRZ =  (BigDecimal) prRZ.get(i);
				BigDecimal preiseGZ =  (BigDecimal) prGZ.get(i);
				BigDecimal preiseDZ =  (BigDecimal) prDZ.get(i);
				BigDecimal preiseEWFV =  (BigDecimal) prEWFV.get(i);
				BigDecimal preiseEWNV =  (BigDecimal) prEWNV.get(i);
				//System.out.println(name);
				//System.out.println(tfzGew.get(i));
				pk18V2RZ.put(name,preiseRZ);
				pk18V2GZ.put(name,preiseGZ);
				pk18V2DZ.put(name,preiseDZ);
				pk18V2EWFV.put(name,preiseEWFV);
				pk18V2EWNV.put(name,preiseEWNV);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*Set set = pk18V2DZ.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + " - " + me.getValue());
		}*/
	}
	
	public void readStrkat17(){
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2017.xls");
			ArrayList strkat = new ArrayList();
			ArrayList prRZ = new ArrayList();
			ArrayList prGZ = new ArrayList();
			ArrayList prEWFV = new ArrayList();
			ArrayList prDZ = new ArrayList();
			ArrayList prEWNV = new ArrayList();
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(5);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if(cell.getColumnIndex() == 1){
							if((cell.getStringCellValue().equals("Streckenkategorie")) || (cell.getStringCellValue().equals("Erklärung"))
									|| (cell.getStringCellValue().equals("Zugkm-Preis RZ")) || (cell.getStringCellValue().equals("Zugkm-Preis GZ/DZ"))
									|| (cell.getStringCellValue().equals("Zugkm-Preis EWFV")) || (cell.getStringCellValue().equals("Zugkm-Preis GV-NV"))){
								
							}else{
								switch(cell.getStringCellValue()){
								case "Westachse": strkat.add("WA");
										break;
								case "Brenner": strkat.add("BR");
										break;
								case "Sonstige internat. Achsen": strkat.add("IA");
										break;
								case "Sonstiges Kernnetz": strkat.add("KN");
										break;
								case "Ergänzungsnetz": strkat.add("EN");
										break;
								}
							}
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if(cell.getColumnIndex() == 3){
							prRZ.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}
						if(cell.getColumnIndex() == 4){
							prGZ.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
							prDZ.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}
						if(cell.getColumnIndex() == 5){
							prEWFV.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}
						if(cell.getColumnIndex() == 6){
							prEWNV.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}
					}
				}
			}
			file.close();
			
			for(int i = 0; i < strkat.size();i++){
				String name = (String) strkat.get(i);
				BigDecimal preiseRZ =  (BigDecimal) prRZ.get(i);
				BigDecimal preiseGZ =  (BigDecimal) prGZ.get(i);
				BigDecimal preiseDZ =  (BigDecimal) prDZ.get(i);
				BigDecimal preiseEWFV =  (BigDecimal) prEWFV.get(i);
				BigDecimal preiseEWNV =  (BigDecimal) prEWNV.get(i);
				//System.out.println(name);
				//System.out.println(tfzGew.get(i));
				pk17RZ.put(name,preiseRZ);
				pk17GZ.put(name,preiseGZ);
				pk17DZ.put(name,preiseDZ);
				pk17EWFV.put(name,preiseEWFV);
				pk17EWNV.put(name,preiseEWNV);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*Set set = pk17DZ.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + " - " + me.getValue());
		}*/
	}
	
	public void readKmPr18V1(){
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("berechnungsdaten2018.xls");
			ArrayList segment = new ArrayList();
			ArrayList zugkmpr = new ArrayList();
			ArrayList btkmpr = new ArrayList();
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(6);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if(cell.getColumnIndex() == 0){
							if((cell.getStringCellValue().equals("Marktsegment")) || (cell.getStringCellValue().equals("Zugkm Entgelt"))
									|| (cell.getStringCellValue().equals("Btkm Entgelt"))){
								
							}else{
								switch(cell.getStringCellValue()){
								case "Eigenwirtschaftlicher Personenverkehr": segment.add("1.1.*.a Eig. PV");
										break;
								case "Gemeinwirtschaftlicher Personenverkehr": segment.add("1.1.*.b Gem. PV-FV");
										break;
								case "Nahverkehr stark": segment.add("1.1.*.c PNV stark");
										break;
								case "Nahverkehr schwach": segment.add("1.1.*.d PNV schwach");
										break;
								case "Güterverkehr manipuliert": segment.add("1.1.*.e GV man.");
										break;
								case "Güterverkehr nicht manipuliert": segment.add("1.1.*.f GV n. man.");
										break;
								case "Dienstzug": segment.add("1.1.*.g Dienstzug");
										break;
								}
							}
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if(cell.getColumnIndex() == 1){
							zugkmpr.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}
						if(cell.getColumnIndex() == 2){
							btkmpr.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}
						break;
					}
				}
			}
			file.close();
			
			for(int i = 0; i < segment.size();i++){
				String name = (String) segment.get(i);
				BigDecimal preisezkm =  (BigDecimal) zugkmpr.get(i);
				BigDecimal preisebkm =  (BigDecimal) btkmpr.get(i);

				pk18V1Zugkm.put(name,preisezkm);
				pk18V1Btkm.put(name,preisebkm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*Set set = pk18V1Btkm.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + " - " + me.getValue());
		}*/
	}
	
	public void readBstn17() {
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("bstn2017.xlsx");
			ArrayList bst = new ArrayList();
			ArrayList km = new ArrayList();
			ArrayList fr = new ArrayList();
			ArrayList str = new ArrayList();
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					//5,8,13
					case Cell.CELL_TYPE_STRING:
						if((cell.getStringCellValue().equals("BST")) || (cell.getStringCellValue().equals("FR"))
								|| (cell.getStringCellValue().equals("LDN_KM")) || (cell.getStringCellValue().equals("STRECKE"))){
								
						}else{
							switch(cell.getColumnIndex()){
							case 5: bst.add(cell.getStringCellValue());
									bstnBST17.add(cell.getStringCellValue());
									break;
							}
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if(cell.getColumnIndex() == 8){
							fr.add((int)(cell.getNumericCellValue()));
						}else if(cell.getColumnIndex() == 13){
							km.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}else if(cell.getColumnIndex() == 3){
							str.add((int)(cell.getNumericCellValue()));
						}
						break;
					case Cell.CELL_TYPE_BLANK:
						if(cell.getColumnIndex() == 8){
							fr.add(0);
						}
						break;
					}
				}
			}
			file.close();
			
			//Richtung 1
			for(int i = 0; i < bst.size()-1;i++){
				String hst = (String) bst.get(i); //Wbf
				String hst2 = (String) bst.get(i+1); //Mlx
				int fahrtr=  (int) fr.get(i); //0
				int fahrtr2=  (int) fr.get(i+1); //0
				BigDecimal entf =  (BigDecimal) km.get(i); //1.883
				BigDecimal entf2 =  (BigDecimal) km.get(i+1); //3.109
				int strecke = (int) str.get(i); //10501
				int strecke2 = (int) str.get(i+1); //10501
				
				if((strecke == strecke2)){ //ok
					if(((fahrtr == 0) || (fahrtr == 1))){ //ok
						if(((fahrtr2 == 0) || (fahrtr2 == 1))){ //ok
							bstn17.put(hst+" / "+hst2, entf2.subtract(entf)); // Wbf / Mlx - 3.109-1.883
						}else{
							a: for(int u=0; u<5; u++){
								String hst3 = (String) bst.get(i+2+u);
								BigDecimal entf3 =  (BigDecimal) km.get(i+2+u);
								bstn17.put(hst+" / "+hst3, entf3.subtract(entf));
								int str3 = (int) str.get(i+2+u);
								int fahrtr3 = (int) fr.get(i+2+u);
								if(strecke == str3 && fahrtr3!=2){
									
									bstn17.put(hst+" / "+hst3, entf3.subtract(entf));

									i = i+u+1;
									break a; 
							}
						}
						}
					}
				}else{
					
				}
			}
			
			//Richtung 2
			for(int i = bst.size()-1; i > 0 ;i--){
				String hst = (String) bst.get(i); //Mlx
				String hst2 = (String) bst.get(i-1); //Wbf
				int fahrtr=  (int) fr.get(i); //0
				int fahrtr2=  (int) fr.get(i-1); //0
				BigDecimal entf =  (BigDecimal) km.get(i); //3.109
				BigDecimal entf2 =  (BigDecimal) km.get(i-1); //1.883
				int strecke = (int) str.get(i); //10501
				int strecke2 = (int) str.get(i-1); //10501
				
				if((strecke == strecke2)){
					if(((fahrtr == 0) || (fahrtr == 2))){ //ok
						if(((fahrtr2 == 0) || (fahrtr2 == 2))){ //ok
							bstn17.put(hst+" / "+hst2, entf.subtract(entf2)); // Mlx / Wbf - 3.109-1.883
						
						}else{
							a: for(int u=0; u<5; u++){
								String hst3 = (String) bst.get(i-2-u);
								BigDecimal entf3 =  (BigDecimal) km.get(i-2-u);
								int str3 = (int) str.get(i-2-u);
								int fahrtr3 = (int) fr.get(i-2-u);
								if(strecke == str3 && fahrtr3!=1){
									
									bstn17.put(hst+" / "+hst3, entf.subtract(entf3));
									i = i-u-1;
									break a; 
								}

							}
							
						}
					}
				}else{
					
				}	
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readBstn18() {
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("bstn2018.xlsx");
			ArrayList bst = new ArrayList();
			ArrayList km = new ArrayList();
			ArrayList fr = new ArrayList();
			ArrayList str = new ArrayList();
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					//5,8,13
					case Cell.CELL_TYPE_STRING:
						if((cell.getStringCellValue().equals("BST")) || (cell.getStringCellValue().equals("FR"))
								|| (cell.getStringCellValue().equals("LDN_KM")) || (cell.getStringCellValue().equals("STRECKE"))){
								
						}else{
							switch(cell.getColumnIndex()){
							case 5: bst.add(cell.getStringCellValue());
									bstnBST18.add(cell.getStringCellValue());
									break;
							}
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if(cell.getColumnIndex() == 8){
							fr.add((int)(cell.getNumericCellValue()));
						}else if(cell.getColumnIndex() == 13){
							km.add(new BigDecimal(cell.getNumericCellValue(),MathContext.DECIMAL32));
						}else if(cell.getColumnIndex() == 3){
							str.add((int)(cell.getNumericCellValue()));
						}
						break;
					case Cell.CELL_TYPE_BLANK:
						if(cell.getColumnIndex() == 8){
							fr.add(0);
						}
						break;
					}
				}
			}
			file.close();
			
			//Richtung 1
			for(int i = 0; i < bst.size()-1;i++){
				String hst = (String) bst.get(i); //Wbf
				String hst2 = (String) bst.get(i+1); //Mlx
				int fahrtr=  (int) fr.get(i); //0
				int fahrtr2=  (int) fr.get(i+1); //0
				BigDecimal entf =  (BigDecimal) km.get(i); //1.883
				BigDecimal entf2 =  (BigDecimal) km.get(i+1); //3.109
				int strecke = (int) str.get(i); //10501
				int strecke2 = (int) str.get(i+1); //10501
				
				if((strecke == strecke2)){ //ok
					if(((fahrtr == 0) || (fahrtr == 1))){ //ok
						if(((fahrtr2 == 0) || (fahrtr2 == 1))){ //ok
							bstn18.put(hst+" / "+hst2, entf2.subtract(entf)); // Wbf / Mlx - 3.109-1.883
						}else{
							a: for(int u=0; u<5; u++){
								String hst3 = (String) bst.get(i+2+u);
								BigDecimal entf3 =  (BigDecimal) km.get(i+2+u);
								bstn18.put(hst+" / "+hst3, entf3.subtract(entf));
								int str3 = (int) str.get(i+2+u);
								int fahrtr3 = (int) fr.get(i+2+u);
								if(strecke == str3 && fahrtr3!=2){
									
									bstn18.put(hst+" / "+hst3, entf3.subtract(entf));

									i = i+u+1;
									break a; 
							}
						}
						}
					}
				}else{
					
				}
			}
			
			//Richtung 2
			for(int i = bst.size()-1; i > 0 ;i--){
				String hst = (String) bst.get(i); //Mlx
				String hst2 = (String) bst.get(i-1); //Wbf
				int fahrtr=  (int) fr.get(i); //0
				int fahrtr2=  (int) fr.get(i-1); //0
				BigDecimal entf =  (BigDecimal) km.get(i); //3.109
				BigDecimal entf2 =  (BigDecimal) km.get(i-1); //1.883
				int strecke = (int) str.get(i); //10501
				int strecke2 = (int) str.get(i-1); //10501
				
				if((strecke == strecke2)){
					if(((fahrtr == 0) || (fahrtr == 2))){ //ok
						if(((fahrtr2 == 0) || (fahrtr2 == 2))){ //ok
							bstn18.put(hst+" / "+hst2, entf.subtract(entf2)); // Mlx / Wbf - 3.109-1.883
						
						}else{
							a: for(int u=0; u<5; u++){
								String hst3 = (String) bst.get(i-2-u);
								BigDecimal entf3 =  (BigDecimal) km.get(i-2-u);
								int str3 = (int) str.get(i-2-u);
								int fahrtr3 = (int) fr.get(i-2-u);
								if(strecke == str3 && fahrtr3!=1){
									
									bstn18.put(hst+" / "+hst3, entf.subtract(entf3));
									i = i-u-1;
									break a; 
								}

							}
							
						}
					}
				}else{
					
				}	
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readBstVerbindungen17() {
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("bstverbindungen2017.xlsx");
			ArrayList von = new ArrayList();
			ArrayList bis = new ArrayList();
			ArrayList strkat = new ArrayList();
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if((cell.getStringCellValue().equals("DB640_KURZ_VON")) || (cell.getStringCellValue().equals("DB640_KURZ_BIS"))
								|| (cell.getStringCellValue().equals("STRKAT_ID"))){
								
						}else{
							switch(cell.getColumnIndex()){
							case 3: von.add(cell.getStringCellValue());
									break;
							case 4: bis.add(cell.getStringCellValue());
									break;
							}
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if(cell.getColumnIndex() == 9){
							if(cell.getNumericCellValue() == 1268960){
								strkat.add("EN");
							}else if(cell.getNumericCellValue() == 1268962){
								strkat.add("BR");
							}else if(cell.getNumericCellValue() == 1268966){
								strkat.add("IN");
							}else if(cell.getNumericCellValue() == 1268968){
								strkat.add("KN");
							}else if(cell.getNumericCellValue() == 1268970){
								strkat.add("WA");
							}
							break;
						}
						
					}
				}
			}
			file.close();
			
			for(int i = 0; i < von.size();i++){
				String namev = (String) von.get(i);
				String nameb =  (String) bis.get(i);
				String kat =  (String) strkat.get(i);

				strKat17.put(namev+" - "+nameb,kat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*Set set = strKat17.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + "    - " + me.getValue());
		}*/
		
		//System.out.println(strKat17.get("Ams - Ams S11"));
	}
	
	public void readBstVerbindungen18() {
		try {
			file = this.getClass().getClassLoader().getResourceAsStream("bstverbindungen2018.xlsx");

			ArrayList von = new ArrayList();
			ArrayList bis = new ArrayList();
			ArrayList strkat = new ArrayList();
			
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if((cell.getStringCellValue().equals("DB640_KURZ_VON")) || (cell.getStringCellValue().equals("DB640_KURZ_BIS"))
								|| (cell.getStringCellValue().equals("STRKAT_ID"))){
								
						}else{
							switch(cell.getColumnIndex()){
							case 3: von.add(cell.getStringCellValue());
									break;
							case 4: bis.add(cell.getStringCellValue());
									break;
							}
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if(cell.getColumnIndex() == 9){
							if(cell.getNumericCellValue() == 1268960){
								strkat.add("EN");
							}else if(cell.getNumericCellValue() == 1268962){
								strkat.add("BR");
							}else if(cell.getNumericCellValue() == 1268966){
								strkat.add("IN");
							}else if(cell.getNumericCellValue() == 1268968){
								strkat.add("KN");
							}else if(cell.getNumericCellValue() == 1268970){
								strkat.add("WA");
							}
							break;
						}
						
					}
				}
			}
			file.close();
			
			for(int i = 0; i < von.size();i++){
				String namev = (String) von.get(i);
				String nameb =  (String) bis.get(i);
				String kat =  (String) strkat.get(i);

				strKat18.put(namev+" - "+nameb,kat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*Set set = strKat18.entrySet();
		Iterator i = set.iterator();
		
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.println(me.getKey() + "    - " + me.getValue());
		}
		System.out.println(strKat18.get("Wbf - Mlx"));*/
	}
}

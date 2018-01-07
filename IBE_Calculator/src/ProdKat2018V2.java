import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.TreeMap;

public class ProdKat2018V2 {
	private TreeMap<String, BigDecimal> strecke = new TreeMap<>();
	private BigDecimal zugGew = new BigDecimal(0);
	private BigDecimal btkm = new BigDecimal(0);
	private BigDecimal zugkmWA = new BigDecimal(0);
	private BigDecimal zugkmBR = new BigDecimal(0);
	private BigDecimal zugkmIA = new BigDecimal(0);
	private BigDecimal zugkmKN = new BigDecimal(0);
	private BigDecimal zugkmEN = new BigDecimal(0);
	private BigDecimal zugkmGes = new BigDecimal(0);
	private BigDecimal ibe = new BigDecimal(0);
	private BigDecimal halteGes = new BigDecimal(0);
	private BigDecimal tfzEntg = new BigDecimal(0);
	private ControllerMain cm;
	private ExcelHandler eh;
	private ArrayList<String> halte = new ArrayList();
	
	public ProdKat2018V2(){
		
	}
	
	public ProdKat2018V2(ControllerMain c,TreeMap<String, BigDecimal> treeMap, BigDecimal zuggew, ArrayList<String> halte){
		this.cm = c;
		this.eh = new ExcelHandler();
		init(treeMap,zuggew,halte);
	}
	
	public void init(TreeMap<String, BigDecimal> treeMap, BigDecimal zuggew, ArrayList<String> halte) {
		System.out.println("Verkehrsart ProdKat18V2:" + this.cm.getVerkehrsart());
		System.out.println("Zuggew in ProdKat18V2: "+ zuggew);
		System.out.println("halte in ProdKat18V2");
		this.halte = halte;
		for(int i = 0; i < halte.size(); i++){
			System.out.println(halte.get(i));
		}
		System.out.println(ExcelHandler.stationen18.size());
		strecke = treeMap;
		zugGew = zuggew;
		
		for(int i = 0; i < halte.size(); i++){
			halteGes = halteGes.add(ExcelHandler.stationen18.get(halte.get(i)));
		}
		this.cm.getTextFieldHalte().setText(halteGes.setScale(2, BigDecimal.ROUND_UP).toString());
		this.cm.getTextFieldTfzFak().setText(ExcelHandler.pk18Kat.get(this.cm.getTfzKat()).setScale(4, BigDecimal.ROUND_UP).toString());
		System.out.println("Init in ProdKat18V2");
		calcBtkm();
		calcIBE();
	}

	private void calcBtkm() {
		//in gui GBTKM
		this.btkm = (BigDecimal) (strecke.get("Ges").multiply(zugGew));
		this.btkm = this.btkm.multiply(ExcelHandler.pk18V2Btk);
		this.cm.getTextFieldGBTKM().setText(btkm.setScale(2, BigDecimal.ROUND_UP).toString());
	}
	
	public void calcIBE() {
		BigDecimal ibeWa;
		BigDecimal ibeBr;
		BigDecimal ibeIa;
		BigDecimal ibeKn;
		BigDecimal ibeEn;
		BigDecimal ibeGes;
		BigDecimal ibeZugkm;
		
		if(this.cm.getVerkehrsart().contains("RZ")){
			if(ExcelHandler.stationen18.containsKey(this.cm.getDB640von().getText()) && (!this.halte.contains(this.cm.getDB640von().getText()))){
				halteGes = halteGes.add(ExcelHandler.stationen18.get(this.cm.getDB640von().getText()));
			}
			if(ExcelHandler.stationen18.containsKey(this.cm.getDB640bis().getText()) && (!this.halte.contains(this.cm.getDB640bis().getText()))){
				halteGes = halteGes.add(ExcelHandler.stationen18.get(this.cm.getDB640bis().getText()));
			}
			this.cm.getTextFieldHalte().setText(halteGes.setScale(2, BigDecimal.ROUND_UP).toString());
			
			ibeWa = strecke.get("WA").multiply(ExcelHandler.pk18V2RZ.get("WA"));
			ibeBr = strecke.get("BR").multiply(ExcelHandler.pk18V2RZ.get("BR"));
			ibeIa = strecke.get("IA").multiply(ExcelHandler.pk18V2RZ.get("IA"));
			ibeKn = strecke.get("KN").multiply(ExcelHandler.pk18V2RZ.get("KN"));
			ibeEn = strecke.get("EN").multiply(ExcelHandler.pk18V2RZ.get("EN"));
			ibeGes = strecke.get("Ges").multiply(ExcelHandler.pk18Kat.get(this.cm.getTfzKat()));
			
			this.zugkmWA = ibeWa;
			this.zugkmBR = ibeBr;
			this.zugkmIA = ibeIa;
			this.zugkmKN = ibeKn;
			this.zugkmEN = ibeEn;
			this.tfzEntg = ibeGes;
			
			ibe = ibe.add(ibeWa);
			ibe = ibe.add(ibeBr);
			ibe = ibe.add(ibeIa);
			ibe = ibe.add(ibeKn);
			ibe = ibe.add(ibeEn);
			ibeZugkm = ibe;
			ibe = ibe.add(ibeGes);
			ibe = ibe.add(halteGes);
			ibe = ibe.add(this.btkm);
			ibe = ibe.setScale(2, BigDecimal.ROUND_UP);
			
			this.zugkmGes = ibeZugkm;
			
			System.out.println("IBE-Ges: " + ibe);
			this.cm.getTextFieldEntgeldGes().setText(ibe.toString());
			this.cm.getTextFieldZugKm().setText(ibeZugkm.setScale(2, BigDecimal.ROUND_UP).toString());
		}
		
		if(this.cm.getVerkehrsart().contains("GZ")){
			ibeWa = strecke.get("WA").multiply(ExcelHandler.pk18V2GZ.get("WA"));
			ibeBr = strecke.get("BR").multiply(ExcelHandler.pk18V2GZ.get("BR"));
			ibeIa = strecke.get("IA").multiply(ExcelHandler.pk18V2GZ.get("IA"));
			ibeKn = strecke.get("KN").multiply(ExcelHandler.pk18V2GZ.get("KN"));
			ibeEn = strecke.get("EN").multiply(ExcelHandler.pk18V2GZ.get("EN"));
			ibeGes = strecke.get("Ges").multiply(ExcelHandler.pk18Kat.get(this.cm.getTfzKat()));
			
			this.zugkmWA = ibeWa;
			this.zugkmBR = ibeBr;
			this.zugkmIA = ibeIa;
			this.zugkmKN = ibeKn;
			this.zugkmEN = ibeEn;
			this.tfzEntg = ibeGes;
			
			ibe = ibe.add(ibeWa);
			ibe = ibe.add(ibeBr);
			ibe = ibe.add(ibeIa);
			ibe = ibe.add(ibeKn);
			ibe = ibe.add(ibeEn);
			ibeZugkm = ibe;
			ibe = ibe.add(ibeGes);
			ibe = ibe.add(halteGes);
			ibe = ibe.add(this.btkm);
			ibe = ibe.setScale(2, BigDecimal.ROUND_UP);
			
			this.zugkmGes = ibeZugkm;
			
			System.out.println("IBE-Ges: " + ibe);
			this.cm.getTextFieldEntgeldGes().setText(ibe.toString());
			this.cm.getTextFieldZugKm().setText(ibeZugkm.setScale(2, BigDecimal.ROUND_UP).toString());
		}
		
		if(this.cm.getVerkehrsart().contains("EWFV")){
			ibeWa = strecke.get("WA").multiply(ExcelHandler.pk18V2EWFV.get("WA"));
			ibeBr = strecke.get("BR").multiply(ExcelHandler.pk18V2EWFV.get("BR"));
			ibeIa = strecke.get("IA").multiply(ExcelHandler.pk18V2EWFV.get("IA"));
			ibeKn = strecke.get("KN").multiply(ExcelHandler.pk18V2EWFV.get("KN"));
			ibeEn = strecke.get("EN").multiply(ExcelHandler.pk18V2EWFV.get("EN"));
			ibeGes = strecke.get("Ges").multiply(ExcelHandler.pk18Kat.get(this.cm.getTfzKat()));
			
			this.zugkmWA = ibeWa;
			this.zugkmBR = ibeBr;
			this.zugkmIA = ibeIa;
			this.zugkmKN = ibeKn;
			this.zugkmEN = ibeEn;
			this.tfzEntg = ibeGes;
			
			ibe = ibe.add(ibeWa);
			ibe = ibe.add(ibeBr);
			ibe = ibe.add(ibeIa);
			ibe = ibe.add(ibeKn);
			ibe = ibe.add(ibeEn);
			ibeZugkm = ibe;
			ibe = ibe.add(ibeGes);
			ibe = ibe.add(halteGes);
			ibe = ibe.add(this.btkm);
			ibe = ibe.setScale(2, BigDecimal.ROUND_UP);
			
			this.zugkmGes = ibeZugkm;
			
			System.out.println("IBE-Ges: " + ibe);
			this.cm.getTextFieldEntgeldGes().setText(ibe.toString());
			this.cm.getTextFieldZugKm().setText(ibeZugkm.setScale(2, BigDecimal.ROUND_UP).toString());
		}
		
		if(this.cm.getVerkehrsart().contains("DZ")){
			ibeWa = strecke.get("WA").multiply(ExcelHandler.pk18V2DZ.get("WA"));
			ibeBr = strecke.get("BR").multiply(ExcelHandler.pk18V2DZ.get("BR"));
			ibeIa = strecke.get("IA").multiply(ExcelHandler.pk18V2DZ.get("IA"));
			ibeKn = strecke.get("KN").multiply(ExcelHandler.pk18V2DZ.get("KN"));
			ibeEn = strecke.get("EN").multiply(ExcelHandler.pk18V2DZ.get("EN"));
			ibeGes = strecke.get("Ges").multiply(ExcelHandler.pk18Kat.get(this.cm.getTfzKat()));
			
			this.zugkmWA = ibeWa;
			this.zugkmBR = ibeBr;
			this.zugkmIA = ibeIa;
			this.zugkmKN = ibeKn;
			this.zugkmEN = ibeEn;
			this.tfzEntg = ibeGes;
			
			ibe = ibe.add(ibeWa);
			ibe = ibe.add(ibeBr);
			ibe = ibe.add(ibeIa);
			ibe = ibe.add(ibeKn);
			ibe = ibe.add(ibeEn);
			ibeZugkm = ibe;
			ibe = ibe.add(ibeGes);
			ibe = ibe.add(halteGes);
			ibe = ibe.add(this.btkm);
			ibe = ibe.setScale(2, BigDecimal.ROUND_UP);
			
			this.zugkmGes = ibeZugkm;
			
			System.out.println("IBE-Ges: " + ibe);
			this.cm.getTextFieldEntgeldGes().setText(ibe.toString());
			this.cm.getTextFieldZugKm().setText(ibeZugkm.setScale(2, BigDecimal.ROUND_UP).toString());
		}
		
		if(this.cm.getVerkehrsart().contains("EWNV")){
			ibeGes = strecke.get("Ges").multiply(ExcelHandler.pk18Kat.get(this.cm.getTfzKat())); 
			
			this.tfzEntg = ibeGes;
			
			ibe = ibe.add(ibeGes);
			ibe = ibe.add(halteGes);
			ibe = ibe.add(this.btkm);
			ibe = ibe.setScale(2, BigDecimal.ROUND_UP);
			
			System.out.println("IBE-Ges: " + ibe);
			this.cm.getTextFieldEntgeldGes().setText(ibe.toString());
			this.cm.getTextFieldZugKm().setText("0");
		}
	}
	
	public String[] getWA(){
		String[] data = new String[2];
		data[0] = strecke.get("WA").setScale(2, BigDecimal.ROUND_UP).toString();
		data[1] = this.zugkmWA.setScale(2, BigDecimal.ROUND_UP).toString();
		return data;	
	}
	
	public String[] getBR(){
		String[] data = new String[2];
		data[0] = strecke.get("BR").setScale(2, BigDecimal.ROUND_UP).toString();
		data[1] = this.zugkmBR.setScale(2, BigDecimal.ROUND_UP).toString();
		return data;
	}
	
	public String[] getIA(){
		String[] data = new String[2];
		data[0] = strecke.get("IA").setScale(2, BigDecimal.ROUND_UP).toString();
		data[1] = this.zugkmIA.setScale(2, BigDecimal.ROUND_UP).toString();
		return data;
	}
	
	public String[] getKN(){
		String[] data = new String[2];
		data[0] = strecke.get("KN").setScale(2, BigDecimal.ROUND_UP).toString();
		data[1] = this.zugkmKN.setScale(2, BigDecimal.ROUND_UP).toString();
		return data;
	}
	
	public String[] getEN(){
		String[] data = new String[2];
		data[0] = strecke.get("EN").setScale(2, BigDecimal.ROUND_UP).toString();
		data[1] = this.zugkmEN.setScale(2, BigDecimal.ROUND_UP).toString();
		return data;
	}
	
	public String[] getGes(){
		String[] data = new String[2];
		data[0] = strecke.get("Ges").setScale(2, BigDecimal.ROUND_UP).toString();
		data[1] = this.zugkmGes.setScale(2, BigDecimal.ROUND_UP).toString();
		return data;
	}
	
	public String getBTKM(){
		return this.btkm.setScale(2, BigDecimal.ROUND_UP).toString();
	}
	
	public String getEntgTfz(){
		return this.tfzEntg.setScale(2, BigDecimal.ROUND_UP).toString();
	}
}
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.TreeMap;

public class ProdKat2018V1 {
	 private BigDecimal strecke = new BigDecimal(0);
	 private BigDecimal zugGew = new BigDecimal(0);
	 private BigDecimal btkm = new BigDecimal(0);
	 private BigDecimal ibeGes = new BigDecimal(0);
	 private BigDecimal halteGes = new BigDecimal(0);
	 private BigDecimal ibeKat = new BigDecimal(0);
	 private BigDecimal ibeZugkm = new BigDecimal(0);
	 private ControllerMain cm;
	 private ArrayList<String> halte = new ArrayList();
	
	public ProdKat2018V1(){
		
	}
	
	public ProdKat2018V1(ControllerMain c, BigDecimal strecke, BigDecimal zuggew2, ArrayList<String> halte) {
		this.cm = c;
		init(strecke,zuggew2,halte);
	}

	public void init(BigDecimal bigDecimal, BigDecimal zuggew2, ArrayList<String> halte) {
		strecke = bigDecimal;
		zugGew = zuggew2;
		this.halte = halte;
		for(int i = 0; i < halte.size(); i++){
			halteGes = halteGes.add(ExcelHandler.stationen18.get(halte.get(i)));
		}
		if((this.cm.getVerkehrsart().contains("PV")) || (this.cm.getVerkehrsart().contains("PNV"))){
			if(ExcelHandler.stationen18.containsKey(this.cm.getDB640von().getText()) && (!this.halte.contains(this.cm.getDB640von().getText()))){
				halteGes = halteGes.add(ExcelHandler.stationen18.get(this.cm.getDB640von().getText()));
			}
			if(ExcelHandler.stationen18.containsKey(this.cm.getDB640bis().getText()) && (!this.halte.contains(this.cm.getDB640bis().getText()))){
				halteGes = halteGes.add(ExcelHandler.stationen18.get(this.cm.getDB640bis().getText()));
			}
		}
		
		this.cm.getTextFieldHalte().setText(halteGes.setScale(2, BigDecimal.ROUND_UP).toString());
		this.cm.getTextFieldTfzFak().setText(ExcelHandler.pk18Kat.get(this.cm.getTfzKat()).setScale(4, BigDecimal.ROUND_UP).toString());
		System.out.println("Init in ProdKat18V1");
		calcBtkm();
		calcIBE();
	}

	private void calcBtkm() {
		this.btkm = (BigDecimal) (strecke.multiply(zugGew));
		this.btkm = this.btkm.multiply(ExcelHandler.pk18V1Btkm.get(this.cm.getVerkehrsart()));
		this.cm.getTextFieldGBTKM().setText(btkm.setScale(2, BigDecimal.ROUND_UP).toString());
	}

	
	private void calcIBE() {
		ibeZugkm = strecke.multiply(ExcelHandler.pk18V1Zugkm.get(this.cm.getVerkehrsart()));
		ibeKat = strecke.multiply(ExcelHandler.pk18Kat.get(this.cm.getTfzKat()));
		
		ibeGes = ibeGes.add(ibeZugkm);
		ibeGes = ibeGes.add(ibeKat);
		ibeGes = ibeGes.add(halteGes);
		ibeGes = ibeGes.add(this.btkm);
		ibeGes = ibeGes.setScale(2, BigDecimal.ROUND_UP);
		
		System.out.println("IBE-Ges: " + ibeGes);
		this.cm.getTextFieldTfzFak().setText(ExcelHandler.pk18Kat.get(this.cm.getTfzKat()).setScale(4, BigDecimal.ROUND_UP).toString());
		this.cm.getTextFieldZugKm().setText(ibeZugkm.setScale(2, BigDecimal.ROUND_UP).toString());
		this.cm.getTextFieldEntgeldGes().setText(this.ibeGes.toString());
	}
	
	public String[] getGes(){
		String[] data = new String[2];
		data[0] = strecke.setScale(2, BigDecimal.ROUND_UP).toString();
		data[1] = this.ibeZugkm.setScale(2, BigDecimal.ROUND_UP).toString();
		return data;
	}
	
	public String getBTKM(){
		return this.btkm.setScale(2, BigDecimal.ROUND_UP).toString();
	}
	
	public String getEntgTfz(){
		return this.ibeKat.setScale(2, BigDecimal.ROUND_UP).toString();
	}
}

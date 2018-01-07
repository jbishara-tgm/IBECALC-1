import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Route {
	private final SimpleStringProperty stat;
	private final SimpleStringProperty achse;
	private final SimpleStringProperty km;
	private final SimpleStringProperty anmerkung;
		
	public Route(String statName, String km, String achse, String anmerkung){
		this.stat = new SimpleStringProperty(statName);
		this.km = new SimpleStringProperty(km);
		this.achse = new SimpleStringProperty(achse);
		this.anmerkung = new SimpleStringProperty(anmerkung);
	}
	
	public String getStat(){
		return stat.get();
	}
	
	public String getAnmerkung(){
		return anmerkung.get();
	}
	
	public String getAchse(){
		return achse.get();
	}
	
	public String getKm(){
		return km.get();
	}
	
	public void setKm(String km){
		this.km.set(km);
	}
	
	public void setAchse(String achse){
		this.achse.set(achse);
	}
	
	public void setAnmerkung(String anmerkung){
		this.anmerkung.set(anmerkung);
	}
	
	public void setStatName(String station){
		this.stat.set(station);
	}
}

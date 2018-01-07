import org.jgrapht.*;
import org.jgrapht.alg.*;
import org.jgrapht.graph.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class Dijkstra {
	private ControllerMain cm;

	private TreeMap<String,BigDecimal> wegstrecke_fin = new TreeMap<>();

	public ObservableList<Route> excelData;
	
	public Dijkstra(){
		
	}
	
	public Dijkstra(ControllerMain c){
		this.cm = c;
	}
	
	public void init17(String vonText, String bisText, ArrayList<String> route) {
		/*
		 * berechnet die kürzeste Route
		 */
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);
		BigDecimal entfGes = new BigDecimal(0, MathContext.DECIMAL32);
		String hst_s = "";
		String lbez = "";
		String von = vonText;
		String bis = bisText;
		ArrayList<String> vias = route;
		List shortest_path = new ArrayList<>();
		
		TreeMap<String, String> strKat = ExcelHandler.strKat17;
		
		TreeMap<String,BigDecimal> wegstrecke = new TreeMap<>();
		
		ObservableList<Route> data = FXCollections.observableArrayList();
    	
		BigDecimal wa = new BigDecimal(0, MathContext.DECIMAL32);
		BigDecimal ia = new BigDecimal(0, MathContext.DECIMAL32);
		BigDecimal kn = new BigDecimal(0, MathContext.DECIMAL32);
		BigDecimal en = new BigDecimal(0, MathContext.DECIMAL32);
		BigDecimal br = new BigDecimal(0, MathContext.DECIMAL32);
		
		//fügt alle verfügbaren Hst als Vertex hinzu
		for(int i = 0; i < ExcelHandler.bstnBST17.size() ;i++){
			graph.addVertex((String) ExcelHandler.bstnBST17.get(i).toString());
		}
		
		
		//System.out.println(graph.vertexSet());
		
		/*
		 * fügt alle verfügbaren Verbindungen als Weighted Edge hinzu
		 */
		Set set = ExcelHandler.bstn17.entrySet();
		Iterator it = set.iterator();
		
		while(it.hasNext()){
			Map.Entry me = (Map.Entry)it.next();
			String[] halte = me.getKey().toString().split(" / ");
			double entf = ((BigDecimal) me.getValue()).doubleValue();

			DefaultWeightedEdge e = graph.addEdge(halte[0], halte[1]);
			graph.setEdgeWeight(e, entf);
		}

		try{
			/*
			 * berechnet die kürzeste Route
			 */
			if(this.cm.getListVia().getItems().size() <= 0){
				System.out.println("Shortest path");
				shortest_path = DijkstraShortestPath.findPathBetween(graph, von, bis);
			}else{
				shortest_path = DijkstraShortestPath.findPathBetween(graph, von, route.get(0));
				for(int i = 0; i < route.size()-1; i++){
					shortest_path.addAll(DijkstraShortestPath.findPathBetween(graph, route.get(i), route.get(i+1)));
				}
				shortest_path.addAll(DijkstraShortestPath.findPathBetween(graph, route.get(route.size()-1), bis));
			}
		}catch(Exception e){
    		e.printStackTrace();
    	}
		System.out.println(shortest_path);
		
		/*
		 * holt sich Achse der Route und fügt sie den Datensätzen hinzu (gemäß der Klasse Route)
		 */
		String[] firstEdge = shortest_path.get(0).toString().split(" : ");
		String firstHst = firstEdge[0].substring(1, firstEdge[0].length());
		String secHst = firstEdge[1].substring(0, firstEdge[1].length()-1);
		lbez = ExcelHandler.hst_tm17.get(firstHst);
		System.out.println(firstHst +"\t"+ entfGes);
		switch(strKat.get(firstHst + " - " + secHst)){
		case "EN":	data.add(new Route(firstHst,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Ergänzungsnetz",lbez));
		break;
		case "BR": 	data.add(new Route(firstHst,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Brennerachse",lbez));
		break;
		case "IN": 	data.add(new Route(firstHst,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Sonstige internationale Achse",lbez));
		break;
		case "KN": 	data.add(new Route(firstHst,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Sonstiges Kernnetz",lbez));
		break;
		case "WA": 	data.add(new Route(firstHst,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Westachse",lbez));
		break;
		}
		for(int i = 0; i < shortest_path.size(); i++){
			String[] edge = shortest_path.get(i).toString().split(" : ");
			String hst1 = edge[0].substring(1, edge[0].length());
			String hst2 = edge[1].substring(0, edge[1].length()-1);
			hst_s = hst2;
			entfGes = entfGes.add(ExcelHandler.bstn17.get(hst1+" / "+hst2));
			lbez = ExcelHandler.hst_tm17.get(hst2);
			switch(strKat.get(hst1 + " - " + hst2)){
			case "EN": 	en = en.add(ExcelHandler.bstn17.get(hst1+" / "+hst2));
						data.add(new Route(hst2,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Ergänzungsnetz",lbez));
			break;
			case "BR": 	br = br.add(ExcelHandler.bstn17.get(hst1+" / "+hst2));
						data.add(new Route(hst2,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Brennerachse",lbez));
			break;
			case "IN": 	ia = ia.add(ExcelHandler.bstn17.get(hst1+" / "+hst2));
						data.add(new Route(hst2,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Sonstige internationale Achse",lbez));
			break;
			case "KN": 	kn = kn.add(ExcelHandler.bstn17.get(hst1+" / "+hst2));
						data.add(new Route(hst2,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Sonstiges Kernnetz",lbez));
			break;
			case "WA": 	wa = wa.add(ExcelHandler.bstn17.get(hst1+" / "+hst2));
						data.add(new Route(hst2,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Westachse",lbez));
			break;
			}
			System.out.println(hst2 +"\t"+ entfGes);	
		}
		wegstrecke.put("Ges", entfGes);
    	wegstrecke.put("WA", wa);
    	wegstrecke.put("IA", ia);
    	wegstrecke.put("KN", kn);
    	wegstrecke.put("EN", en);
    	wegstrecke.put("BR", br);
    	
    	System.out.println("Wa: " + wegstrecke.get("WA"));
    	System.out.println("Ia: " + wegstrecke.get("IA"));
    	System.out.println("Kn: " + wegstrecke.get("KN"));
    	System.out.println("En: " + wegstrecke.get("EN"));
    	System.out.println("Br: " + wegstrecke.get("BR"));
    	System.out.println("Ges: " + wegstrecke.get("Ges"));
    	
    	this.wegstrecke_fin = wegstrecke;
    	
    	String kmGes = wegstrecke_fin.get("Ges").setScale(2, BigDecimal.ROUND_UP).toString();
    	this.cm.getTextFieldKm().setText(kmGes);
    	this.cm.getRouteTable().getItems().clear();
    	this.cm.getRouteTable().getItems().addAll(data);
    	this.excelData = data;
	}
	
	public void init18(String vonText, String bisText, ArrayList<String> route) {
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);
		BigDecimal entfGes = new BigDecimal(0, MathContext.DECIMAL32);
		String hst_s = "";
		String lbez = "";
		String von = vonText;
		String bis = bisText;
		ArrayList<String> vias = route;
		List shortest_path = new ArrayList<>();
		
		TreeMap<String, String> strKat = ExcelHandler.strKat18;
		
		TreeMap<String,BigDecimal> wegstrecke = new TreeMap<>();
		
		ObservableList<Route> data = FXCollections.observableArrayList();
    	
		BigDecimal wa = new BigDecimal(0, MathContext.DECIMAL32);
		BigDecimal ia = new BigDecimal(0, MathContext.DECIMAL32);
		BigDecimal kn = new BigDecimal(0, MathContext.DECIMAL32);
		BigDecimal en = new BigDecimal(0, MathContext.DECIMAL32);
		BigDecimal br = new BigDecimal(0, MathContext.DECIMAL32);

		for(int i = 0; i < ExcelHandler.bstnBST18.size() ;i++){
			graph.addVertex((String) ExcelHandler.bstnBST18.get(i).toString());
		}
		
		
		//System.out.println(graph.vertexSet());
		
		Set set = ExcelHandler.bstn18.entrySet();
		Iterator it = set.iterator();
		
		while(it.hasNext()){
			Map.Entry me = (Map.Entry)it.next();
			String[] halte = me.getKey().toString().split(" / ");
			double entf = ((BigDecimal) me.getValue()).doubleValue();

			DefaultWeightedEdge e = graph.addEdge(halte[0], halte[1]);
			graph.setEdgeWeight(e, entf);
		}

		try{
			if(this.cm.getListVia().getItems().size() <= 0){
				System.out.println("Shortest path");
				shortest_path = DijkstraShortestPath.findPathBetween(graph, von, bis);
			}else{
				shortest_path = DijkstraShortestPath.findPathBetween(graph, von, route.get(0));
				for(int i = 0; i < route.size()-1; i++){
					shortest_path.addAll(DijkstraShortestPath.findPathBetween(graph, route.get(i), route.get(i+1)));
				}
				shortest_path.addAll(DijkstraShortestPath.findPathBetween(graph, route.get(route.size()-1), bis));
			}
		}catch(Exception e){
    		e.printStackTrace();
    	}
		System.out.println(shortest_path);
		
		String[] firstEdge = shortest_path.get(0).toString().split(" : ");
		String firstHst = firstEdge[0].substring(1, firstEdge[0].length());
		String secHst = firstEdge[1].substring(0, firstEdge[1].length()-1);
		lbez = ExcelHandler.hst_tm18.get(firstHst);
		System.out.println(firstHst +"\t"+ entfGes);
		switch(strKat.get(firstHst + " - " + secHst)){
		case "EN":	data.add(new Route(firstHst,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Ergänzungsnetz",lbez));
		break;
		case "BR": 	data.add(new Route(firstHst,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Brennerachse",lbez));
		break;
		case "IN": 	data.add(new Route(firstHst,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Sonstige internationale Achse",lbez));
		break;
		case "KN": 	data.add(new Route(firstHst,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Sonstiges Kernnetz",lbez));
		break;
		case "WA": 	data.add(new Route(firstHst,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Westachse",lbez));
		break;
		}
		for(int i = 0; i < shortest_path.size(); i++){
			String[] edge = shortest_path.get(i).toString().split(" : ");
			String hst1 = edge[0].substring(1, edge[0].length());
			String hst2 = edge[1].substring(0, edge[1].length()-1);
			hst_s = hst2;
			entfGes = entfGes.add(ExcelHandler.bstn18.get(hst1+" / "+hst2));
			lbez = ExcelHandler.hst_tm18.get(hst2);
			switch(strKat.get(hst1 + " - " + hst2)){
			case "EN": 	en = en.add(ExcelHandler.bstn18.get(hst1+" / "+hst2));
						data.add(new Route(hst2,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Ergänzungsnetz",lbez));
			break;
			case "BR": 	br = br.add(ExcelHandler.bstn18.get(hst1+" / "+hst2));
						data.add(new Route(hst2,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Brennerachse",lbez));
			break;
			case "IN": 	ia = ia.add(ExcelHandler.bstn18.get(hst1+" / "+hst2));
						data.add(new Route(hst2,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Sonstige internationale Achse",lbez));
			break;
			case "KN": 	kn = kn.add(ExcelHandler.bstn18.get(hst1+" / "+hst2));
						data.add(new Route(hst2,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Sonstiges Kernnetz",lbez));
			break;
			case "WA": 	wa = wa.add(ExcelHandler.bstn18.get(hst1+" / "+hst2));
						data.add(new Route(hst2,entfGes.setScale(2, BigDecimal.ROUND_UP).toString(),"Westachse",lbez));
			break;
			}
			System.out.println(hst2 +"\t"+ entfGes);	
		}
		wegstrecke.put("Ges", entfGes);
    	wegstrecke.put("WA", wa);
    	wegstrecke.put("IA", ia);
    	wegstrecke.put("KN", kn);
    	wegstrecke.put("EN", en);
    	wegstrecke.put("BR", br);
    	
    	System.out.println("Wa: " + wegstrecke.get("WA"));
    	System.out.println("Ia: " + wegstrecke.get("IA"));
    	System.out.println("Kn: " + wegstrecke.get("KN"));
    	System.out.println("En: " + wegstrecke.get("EN"));
    	System.out.println("Br: " + wegstrecke.get("BR"));
    	System.out.println("Ges: " + wegstrecke.get("Ges"));
    	
    	this.wegstrecke_fin = wegstrecke;
    	
    	String kmGes = wegstrecke_fin.get("Ges").setScale(2, BigDecimal.ROUND_UP).toString();
    	this.cm.getTextFieldKm().setText(kmGes);
    	this.cm.getRouteTable().getItems().clear();
    	this.cm.getRouteTable().getItems().addAll(data);
    	this.excelData = data;
	}
	
	public TreeMap<String, BigDecimal> getWegstrecke(){
		return this.wegstrecke_fin;
	}
}
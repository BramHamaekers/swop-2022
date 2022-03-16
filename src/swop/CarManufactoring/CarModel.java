package swop.CarManufactoring;

import swop.Parts.*;

import java.util.*;
import java.util.Map.Entry;

public class CarModel {
	
	List<Part> parts = new LinkedList<>();
	
	
	  public CarModel(int model, Map<String, String> parts) {
	        for(var part : parts.entrySet()) {
	        	this.parts.add(this.createPart(part));
	        }
	    }


	private Part createPart(Entry<String, String> part) {
		return switch (part.getKey().toLowerCase()) {
		case  "body" -> new Body(part.getValue());
		case "color" -> new Color(part.getValue());
		case "engine" -> new Engine(part.getValue());
		case "gearbox" -> new GearBox(part.getValue());
		case "seats" -> new Seats(part.getValue());
		case "airco" -> new Airco(part.getValue());
		case "wheels" -> new Wheels(part.getValue());
		default ->  throw new IllegalArgumentException("invalid part");
		}; 		   
	}
	
	public List<Part> getParts() {
		return this.parts;
	}
	
	public Map<String, String> getPartsMap() {
		Map<String, String> map = new HashMap<>();
		for(Part part: parts) map.put(part.getName(), part.getValue());
		return map;
	}

	public String getValueOfPart(Part part) {
		if(part == null) {
			 throw new IllegalArgumentException("Can't retrieve value (part = null)");
		}
		for(Part p : this.parts) if(part.getClass() == p.getClass()) return p.getValue() ;
		 throw new IllegalArgumentException("invalid part");
	}
	
	
	

  /*  private Map<String, String> parts= new HashMap<>();

    public CarModel(Map<String, String> parts) {
        LinkedHashMap<String, List<String>> optionsMap = Database.openDatabase("carOptions.json", "component", "options");
        List<String> reqParts = new ArrayList<>();
        optionsMap.forEach((String component, List<String> opts) -> reqParts.add(component));
        for(String comp : parts.keySet()){
            //check if all components are present
            if (!reqParts.contains(comp))
                throw new IllegalArgumentException("not all parts selected");
            //check if selected option is valid
            if (!optionsMap.get(comp).contains(parts.get(comp)))
                throw new IllegalArgumentException("not a valid option");
        }
        this.setParts(parts);
    }

    public Map<String, String> getParts() {
        return parts;
    }
    
    public String getValueOfPart(String part) {
    	try { return parts.get(part);}
    	catch(Exception e) { 
    		System.out.println("Invalid part");
    		return null;}
    }

    public void setParts(Map<String, String> parts) {
        this.parts = parts;
    } */
}

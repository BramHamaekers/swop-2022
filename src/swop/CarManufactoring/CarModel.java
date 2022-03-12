package swop.CarManufactoring;

import swop.Database.Database;

import java.util.*;

public class CarModel {

    private Map<String, String> parts= new HashMap<>();

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
    }
}

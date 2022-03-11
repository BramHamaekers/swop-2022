package swop.CarManufactoring;

import swop.Database.Database;

import java.util.*;

public class CarModel {

    private Map<String, String> parts= new HashMap<>();

    public CarModel(Map<String, String> parts) throws IllegalAccessException {
        LinkedHashMap<String, List<String>> optionsMap = Database.openDatabase("carOptions.json", "component", "options");
        List<String> reqParts = new ArrayList<>();
        optionsMap.forEach((String component, List<String> opts) -> reqParts.add(component));
        for(String comp : parts.keySet()){
            //check if all components are present
            if (!reqParts.contains(comp))
                throw new IllegalAccessException("not all parts selected");
            //check if selected option is valid
            if (!optionsMap.get(comp).contains(parts.get(comp)))
                throw new IllegalAccessException("not a valid option");
        }
        this.setParts(parts);
//        System.out.println(parts);
    }

    public Map<String, String> getParts() {
        return parts;
    }
    
    public String getValueOfPart(String part) {
    	return parts.get(part);
    }

    public void setParts(Map<String, String> parts) {
        this.parts = parts;
    }
}

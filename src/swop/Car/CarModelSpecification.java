package swop.Car;

import swop.Parts.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CarModelSpecification {
	
	Map<String, String> chosenOptions;

	public CarModelSpecification(Map<String, String> parts){
		// todo: check for airco etc
		this.chosenOptions = parts;
	}

	public Map<String, String> getAllParts(){
		//https://stackoverflow.com/questions/28288546/how-to-copy-hashmap-not-shallow-copy-in-java
		return this.chosenOptions.entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	public String getPart(String key){
		return this.chosenOptions.get(key);
	}
	
//	/**
//	 * create a new car model given the parts and model choice.
//	 * @param model the model of car
//	 * @param parts the selected parts
//	 */
//	 public CarModelSpecification(int model, Map<String, String> parts) {
//		 if (parts.size() != 7) throw new IllegalArgumentException("The car model does not have the right amount of parts");
//	        for(var part : parts.entrySet()) {
//	        	this.carOptionCategories.add(this.createPart(part));
//	        }
////	     if(!containAllPartsOfModel())throw new IllegalArgumentException("Invalid parts");
//	 }
//
//	/**
//	 * create a new part from a given description of a part
//	 * @param part Description of a part
//	 * @return new Part
//	 */
//	private CarOptionCategory createPart(Entry<String, String> part) {
//		if (part == null) throw new IllegalArgumentException("part is null");
//		return switch (part.getKey().toLowerCase()) {
//			case "body" -> new Body(part.getValue());
//			case "color" -> new Color(part.getValue());
//			case "engine" -> new Engine(part.getValue());
//			case "gearbox" -> new GearBox(part.getValue());
//			case "seats" -> new Seats(part.getValue());
//			case "airco" -> new Airco(part.getValue());
//			case "wheels" -> new Wheels(part.getValue());
//			default ->  throw new IllegalArgumentException("invalid part");
//		};
//	}
//
//	public List<CarOptionCategory> getParts() {
//		return this.carOptionCategories;
//	}
//
//	/**
//	 * Returns Map<String, String> (part.name => part.value)
//	 * @return
//	 */
//	public Map<String, String> getPartsMap() {
//		Map<String, String> map = new HashMap<>();
//		for(CarOptionCategory carOptionCategory : carOptionCategories) map.put(carOptionCategory.getName(), carOptionCategory.getValue());
//		return map;
//	}
//
//	/**
//	 * get a given part value from the part
//	 * @param carOptionCategory given part
//	 * @return part value (String)
//	 */
//	public String getValueOfPart(CarOptionCategory carOptionCategory) {
//		if(carOptionCategory == null) {
//			 throw new IllegalArgumentException("Can't retrieve value (part = null)");
//		}
//		for(CarOptionCategory p : this.carOptionCategories) if(carOptionCategory.getClass() == p.getClass()) return p.getValue();
//		throw new IllegalArgumentException("invalid part");
//	}
}

package swop.CarManufactoring;

import swop.Parts.*;

import java.util.*;
import java.util.Map.Entry;

public class CarModel {
	
	List<Part> parts = new LinkedList<>();
	
	/**
	 * create a new car model given the parts and model choice.
	 * @param model the model of car
	 * @param parts the selected parts
	 */
	 public CarModel(int model, Map<String, String> parts) {
	        for(var part : parts.entrySet()) {
	        	this.parts.add(this.createPart(part));
	        }
	 }

	/**
	 * create a new part from a given description of a part
	 * @param part Description of a part
	 * @return new Part
	 */
	private Part createPart(Entry<String, String> part) {
		if (part == null) throw new IllegalArgumentException("part is null");
		return switch (part.getKey().toLowerCase()) {
			case "body" -> new Body(part.getValue());
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
	
	/**
	 * Returns Map<String, String> (part.name => part.value)
	 * @return
	 */
	public Map<String, String> getPartsMap() {
		Map<String, String> map = new HashMap<>();
		for(Part part: parts) map.put(part.getName(), part.getValue());
		return map;
	}

	/**
	 * get a given part value from the part
	 * @param part given part
	 * @return part value (String)
	 */
	public String getValueOfPart(Part part) {
		if(part == null) {
			 throw new IllegalArgumentException("Can't retrieve value (part = null)");
		}
		for(Part p : this.parts) if(part.getClass() == p.getClass()) return p.getValue();
		throw new IllegalArgumentException("invalid part");
	}
}

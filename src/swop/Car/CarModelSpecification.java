package swop.Car;

import swop.Parts.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CarModelSpecification {
	
	Map<String, String> chosenOptions;

	public CarModelSpecification(Map<String, String> chosenOptions){
		checkConstraints(chosenOptions);
		this.chosenOptions = chosenOptions;
	}

	/**
	 * Check if given chosenOptions map is valid for a CarModelSpecification
	 * @param chosenOptions chosen options
	 */
	private void checkConstraints(Map<String, String> chosenOptions) {
		if (!this.isValidBodySpoilerCombination(chosenOptions.get("Body"), chosenOptions.get("Spoiler"))) {
			throw new IllegalArgumentException("Spoiler is mandatory when choosing a sport body");
		}
		if (!this.isValidBodyEngineCombination(chosenOptions.get("Body"), chosenOptions.get("Engine"))) {
			throw new IllegalArgumentException("Engine must be performance or ultra when choosing a sport body");
		}
		if (!this.isValidEngineAirco(chosenOptions.get("Engine"), chosenOptions.get("Airco"))) {
			throw new IllegalArgumentException("Airco must be manual or none if you select the ultra engine");
		}
	}

	/**
	 * If you select the ultra engine, you can only fit the manual airco into your car (or none)
	 * @param engine chosen engine
	 * @param airco chosen airco
	 * @return whether the engine & airco combination is valid
	 */
	private boolean isValidEngineAirco(String engine, String airco) {
		if (engine.contains("ultra")) return airco == null || airco.equals("manual");
		return true;
	}

	/**
	 * If you select a sport body, you must also select the performance or ultra engine
	 * @param body chosen body
	 * @param engine chosen spoiler
	 * @return whether the body & engine combination is valid
	 */
	private boolean isValidBodyEngineCombination(String body, String engine) {
		if (body.equals("sport")) return engine.contains("performance") || engine.contains("ultra");
		return true;
	}

	/**
	 * If you select a sport body, a spoiler is mandatory
	 * @param body chosen body
	 * @param spoiler chosen spoiler
	 * @return whether the body & spoiler combination is valid
	 */
	private boolean isValidBodySpoilerCombination(String body, String spoiler) {
		if (body.equals("sport")) return spoiler != null;
		return true;
	}

	public Map<String, String> getAllParts(){
		//https://stackoverflow.com/questions/28288546/how-to-copy-hashmap-not-shallow-copy-in-java
		return this.chosenOptions.entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	public String getPart(String key){
		return this.chosenOptions.get(key);
	}

	public Map<String, String> getPartsMap() {
		return this.getAllParts();
	}

	/**
	 * get a given part value from the part
	 * @param selectedCategory the selected category fe. Body
	 * @return part value (String)
	 */
	public String getValueOfPart(String selectedCategory) {
		if(selectedCategory == null) {
			 throw new IllegalArgumentException("Can't retrieve value (part = null)");
		}
		if (!this.chosenOptions.containsKey(selectedCategory)) {
			throw new IllegalArgumentException("invalid category");
		}
		return this.chosenOptions.get(selectedCategory);
	}
}

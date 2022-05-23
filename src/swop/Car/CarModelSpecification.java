package swop.Car;


import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * A selection for each category for a carModel
 */
public class CarModelSpecification {
	
	Map<String, String> chosenOptions;

	/**
	 * initialize this specification with a map consisting of the category and the selection for that specification
	 * @param chosenOptions the selected options for the car
	 */
	public CarModelSpecification(Map<String, String> chosenOptions){
		this.checkConstraints(chosenOptions);
		this.chosenOptions = chosenOptions;
	}

	/**
	 * Check if given chosenOptions map is valid for a CarModelSpecification
	 * @param chosenOptions chosen options
	 */
	private void checkConstraints(Map<String, String> chosenOptions) {
		if (chosenOptions == null)
			throw new IllegalArgumentException("no options chosen");
		if (!this.isValidBodySpoilerCombination(chosenOptions.get("Body"), chosenOptions.get("Spoiler")))
			throw new IllegalArgumentException("Spoiler is mandatory when choosing a sport body");

		if (!this.isValidBodyEngineCombination(chosenOptions.get("Body"), chosenOptions.get("Engine")))
			throw new IllegalArgumentException("Engine must be performance or ultra when choosing a sport body");

		if (!this.isValidEngineAirco(chosenOptions.get("Engine"), chosenOptions.get("Airco")))
			throw new IllegalArgumentException("Airco must be manual or none if you select the ultra engine");
	}

	/**
	 * If you select the ultra engine, you can only fit the manual airco into your car (or none)
	 * @param engine chosen engine
	 * @param airco chosen airco
	 * @return returns whether the engine and airco combination is valid
	 */
	private boolean isValidEngineAirco(String engine, String airco) {
		return !engine.contains("ultra") || airco == null || airco.equals("manual");
	}

	/**
	 * If you select a sport body, you must also select the performance or ultra engine
	 * @param body chosen body
	 * @param engine chosen spoiler
	 * @return whether the body and engine combination is valid
	 */
	private boolean isValidBodyEngineCombination(String body, String engine) {
		return !body.equals("sport") || engine.contains("performance") || engine.contains("ultra");
	}

	/**
	 * If you select a sport body, a spoiler is mandatory
	 * @param body chosen body
	 * @param spoiler chosen spoiler
	 * @return whether the body and spoiler combination is valid
	 */
	private boolean isValidBodySpoilerCombination(String body, String spoiler) {
		return !body.equals("sport") || spoiler != null;
	}

	/**
	 * get the chosenOptions and its value of the CarModelSpecification as a Map
	 * @return Map of all the chosen options
	 */
	public Map<String, String> getAllParts(){
		//TODO: remove
		//https://stackoverflow.com/questions/28288546/how-to-copy-hashmap-not-shallow-copy-in-java
		return this.chosenOptions.entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	/**
	 * get a given part value from the part
	 * @param selectedCategory the selected category fe. Body
	 * @return part value (String)
	 */
	public String getSelectionForPart(String selectedCategory) {
		if(selectedCategory == null)
			 throw new IllegalArgumentException("Can't retrieve value (part = null)");

		if (!isPartInChosenOptions(selectedCategory))
			throw new IllegalArgumentException("invalid category");

		return this.chosenOptions.get(selectedCategory);
	}
	
	/**
	 * Checks if a part is chosen or not
	 * @param part the part to check
	 * @return True if part is in this.chosenOptions
	 */
	public boolean isPartInChosenOptions(String part) {
		if(part == null) throw new IllegalArgumentException("Can't retrieve value (part = null)");
		return this.chosenOptions.containsKey(part);
	}
}

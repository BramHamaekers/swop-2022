package swop.Car;


import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * A selection for each category for a carModel
 */
public class CarModelSpecification {

	/**
	 * The options chosen for this carModelSpecification
	 */
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
			throw new IllegalArgumentException("ChosenOptions is null");
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
		if (engine == null)
			throw new IllegalArgumentException("Null string provided");
		return !engine.contains("ultra") || airco == null || airco.equals("manual");
	}

	/**
	 * If you select a sport body, you must also select the performance or ultra engine
	 * @param body chosen body
	 * @param engine chosen spoiler
	 * @return whether the body and engine combination is valid
	 */
	private boolean isValidBodyEngineCombination(String body, String engine) {
		if (body == null)
			throw new IllegalArgumentException("Null string provided");
		if (engine == null)
			throw new IllegalArgumentException("Null string provided");
		return !body.equals("sport") || engine.contains("performance") || engine.contains("ultra");
	}

	/**
	 * If you select a sport body, a spoiler is mandatory
	 * @param body chosen body
	 * @param spoiler chosen spoiler
	 * @return whether the body and spoiler combination is valid
	 */
	private boolean isValidBodySpoilerCombination(String body, String spoiler) {
		if (body == null)
			throw new IllegalArgumentException("Null string provided");
		return !body.equals("sport") || spoiler != null;
	}

	/**
	 * get the chosenOptions and its value of the CarModelSpecification as a Map
	 * @return Map of all the chosen options
	 */
	public Map<String, String> getAllParts(){
		return new HashMap<>(this.chosenOptions);
	}

	/**
	 * Returns the value of option given with the parameter
	 * @param selectedPart is the part you want to retrieve the value from
	 * @return part value (String)
	 */
	public String getSelectionForPart(String selectedPart) {
		if(selectedPart == null)
			 throw new IllegalArgumentException("Can't retrieve value (selectedCategory = null)");

		if (!isPartInChosenOptions(selectedPart))
			throw new IllegalArgumentException("Provided category does not exist");

		return this.chosenOptions.get(selectedPart);
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

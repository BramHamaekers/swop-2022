package swop.UI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.*;

public class GarageHolderUI extends UI {

	public GarageHolderUI() {
		super("garage holder");
		System.out.println("je zit nu in Garage UI");

		//just so program doesnt exit right away
		try {
			int a = System.in.read();
		}
		catch (java.io.IOException e) {
			System.out.println(e);
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void load() {
		// load options
		LinkedHashMap<String, List<String>> optionsMap = this.loadOptionsDatabase();
		if (!isValidOptionsMap(optionsMap)) {
			System.out.println("Fail! optionsMap not valid"); //TODO: should throw error
		}
		
		// Display ordering from
		this.displayOrderingForm(optionsMap);
	}

	/**
	 * Displays the ordering from given a list of components and its options
	 * @param optionsMap list of components and its options
	 */
	private void displayOrderingForm(Map<String, List<String>> optionsMap) {
		System.out.println("============ Ordering Form ============");
		optionsMap.forEach((key, value) -> {
			System.out.print(key + ": ");
			final int[] itemNumber = {-1};
			value.forEach(v -> System.out.printf("[%s] %s, ", itemNumber[0] += 1, v));
			System.out.printf("%n");
		});
		System.out.println("=======================================");
	}

	/**
	 * Parses the carOptions.json file and returns it as a LinkedHashMap<String, List<String>> of all available options
	 * @return LinkedHashMap<String, List<String>> || null
	 */
	private LinkedHashMap<String, List<String>> loadOptionsDatabase() {
		JSONParser jsonParser = new JSONParser();
		try (FileReader data = new FileReader("carOptions.json")) {
			return this.parseOptionsJSONArrayToMap((JSONArray) jsonParser.parse(data));
		}
		catch (Exception e) {e.printStackTrace();}
		return null;
	}

	/**
	 * Parses the JSONArray obtainded from carOptions.json and returns it as a linked hashmap
	 * @param optionsList JSONArray containing the components and its available options
	 * @return LinkedHashMap<String, List<String>> of all components and its available options
	 */
	private LinkedHashMap<String, List<String>> parseOptionsJSONArrayToMap(JSONArray optionsList) {
		LinkedHashMap <String, List<String>> map = new LinkedHashMap<>();
		for (JSONObject user : (Iterable<JSONObject>) optionsList) {
			map.put((String) user.get("component"), (List<String>) user.get("options"));
		}
		return map;
	}

	/**
	 * Check if given optionsMap is a valid userMap
	 * @param optionsMap LinkedHashMap<String, List<String>> to check
	 * @return optionsMap != null
	 */
	private boolean isValidOptionsMap(Map<String, List<String>> optionsMap) {
		return optionsMap != null;
	}
}

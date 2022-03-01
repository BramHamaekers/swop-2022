package swop.Database;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import swop.Users.User;

public class Database {
	
	
	
	/**
	 * tries 2 open new database
	 * @param name
	 * @param keyName
	 * @param valueName
	 * @return
	 */
	public static LinkedHashMap<String, List<String>> openDatabase(String name, String keyName, String valueName) {
		return getDataFromJSONFile(name, keyName, valueName);
	}
	/**
	 * Will return a LinkedHashMap from JSON file .
	 * @param name of JSON file
	 * @param keyName first attribute
	 * @param valueName second attribute
	 * @return Linked Hash Map
	 */
	private static LinkedHashMap<String, List<String>> getDataFromJSONFile(String name, String keyName, String valueName) {
		JSONParser jsonParser = new JSONParser();
		try (FileReader data = new FileReader(name)) {
			return parseJSONArrayToMap((JSONArray) jsonParser.parse(data), keyName, valueName);
		}
		catch (Exception e) {e.printStackTrace();}
		return null;
	}
		
	/**
	 * Will return a list of values linked to a giver key.
	 * @param name
	 * @param keyName
	 * @param valueName
	 * @return
	 */
	public static List<String> getKeyValues(LinkedHashMap<String, List<String>> dataMap, String key) {
		if (dataMap != null) return dataMap.get(key);
		return null;
	}
	
	/**
	 * Controls if a key is a valid key in the map.
	 * @param userDatabase
	 * @param ID
	 * @return
	 */
	public static boolean isValidKey(Map<String, List<String>> userDatabase, String key) {
		if (userDatabase != null) return userDatabase.containsKey(key);
		return false;
	}

	/**
	 *parses JSON array to Linked Hash Map
	 */
	private static LinkedHashMap<String, List<String>> parseJSONArrayToMap(JSONArray List, String keyName, String valueName) {
		LinkedHashMap <String, List<String>> map = new LinkedHashMap<>();
		for (JSONObject user : (Iterable<JSONObject>) List) {
			if(user.get(valueName) instanceof String) {
				List<String> temp = new ArrayList();
				temp.add((String) user.get(valueName));
				map.put((String) user.get(keyName), temp);
			}
			else map.put((String) user.get(keyName), (List<String>) user.get(valueName));
		}
		return map;
	}
}


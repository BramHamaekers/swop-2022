package swop.UI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginUI extends UI{

	private Scanner inputScanner;
	private String userID;
	private Map<String, String> userMap;
	
	public LoginUI() {
		super("login");
	}
	
	
	@Override
	public void load() {
		// Load user map
		this.userMap = this.loadUserDatabase();
		if (!isValidUserMap(userMap))
			System.out.println("Fail! userMap not valid"); //Should throw error
		System.out.println("Welcome!");
		// Ask userID
		do {
			this.inputScanner = new Scanner(System.in);
			System.out.print("Please login with userID: ");
			this.userID = inputScanner.nextLine();

			// Check if userID is a valid userID
			if (!isValidUserID(userID, userMap))
				System.out.println("Fail! userID not valid");  //Should throw error
			else {
				System.out.println("Success! Changing State/UI");

			}
		} while (!isValidUserID(userID, userMap));
	}
	
	/**
	 * temporary
	 */
	public String getKeyValue() {
		return userMap.get(userID);
	}


	/**
	 * Parses the users.json file and returns it as a Map<String, String> of all user in the system
	 * @return Map<String, String> || null
	 */
	private Map<String, String> loadUserDatabase() {
		JSONParser jsonParser = new JSONParser();
        try (FileReader data = new FileReader("users.json")) {
            return parseUserJSONArrayToMap((JSONArray) jsonParser.parse(data));
        } 
        catch (Exception e) {e.printStackTrace();}
		return null;
	}

	/**
	 * Parses the JSONArray obtainded from users.json and returns it as a map
	 * @param userList JSONArray containing the users currently in the system
	 * @return Map<name,job> of all users in the system
	 */
	private Map<String, String> parseUserJSONArrayToMap(JSONArray userList) {
		Map <String, String> map = new HashMap<>();
		for (JSONObject user : (Iterable<JSONObject>) userList) {
			map.put((String) user.get("id"), (String) user.get("job"));
		}
		return map;
	}

	/**
	 * Check if given userMap is a valid userMap
	 * @param userMap Map<name,job> to check
	 * @return userMap != null
	 */
	private boolean isValidUserMap(Map<String, String> userMap) {
		return userMap != null;
	}

	/**
	 * Check if the given userID is a valid userID
	 * @param userID userID to check
	 * @param userMap Map<name,job> of all users in the system
	 * @return userMap.containsKey(userID);
	 */
	private boolean isValidUserID(String userID, Map<String, String> userMap){
		return userMap.containsKey(userID);
	}

}

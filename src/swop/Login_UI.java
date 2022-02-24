package swop;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Login_UI extends UI{

	private Scanner inputScanner;
	private String userID;
	private Map<String, String> userMap;

	@Override
	public void load() {
		// Load user map
		this.userMap = this.loadUserDatabase();

		// Ask userID
		this.inputScanner = new Scanner(System.in);
		System.out.println("Please login with userID.");
		this.userID = inputScanner.nextLine();

		// Check if userID is a valid userID
		if (isValidUserID(userID, userMap)) {
			System.out.println("Succes! Changing State/UI");
		}
		else System.out.println("Fail! userID not valid");
		
	}


	
	private Map<String, String> loadUserDatabase() {
		JSONParser jsonParser = new JSONParser();
        try (FileReader data = new FileReader("users.json"))
        {
            Object data_obj = jsonParser.parse(data);
            return parseUserJSONArrayToMap((JSONArray) data_obj);
        } 
        catch (Exception e) {
        	e.printStackTrace();
            //System.out.println("ERROR COULDN'T CONNECT TO DATABASE");
        }
		return null;
		
	}

	/**
	 * Parses the JSONArray obtainded from users.json and returns it as a map
	 * @param userList JSONArray containing the users currently in the system
	 * @return Map<name,job> of all users in the system
	 */
	Map<String, String> parseUserJSONArrayToMap(JSONArray userList) {
		Map <String, String> map = new HashMap<>();
		for (JSONObject user : (Iterable<JSONObject>) userList) {
			map.put((String) user.get("id"), (String) user.get("job"));
		}
		return map;
	}

	/**
	 * Check if the given userID is a valid userID
	 * @param userID userID to check
	 * @param userMap Map<name,job> of all users in the system
	 * @return userMap.containsKey(userID);
	 */
	public boolean isValidUserID(String userID, Map<String, String> userMap){
		return userMap.containsKey(userID);
	}

}

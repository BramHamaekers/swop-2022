package swop.Main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import swop.UI.LoginUI;
import swop.Users.CarMechanic;
import swop.Users.GarageHolder;
import swop.Users.Manager;
import swop.Users.User;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class AssemAssist {

	private Map <String, User> userMap;
	private User activeUser;

	public AssemAssist() {
    	this.run();
    }
    /**
     * Starts the program
     */
	private void run() {
		// Load user database
		final Map <String, String> userDatabase = loadUserDatabase("users.json");
		if (!isValiduserDataBase(userDatabase))
			System.out.println("Fail! userDataBase not valid"); //TODO: Should throw error

		// Create user objects
		this.userMap = createUserMap(userDatabase);

		//
		this.login();
		
	}

	/************************ Login *************************/

	/**
	 * Handles logging in to the system
	 */
	private void login() {
		LoginUI.init();
		String id = LoginUI.getUserID();
		while (!isValidUserID(id)) {
			System.out.println("Invalid user ID.");
			id = LoginUI.getUserID();
		}
		this.activeUser = this.userMap.get(id);
		this.activeUser.load();
		this.activeUser = null;
		login();	//TODO: option to exit the program
	}

	/**
	 * Check if the given userID is a valid userID
	 * @param userID userID to check
	 * @return userMap.containsKey(userID);
	 */
	private boolean isValidUserID(String userID){
		return this.userMap.containsKey(userID);
	}

	/************************ User Map *************************/

	private Map<String, User> createUserMap(Map<String, String> userDatabase) {
		Map<String, User> userMap = new HashMap<>();
		userDatabase.forEach((id, job) -> userMap.put(id, createUser(id, job)));
		return userMap;
	}

	private User createUser(String id, String job) {
		switch (job) {
			case "garage holder":
				return new GarageHolder(id);
			case "car mechanic":
				return new CarMechanic(id);
			case "manager":
				return new Manager(id);
			default:
				System.out.println("Given job is not a valid job");
				return null; //TODO: should just throw an error and not return anything
		}
	}

	/************************ User Database *************************/

	/**
	 * Parses the users.json file and returns it as a Map<String, String> of all user in the system
	 * @return Map<String, String> || null
	 */
	private Map<String, String> loadUserDatabase(String fileName) {
		JSONParser jsonParser = new JSONParser();
		try (FileReader data = new FileReader(fileName)) {
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
	 * Check if given userDataBase is a valid userDataBase
	 * @param userDataBase Map<name,job> to check
	 * @return userDataBase != null
	 */
	private boolean isValiduserDataBase(Map<String, String> userDataBase) {
		return userDataBase != null; // TODO: check if jobs are valid jobs
	}
}
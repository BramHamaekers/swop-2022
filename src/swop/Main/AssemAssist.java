package swop.Main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import swop.Database.Database;
import swop.UI.LoginUI;
import swop.Users.CarMechanic;
import swop.Users.GarageHolder;
import swop.Users.Manager;
import swop.Users.User;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
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

		this.login();
		
	}

	/************************ Login *************************/

	/**
	 * Handles logging in to the system
	 */
	private void login() {
		// Load user database
		final Map <String, List<String>> userDatabase = Database.openDatabase("users.json", "id", "job");
		LoginUI.init();
		String id = LoginUI.getUserID();
		while (!Database.isValidKey(userDatabase, id)) {
			System.out.println("Invalid user ID.");
			id = LoginUI.getUserID();
		}
		this.userMap = this.createUserMap(userDatabase);
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

	private Map<String, User> createUserMap(Map<String, List<String>> userDatabase) {
		Map<String, User> userMap = new HashMap<>();
		userDatabase.forEach((id, job) -> userMap.put(id, createUser(id, job)));
		return userMap;
	}

	private User createUser(String id, List<String> job) {
		for(String j : job) {
			switch (j) {
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
		return null;
	}

}
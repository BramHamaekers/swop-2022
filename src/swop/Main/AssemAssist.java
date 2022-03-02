package swop.Main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import swop.Database.Database;
import swop.Database.ConvertMapType;
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
		this.userMap = ConvertMapType.changeToUserMap(userDatabase);
		this.activeUser = this.userMap.get(id);
		this.activeUser.load();
		this.activeUser = null;
		login();	//TODO: option to exit the program
	}

}
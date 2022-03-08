package swop.Main;

import swop.CarManufactoring.AssemblyLine;
import swop.Database.Database;
import swop.Database.ConvertMapType;
import swop.UI.LoginUI;
import swop.Users.User;

import java.util.List;
import java.util.Map;

public class AssemAssist {

	private Map <String, User> userMap;
	private AssemblyLine assemblyLine;

	public AssemAssist() {
		this.assemblyLine = new AssemblyLine();
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
		User activeUser = this.userMap.get(id);
		activeUser.load(this);
		login();	//TODO: option to exit the program
	}

}
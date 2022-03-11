package swop.Main;

import swop.CarManufactoring.AssemblyLine;
import swop.CarManufactoring.CarOrder;
import swop.Exceptions.NotAllTasksCompleteException;
import swop.Database.Database;
import swop.Database.ConvertMapType;
import swop.UI.LoginUI;
import swop.Users.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
		while (!(Objects.equals(id, "QUIT"))){
			while (!Database.isValidKey(userDatabase, id)) {
				System.out.println("Invalid user ID, type QUIT to exit");
				id = LoginUI.getUserID();
			}
			if(this.userMap == null) this.userMap = ConvertMapType.changeToUserMap(userDatabase);
			User activeUser = this.userMap.get(id);
			activeUser.load(this);
			activeUser = null;
			id = LoginUI.getUserID();
		}
	}

	/************************ Assembly *************************/

	public void addOrder(CarOrder carOrder) { //TODO should not be public?
		this.assemblyLine.addToAssembly(carOrder);
	}

	public void advanceAssembly() throws NotAllTasksCompleteException { //TODO should not be public?
		this.assemblyLine.advanceAssemblyLine();
	}
	

	public String[] getCurrentAssemblyStatus() {
		return this.assemblyLine.getCurrentStatus();
	}
	public String[] getAdvancedAssemblyStatus() {
		return this.assemblyLine.getAdvancedStatus();
	}
	public List<String> getStations() {
		return this.assemblyLine.getWorkstations();
	}
	public Set<String> getsAvailableTask(String string) {
		return this.assemblyLine.getAvailableTasks(string);
	}
	public String getTaskInfo(String task) {
		return this.assemblyLine.getTaskInfo(task);
	}

}
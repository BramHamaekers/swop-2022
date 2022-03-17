package swop.Main;

import swop.CarManufactoring.AssemblyLine;
import swop.CarManufactoring.CarOrder;
import swop.CarManufactoring.Task;
import swop.Exceptions.IlligalUserException;
import swop.Exceptions.NotAllTasksCompleteException;
import swop.Database.Database;
import swop.Database.ConvertMapType;
import swop.UI.LoginUI;
import swop.Users.CarMechanic;
import swop.Users.GarageHolder;
import swop.Users.Manager;
import swop.Users.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class AssemAssist {

	private Map <String, User> userMap;
	private AssemblyLine assemblyLine;
	User activeUser;

	public AssemAssist() {
		this.assemblyLine = new AssemblyLine();
    }
    /**
     * Starts the program
     */
	public void run() {

		this.login();
		
	}

	/************************ Login *************************/

	/**
	 * Handles logging in to the system
	 */
	private void login() {
		LoginUI.init();
		String id = LoginUI.getUserID();
		this.loadUser(id);		
	}

	/**
	 * Loads the User from the database (database is currently a JSON file)
	 * @param id is the user ID
	 */
	private void loadUser(String id) {
		// Load user database
		final Map <String, List<String>> userDatabase = Database.openDatabase("users.json", "id", "job");
		while (!Database.isValidKey(userDatabase, id) && !(Objects.equals(id, "QUIT"))) {
			System.out.println("Invalid user ID, type QUIT to exit");
			id = LoginUI.getUserID();
		}
		if(id.equals("QUIT")) return;
		if(this.getUserMap() == null) this.setUserMap(ConvertMapType.changeToUserMap(userDatabase));
		activeUser = this.getUserMap().get(id);
		activeUser.load(this);
		login();
		
	}
	
	
	
	/************************ Users can communicate with assembly line via these methods*************************/

	private boolean isValidUser(String name) {
		if(this.activeUser == null) return false;
		
		return switch(name) {
		case "manager" -> this.activeUser instanceof Manager;
		case "garage holder" -> this.activeUser instanceof GarageHolder;
		case "car mechanic" -> this.activeUser instanceof CarMechanic;
		default -> false;
		};
	}
	
	/***********Methods used by garage holder************/
	
	public void addOrder(CarOrder carOrder) {
		if(isValidUser("garage holder"))this.assemblyLine.addToAssembly(carOrder);
		else throw new IlligalUserException("addOrder()");
	}
	
	/***********Methods used by manager************/
	
	public void advanceAssembly(int minutes) throws NotAllTasksCompleteException {
		if(isValidUser("manager"))this.assemblyLine.advanceAssemblyLine(minutes);
		else throw new IlligalUserException("advanceAssembly()");
	}	

	public List<String> getCurrentAssemblyStatus() {
		return this.assemblyLine.getCurrentStatus();
	}
	
	public List<String> getAdvancedAssemblyStatus() {
		return this.assemblyLine.getAdvancedStatus();
	}
	
	/***********Methods used by car mechanic************/
	
	public void completeTask(Task task) {
		if(isValidUser("car mechanic"))this.assemblyLine.completeTask(task);
		else throw new IlligalUserException("completeTask()");
		
	}
	
	public List<String> getStations() {
		return this.assemblyLine.getWorkstations();
	}
	
	public Set<Task> getsAvailableTasks(String string) {
		return this.assemblyLine.getUncompletedTasks(string);
	}
	
	public String getTaskDescription(Task task) {
		return this.assemblyLine.getTaskDescription(task);
	}

	public Map<String, User> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, User> userMap) {
		this.userMap = userMap;
	}
}
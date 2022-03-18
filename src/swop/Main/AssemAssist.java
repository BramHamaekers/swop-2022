package swop.Main;

import swop.CarManufactoring.AssemblyLine;
import swop.CarManufactoring.CarOrder;
import swop.CarManufactoring.Task;
import swop.Exceptions.IllegalUserException;
import swop.Exceptions.NotAllTasksCompleteException;
import swop.Database.Database;
import swop.Database.ConvertMapType;
import swop.UI.LoginUI;
import swop.Users.CarMechanic;
import swop.Users.GarageHolder;
import swop.Users.Manager;
import swop.Users.User;

import java.util.*;

public class AssemAssist {

	private Map <String, User> userMap;
	private final AssemblyLine assemblyLine;
	User activeUser;

	public AssemAssist() {
		this.assemblyLine = new AssemblyLine();
		this.loadUserMap();
    }

	/**
	 * load userId's and their functions
	 */
    private void loadUserMap() {
//    	final Map <String, List<String>> userDatabase = Database.openDatabase("users.json", "id", "job");
//		System.out.println(userDatabase);
		// hardcoded because json doesnt work in a jar file
		final Map <String, List<String>> userDatabase = new HashMap<>() {{
			put("a", List.of("garage holder"));
			put("b", List.of("car mechanic"));
			put("c", List.of("manager"));
		}};
    	if(this.getUserMap() == null) this.setUserMap(ConvertMapType.changeToUserMap(userDatabase));
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
		while (!Database.isValidKey(this.getUserMap(), id) && !(Objects.equals(id, "QUIT"))) {
			System.out.println("Invalid user ID, type QUIT to exit");
			id = LoginUI.getUserID();
		}
		if(id.equals("QUIT")) return;
		activeUser = this.getUserMap().get(id);
		activeUser.load(this);
		login();
		
	}
	
	
	
	/************************ Users can communicate with assembly line via these methods*************************/

	/**
	 * Check if function is valid
	 * @param name name of function
	 * @return whether function is valid
	 */
	private boolean isValidUser(String name) {
		if (name==null) throw new IllegalArgumentException("no name for user");
		if(this.activeUser == null) return false;
		
		return switch(name) {
			case "manager" -> this.activeUser instanceof Manager;
			case "garage holder" -> this.activeUser instanceof GarageHolder;
			case "car mechanic" -> this.activeUser instanceof CarMechanic;
			default -> false;
		};
	}
	
	/***********Methods used by garage holder************/

	/**
	 * add an order to assembly line
	 * @param carOrder the specified order
	 */
	public void addOrder(CarOrder carOrder) {
		if (carOrder == null) throw new IllegalArgumentException("carorder is null");
		if(isValidUser("garage holder")) this.getAssemblyLine().addToAssembly(carOrder);
		else throw new IllegalUserException("addOrder()");
	}
	
	/***********Methods used by manager************/

	/**
	 * advance the assembly line
	 * @param minutes minutes past since task started
	 * @throws NotAllTasksCompleteException thrown if there are still tasks not done
	 */
	public void advanceAssembly(int minutes) throws NotAllTasksCompleteException {
		if(isValidUser("manager")) this.getAssemblyLine().advanceAssemblyLine(minutes);
		else throw new IllegalUserException("advanceAssembly()");
	}	

	public List<String> getCurrentAssemblyStatus() {
		return this.getAssemblyLine().getCurrentStatus();
	}
	
	public List<String> getAdvancedAssemblyStatus() {
		return this.getAssemblyLine().getAdvancedStatus();
	}
	
	/***********Methods used by car mechanic************/

	/**
	 * mechanic completes a task
	 * @param task task which is completed
	 */
	public void completeTask(Task task) {
		if (task == null) throw new IllegalArgumentException("task is null");
		if(isValidUser("car mechanic")) this.getAssemblyLine().completeTask(task);
		else throw new IllegalUserException("completeTask()");
		
	}
	
	public List<String> getStations() {
		return this.getAssemblyLine().getWorkstationNames();
	}
	
	public Set<Task> getsAvailableTasks(String string) {
		return this.getAssemblyLine().getUncompletedTasks(string);
	}
	
	public String getTaskDescription(Task task) {
		return this.getAssemblyLine().getTaskDescription(task);
	}

	public Map<String, User> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, User> userMap) {
		this.userMap = userMap;
	}

	public AssemblyLine getAssemblyLine() {
		return assemblyLine;
	}
}
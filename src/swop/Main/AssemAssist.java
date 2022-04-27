package swop.Main;

import swop.Car.CarOrder;
import swop.CarManufactoring.*;
import swop.Exceptions.IllegalUserException;
import swop.Miscellaneous.Statistics;
import swop.Records.allStats;
import swop.UI.LoginUI;
import swop.Users.CarMechanic;
import swop.Users.GarageHolder;
import swop.Users.Manager;
import swop.Users.User;
import java.util.*;

public class AssemAssist {

	private final CarManufacturingController controller;
	private final Statistics statistics;
	private User activeUser;
	final Map <String, User> userDatabase = new HashMap<>() {{
		put("a", new GarageHolder("a"));
		put("b", new CarMechanic("b"));
		put("c", new Manager("c"));
	}};

	public AssemAssist() {
		this.statistics = new Statistics();
		this.controller = new CarManufacturingController();
		this.controller.addListener(statistics.statisticsListener);
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
		while (!this.userDatabase.containsKey(id) && !(Objects.equals(id, "QUIT"))) {
			System.out.println("Invalid user ID, type QUIT to exit");
			id = LoginUI.getUserID();
		}
		if(id.equals("QUIT")) return;
		activeUser = this.userDatabase.get(id);
		activeUser.load(this);
		login();	
	}

	/**
	 * Returns the assemblyLine associated with the system
	 * @return this.assemblyLine
	 */
	private AssemblyLine getAssemblyLine() {
		return this.controller.getAssembly();
	}
	
	//for order new car test
	public CarManufacturingController getController() {
		return this.controller;
	}
	
	/**
	 * Gives you a copy of the user data base in the form of Map <String, User>
	 * @return userDatabase
	 */
	public Map <String, User> getUserMap(){
		return Map.copyOf(userDatabase);
		
	}
	
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
	
	/************************ Users can communicate with assembly line via these methods*************************/
	
	/***********Methods used by garage holder************/

	/**
	 * add an order to assembly line
	 * @param carOrder the specified order
	 */
	public void addOrder(CarOrder carOrder) {
		if (carOrder == null) throw new IllegalArgumentException("car order is null");
		if(isValidUser("garage holder")) this.controller.addOrderToQueue(carOrder);
		else throw new IllegalUserException("addOrder()");
	}
	
	/***********Methods used by car mechanic************/

	/**
	 * mechanic completes a task
	 * @param task task which is completed
	 * @param time 
	 */
	public void completeTask(Task task, int time) {
		if (task == null) throw new IllegalArgumentException("task is null");
		if(isValidUser("car mechanic")) {
			task.completeTask(time);
			}
		else throw new IllegalUserException("completeTask()");
		
	}
	
	public List<String> getStationsNames() {
		return this.getAssemblyLine().getWorkstationNames();
	}

	public List<WorkStation> getStations() {
		return this.getAssemblyLine().getWorkStations();
	}
	
	public List<Task> getsAvailableTasks(String string) {
		return this.getAssemblyLine().getUncompletedTasks(string);
	}
	
	public List<String> getTaskDescription(Task task) {
		return task.getTaskDescription();
	}
	public allStats getStats() {
		return this.statistics.getOrderStats();
		
	}
}


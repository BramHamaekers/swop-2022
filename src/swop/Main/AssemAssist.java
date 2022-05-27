package swop.Main;

import swop.Car.CarOrder;
import swop.CarManufactoring.*;
import swop.Miscellaneous.Statistics;
import swop.Records.AllStats;
import swop.Users.CarMechanic;
import swop.Users.GarageHolder;
import swop.Users.Manager;
import swop.Users.User;
import java.util.*;

/**
 * AssemAssist is the main program which starts all other classes
 */
public class AssemAssist {

	private final CarManufacturingController controller;
	private final Statistics statistics;
	final Map <String, User> userDatabase = new HashMap<>();

	/**
	 * Initializes the class with the relevant statistics and the controller
	 */
	public AssemAssist() {
		this.statistics = new Statistics();
		this.controller = new CarManufacturingController();
		this.controller.addListener(statistics.statisticsListener);
		this.userDatabase.put("a", new GarageHolder("a", this));
		this.userDatabase.put("b", new CarMechanic("b", this));
		this.userDatabase.put("c", new Manager("c", this));
    }

	/**
	 * getter for carcontroller
	 * @return the CarManufacturingController
	 */
	public CarManufacturingController getController() {
		return this.controller;
	}
	
	/**
	 * Gives you a copy of the user data base in the form of a Map
	 * @return userDatabase
	 */
	public Map <String, User> getUserMap(){
		return Map.copyOf(userDatabase);
		
	}
	
	/**
	 * add an order to assembly line
	 * @param carOrder the specified order
	 */
	public void addOrder(CarOrder carOrder) {
		if (carOrder == null) throw new IllegalArgumentException("car order is null");
		this.controller.addOrderToQueue(carOrder);
	}

	/**
	 * gets all stats from the {@code Statistics} class
	 * @return all statistics from {@code Statistics}
	 */
	public AllStats getStats() {
		return this.statistics.getOrderStats();
	}

	public User getUser(String id) {
		if (!this.userDatabase.containsKey(id)) {
			return null;
		}
		return this.userDatabase.get(id);
	}
}


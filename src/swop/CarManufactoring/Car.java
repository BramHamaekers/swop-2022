package swop.CarManufactoring;

import java.util.HashSet;
import java.util.Set;
import swop.Database.RandomID;

public class Car {
	public static Set<String> tasks = Set.of("Assembly car body", "Paint car", "Insert engine", "Insert gearbox",
			"Install seats", "Install airco", "Mount wheels");
	private Set<String> uncompletedTasks = new HashSet<>();
    private CarModel carModel;
    private String uniqueID;
    
    public Car(CarModel model){
        this.carModel = model;
		uniqueID = RandomID.random(5);
		//TODO als we tasks meegeven aan constructor -> check of tasks niet null is.
		this.setUncompletedTasks(Set.copyOf(tasks));
    }

	public void completeTask(String task) {
		if (!isValidTask(task)) {
			System.out.println("Task is not valid"); //TODO Error
		}
		//TODO check if uncompletedtasks contains task??
		uncompletedTasks.remove(task);
	}

	private boolean isValidTask(String task) {
		return tasks.contains(task) && task != null;
	}

	public boolean isCompleted() {
		return getUncompletedTasks() == null;
	}
	
	public String getUniqueID() {
		return uniqueID;
	}

	public Set<String> getUncompletedTasks() {
		return uncompletedTasks;
	}

	public void setUncompletedTasks(Set<String> uncompletedTasks) {
		this.uncompletedTasks = uncompletedTasks;
	}
}

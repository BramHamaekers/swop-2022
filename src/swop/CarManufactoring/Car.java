package swop.CarManufactoring;

import java.util.HashSet;
import java.util.Set;

public class Car {
	public static Set<String> tasks = Set.of("Assembly car body", "Paint car", "Insert engine", "Insert gearbox",
			"Install seats", "Install airco", "Mount wheels");
	private Set<String> uncompletedTasks = new HashSet<>();
    private CarModel carModel;
    
    public Car(CarModel model){
        this.setCarModel(model);
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

	public Set<String> getUncompletedTasks() {
		return uncompletedTasks;
	}

	public void setUncompletedTasks(Set<String> uncompletedTasks) {
		this.uncompletedTasks = uncompletedTasks;
	}

	public CarModel getCarModel() {
		return carModel;
	}

	public void setCarModel(CarModel carModel) {
		this.carModel = carModel;
	}
}

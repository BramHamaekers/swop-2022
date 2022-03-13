package swop.CarManufactoring;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Car {
	public static Set<String> tasks = Set.of("assembly car body", "paint car", "insert engine", "insert gearbox",
			"install seats", "install airco", "mount wheels");
	private Set<String> uncompletedTasks;
    private CarModel carModel;
    
    public Car(CarModel model){
		if (model == null)
			throw new IllegalArgumentException("carmodel is null");
        this.setCarModel(model);
		this.setUncompletedTasks(Set.copyOf(tasks));
    }

	public void completeTask(String task) {
		task = task.toLowerCase();
		if (!isValidTask(task)) {
			throw new IllegalArgumentException("sets are distinct");
		}
		if (!uncompletedTasks.contains(task))
			throw new IllegalArgumentException("task not in todo list");
		uncompletedTasks.remove(task);
	}

	public boolean isValidTask(String task) {
		return task != null && tasks.contains(task.toLowerCase());
	}

	public boolean isCompleted() {
		return getUncompletedTasks().size() == 0;
	}

	public Set<String> getUncompletedTasks() {
		return uncompletedTasks;
	}

	public void setUncompletedTasks(Set<String> uncompletedTasks) {
		for (String task : uncompletedTasks){
			if (!isValidTask(task))
				throw new IllegalArgumentException("sets are distinct");
		}
		// otherwise uncompletedtasks is immutable
		this.uncompletedTasks = new HashSet<>(uncompletedTasks);
	}


	public CarModel getCarModel() {
		return carModel;
	}

	public void setCarModel(CarModel carModel) {
		this.carModel = carModel;
	}
	public String getValueOfPart(String part) {
		if (this.carModel == null) {
			System.out.println("The car has no carmodel");
			return null;
		}
		return this.getCarModel().getValueOfPart(part);
	}
	public  Map<String, String> getParts() {
		if (this.carModel == null) {
			System.out.println("The car has no carmodel");
			return null;
		}
		return this.getCarModel().getParts();
	}
}

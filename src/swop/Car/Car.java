package swop.Car;

import java.util.*;

import swop.CarManufactoring.Task;
import swop.Parts.CarOptionCategory;

public class Car {
	private Set<Task> uncompletedTasks;
    private CarModel carModel;
	private String estimatedCompletionTime; // TODO Dynamic
	private Map<String, Integer> deliveryTime; // TODO
    
    public Car(CarModel model){
        this.setCarModel(model);  
		this.initiateUncompletedTasks();
    }

	/**
	 * Complete a remaining task for assembly
	 * @param task given a completed task
	 */
	public void completeTask(Task task) {
		if (task == null)
			throw new IllegalArgumentException("task is null");
		if (!uncompletedTasks.contains(task))
			throw new IllegalArgumentException("task not in todo list");
		for(Task t: uncompletedTasks) {
			if (Objects.equals(task.getName(), t.getName())) {
				this.uncompletedTasks.remove(t);
				break;
			}
		}
	}

	/**
	 * Checks if all tasks are completed
	 * @return true if Set<Task> uncompletedTasks is empty
	 */
	public boolean isCompleted() {
		return getUncompletedTasks().isEmpty();
	}

	public Set<Task> getUncompletedTasks() {
		return Set.copyOf(uncompletedTasks);
	}

	private void initiateUncompletedTasks() {
		uncompletedTasks = Task.getAllTasks();
	}

	/**
	 * Returns the car model of this car
	 * @return carModel
	 */
	public CarModel getCarModel() {
		return carModel;
	}

	//TODO this is a placeholder function untill the different models are implemented
	public String getCarModelName() {
		return "c";
	}
	
	public void setCarModel(CarModel carModel) {
		if (carModel == null)
			throw new IllegalArgumentException("car model is null");
		this.carModel = carModel;
	}

	/**
	 * Returns the value of given part based on the model of this car.
	 * @param category todo
	 * @return value part
	 */
	public String getValueOfPart(String category) {
		if (this.carModel == null) {
			throw new IllegalArgumentException("The car has no carmodel");
		}
		return this.getCarModel().getCarModelSpecification().getValueOfPart(category);
	}
	public  Map<String, String> getPartsMap() {
		if (this.carModel == null) {
			throw new IllegalArgumentException("The car has no carmodel");
		}
		return this.getCarModel().getCarModelSpecification().getAllParts();
	}

	public void setEstimatedCompletionTime(String time) { // TODO Remove
		this.estimatedCompletionTime = time;
	}

	public String getEstimatedCompletionTime() {
		return this.estimatedCompletionTime;
	}

	public Map<String, Integer> getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(Map<String, Integer> time) {
		this.deliveryTime = time;
	}
}

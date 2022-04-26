package swop.Car;

import java.util.*;

import swop.Car.CarModel.CarModel;
import swop.CarManufactoring.Task;

public class Car {
	private Set<Task> uncompletedTasks;
	private Set<Task> allTasks;
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
	 * @throws IllegalArgumentException when task is null
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

	/**
	 * Sets all tasks that need to be done
	 */
	private void initiateUncompletedTasks() {
		this.uncompletedTasks = Task.getAllTasks(this.getCarModel().getCarModelSpecification().getAllParts());
		this.allTasks = this.getUncompletedTasks();
	}

	/**
	 * Returns this.allTasks
	 * @return all tasks from the car
	 */
	public Set<Task> getAllTasks(){
		return this.allTasks;
	}
	
	/**
	 * Returns the car model of this car
	 * @return carModel
	 */
	public CarModel getCarModel() {
		return carModel;
	}

	/**
	 * get name of carModel
	 * @return this.carModel.getName()
	 */
	public String getCarModelName() {
		return this.carModel.getName();
	}
	
	private void setCarModel(CarModel carModel) {
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
		return this.getCarModel().getCarModelSpecification().getValueOfPart(category);
	}
	public  Map<String, String> getPartsMap() {
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

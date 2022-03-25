package swop.CarManufactoring;

import java.util.*;

import swop.Parts.Part;

public class Car {
	private Set<Task> uncompletedTasks;
    private CarModel carModel;
	private String estimatedCompletionTime;
	private int completionTime;
    
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

	public void setCarModel(CarModel carModel) {
		if (carModel == null)
			throw new IllegalArgumentException("car model is null");
		this.carModel = carModel;
	}

	/**
	 * Returns the value of given part based on the model of this car.
	 * @param part
	 * @return value part
	 */
	public String getValueOfPart(Part part) {
		if (this.carModel == null) {
			throw new IllegalArgumentException("The car has no carmodel");
		}
		return this.getCarModel().getValueOfPart(part);
	}
	public  Map<String, String> getPartsMap() {
		if (this.carModel == null) {
			throw new IllegalArgumentException("The car has no carmodel");
		}
		return this.getCarModel().getPartsMap();
	}

	public void setEstimatedCompletionTime(String time) {
		this.estimatedCompletionTime = time;

	}


	public String getEstimatedCompletionTime() {
		return this.estimatedCompletionTime;
	}

	public int getCompletionTime() {
		return this.completionTime;
	}

	public void setCompletionTime(int time) {
		this.completionTime = time;
	}
}

package swop.Car;

import java.util.*;

import swop.Car.CarModel.CarModel;
import swop.CarManufactoring.Task;
import swop.Miscellaneous.TimeStamp;

public class Car {
	private Set<Task> uncompletedTasks;
	private Set<Task> allTasks;
    private CarModel carModel;
	private TimeStamp initialCompletionTime;
	private TimeStamp estimatedCompletionTime;
	private TimeStamp deliveryTime;
    
    public Car(CarModel model){
        this.setCarModel(model);
		this.initiateUncompletedTasks();
    }

	/**
	 * Complete a remaining task for assembly
	 * @param task given a completed task
	 * @throws IllegalArgumentException when task is null
	 * */
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
		return this.getUncompletedTasks().isEmpty();
	}

	/**
	 * Return a set of all Tasks of this car that still need to be completed
	 * @return copy of this.uncompletedTasks
	 */
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
		return this.carModel;
	}

	/**
	 * get name of carModel
	 * @return this.carModel.getName()
	 */
	public String getCarModelName() {
		return this.carModel.getName();
	}

	/**
	 * Set this.carModel to the given carModel
	 * @param carModel the given carModel
	 * @throws IllegalArgumentException when car is null
	 */
	private void setCarModel(CarModel carModel) {
		if (carModel == null)
			throw new IllegalArgumentException("car model is null");
		this.carModel = carModel;
	}

	/**
	 * Returns the value of given carOptionCategory based on the model of this car.
	 * @param category the given carOptionCategory
	 * @return value carOptionCategory
	 */
	public String getValueOfPart(String category) {
		return this.getCarModel().getCarModelSpecification().getValueOfPart(category);
	}
	public  Map<String, String> getPartsMap() {
		return this.getCarModel().getCarModelSpecification().getAllParts();
	}

	/**
	 * @return this.initialCompletionTime
	 */
	public TimeStamp getInitialCompletionTime() {
		return this.initialCompletionTime;
	}

	/**
	 * Set estimatedCompletionTime to new timeStamp, if there is not yet a initialCompletionTime
	 * set the initialCompletionTime to the same timeStamp.
	 * @param timeStamp the timeStamp to update the estimatedCompletionTime with
	 */
	public void setEstimatedCompletionTime(TimeStamp timeStamp) {
		if (this.initialCompletionTime == null) this.initialCompletionTime = timeStamp;
		this.estimatedCompletionTime = timeStamp;
	}

	/**
	 * get the estimated time that this car will be completed on as a TimeStamp
	 * @return this.estimatedCompletionTime
	 */
	public TimeStamp getEstimatedCompletionTime() {
		return this.estimatedCompletionTime;
	}

	/**
	 * get the time that this car was finished at
	 * @return this.deliveryTime
	 */
	public TimeStamp getCompletionTime() {
		return this.deliveryTime;
	}

	/**
	 * Set the time that this car was finished at to a new TimeStamp
	 * @param timeStamp the given timestamp
	 */
	public void setDeliveryTime(TimeStamp timeStamp) {
		this.deliveryTime = timeStamp;
	}
}

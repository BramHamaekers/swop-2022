package swop.Car;

import java.util.*;

import swop.Car.CarModel.CarModel;
import swop.CarManufactoring.Tasks.AssemblyCarBody;
import swop.Miscellaneous.TimeStamp;
import swop.CarManufactoring.Task;

/**
 * A car representation in assem assist, consisting of tasks and deliver times
 */
public class Car {
	private Set<Task> tasks;
    private CarModel carModel;
	private TimeStamp initialCompletionTime;
	private TimeStamp estimatedCompletionTime;
	private TimeStamp deliveryTime;

	/**
	 * initializes a car with a {@code CarModel}
	 * @param model a selected carModel
	 */
	public Car(CarModel model){
        this.setCarModel(model);
		this.initiateTasks();
    }

	/**
	 * Checks if all tasks are completed
	 * @return true if all tasks in this.task are completed
	 */
	public boolean isCompleted() {
		return this.tasks.stream().allMatch(Task::isComplete);
	}

	/**
	 * Return a set of all Tasks of this car that are completed
	 * @return Set of all completed tasks in this.tasks
	 */
	public Set<Task> getCompletedTasks() {
		Set<Task> completedTasks = new HashSet<>();
		this.tasks.stream().filter(Task::isComplete)
				.forEach(completedTasks::add);
		return completedTasks;
	}

	/**
	 * Return a set of all Tasks of this car that still need to be completed
	 * @return Set of all uncompleted tasks in this.tasks
	 */
	public Set<Task> getUncompletedTasks() {
		Set<Task> uncompletedTasks = new HashSet<>();
		this.tasks.stream().filter(t -> !t.isComplete())
							.forEach(uncompletedTasks::add);
		return uncompletedTasks;
	}

	/**
	 * Sets all tasks that need to be done
	 */
	private void initiateTasks() {
		Map<String, String> parts = this.getCarModel().getCarModelSpecification().getAllParts();
		for (String part: parts.keySet()) {
			this.tasks.add(createTask(part, parts.get(part)));
		}
	}

	private Task createTask(String part, String option) {
		return switch (part) {
			case "Body" -> new AssemblyCarBody(option);
		};
	}

	/**
	 * Returns this.tasks
	 * @return all tasks from the car
	 */
	public Set<Task> getTasks(){
		return this.tasks;
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

	/**
	 * @return returns a map of all the categories with their selected specification
	 */
	public Map<String, String> getPartsMap() {
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

package swop.Car;

import java.util.*;
import java.util.stream.Collectors;

import swop.Car.CarModel.CarModel;
import swop.CarManufactoring.Tasks.*;
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
		if (model == null)
			throw new IllegalArgumentException("model is null");
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
		return this.tasks.stream().filter(Task::isComplete).collect(Collectors.toSet());
	}

	/**
	 * Return a set of all Tasks of this car that still need to be completed
	 * @return Set of all uncompleted tasks in this.tasks
	 */
	public Set<Task> getUncompletedTasks() {
		return this.tasks.stream().filter(t -> !t.isComplete()).collect(Collectors.toSet());
	}

	/**
	 * Sets all tasks that need to be done for a specific car model
	 */
	private void initiateTasks() {
		Map<String, String> parts = this.getCarModel().getCarModelSpecification().getAllParts();
		if(parts == null) throw new IllegalArgumentException("getAllParts returns null");
		this.tasks = new HashSet<>();
		parts.forEach((key, value) -> this.tasks.add(createTask(key, value)));
	}

	/**
	 * Makes a new task for a specified part and the selected option for that part
	 * @param part the category for the part e.g. "Body"
	 * @param option the selected option for the part by the garageholder
	 * @return a new task
	 */
	private Task createTask(String part, String option) {
		if (part == null)
			throw new IllegalArgumentException("Null string provided");
		if (option == null)
			throw new IllegalArgumentException("Null string provided");
		return switch (part) {
			case "Body" -> new AssemblyCarBody(option);
			case "Color" -> new PaintCar(option);
			case "Engine" -> new InsertEngine(option);
			case "Gearbox" -> new InstallGearbox(option);
			case "Seats" -> new InstallSeats(option);
			case "Airco" -> new InstallAirco(option);
			case "Wheels" -> new MountWheels(option);
			case "Spoiler" -> new InstallSpoiler(option);
			default -> throw new IllegalArgumentException("part is not valid");
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
	 * get name of the carModel
	 * @return this.carModel.getName()
	 */
	public String getCarModelName() {
		return this.carModel.getName();
	}

	/**
	 * Specify the carModel for this car
	 * @param carModel the specified carModel
	 */
	private void setCarModel(CarModel carModel) {
		if (carModel == null)
			throw new IllegalArgumentException("car model is null");
		this.carModel = carModel;
	}

	/**
	 * Returns the selected part for a category in the carmodelspecification
	 * @param category the given carOptionCategory
	 * @return value carOptionCategory
	 */
	public String getSelectionForPart(String category) {
		if (category == null)
			throw new IllegalArgumentException("Null string provided");
		return this.carModel.getCarModelSpecification().getSelectionForPart(category);
	}

	/**
	 * Get a map of all the categories with their selected specification
	 * @return a map of all the categories with their selected specification
	 */
	public Map<String, String> getPartsMap() {
		return this.carModel.getCarModelSpecification().getAllParts();
	}

	/**
	 * Get the initial completion time calculated when the car was first ordered
	 * @return  the initial completion time for this car
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
		if (timeStamp == null)
			throw new IllegalArgumentException("not a valid timestamp specified");
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
	 * @return the delivery time of a finished car, null if it was not finished
	 */
	public TimeStamp getCompletionTime() {
		return this.deliveryTime;
	}

	/**
	 * Set the time that this car was finished at to a new TimeStamp
	 * @param timeStamp the given timestamp
	 */
	public void setDeliveryTime(TimeStamp timeStamp) {
		if (timeStamp == null)
			throw new IllegalArgumentException("not a valid timestamp specified");
		this.deliveryTime = timeStamp;
	}
}

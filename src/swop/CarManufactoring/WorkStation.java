package swop.CarManufactoring;

import java.util.*;

import swop.Car.Car;
import swop.Listeners.TaskCompletedListener;

public class WorkStation {
	private final String name;
	private Car car;
	private int currentWorkingTime = 0;
	private final List<TaskCompletedListener> listeners = new ArrayList<>();

	public WorkStation(String name) {
		if (!isValidName(name)) {
			throw new IllegalArgumentException("Not a valid work station name"); 
		}
		this.name = name;
		for(Task t:this.getTasks()) t.setWorkStation(this);

	}

	/**
	 * Add a new listener to the list of listeners
	 * @param listener the listener to add
	 */
	public void addListener(TaskCompletedListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * all listeners getting triggered and execute taskCompleted()
	 */
	public void triggerListenersTaskCompletion() {
		for (TaskCompletedListener l:this.listeners) l.taskCompleted();
	}

	/**
	 * Returns a set of uncompleted tasks
	 * @return Set<Task>
	 */
	public List<Task> getUncompletedTasks() {
		if(this.getCar() == null) {
			return null;
		}
		List<Task> tasks = this.getTasks();
		tasks.retainAll(this.getCar().getUncompletedTasks()); 
		return tasks;
	}

	/**
	 * Returns a set of completed tasks
	 * @return Set<Task>
	 */
	public List<Task> getCompletedTasks() {
		if(this.getCar() == null) {
			return null;
		}
		List<Task> tasksOfWorkstation = this.getTasks();
		List<Task> allTasksOfCar = new LinkedList<>(this.getCar().getAllTasks());
		tasksOfWorkstation.retainAll(allTasksOfCar);
		tasksOfWorkstation.removeAll(this.getCar().getUncompletedTasks());
		return tasksOfWorkstation;
	}

	/**
	 * checks if a name is valid to be a name for workstation
	 */
	private boolean isValidName(String name) {
		return (name.equals("Car Body Post")) ||
				(name.equals("Drivetrain Post")) || (name.equals("Accessories Post"));
	}

	/**
	 * returns the tasks that are part of this workstation
	 * @return tasks of workstation
	 */
	public List<Task> getTasks() {
		return switch (this.getName()) {
			case "Car Body Post" -> new LinkedList<>(Arrays.asList(Task.AssemblyCarBody, Task.PaintCar));
			case "Drivetrain Post" -> new LinkedList<>(Arrays.asList(Task.InsertEngine, Task.InstallGearbox));
			case "Accessories Post" -> new LinkedList<>(Arrays.asList(Task.InstallSeats, Task.InstallAirco, Task.MountWheels, Task.InstallSpoiler));
			default -> throw new IllegalArgumentException("No tasks could be given: Invalid name Workstation");
		};
	}

	/**
	 * returns name of workstation
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns current car in workstation
	 */
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.currentWorkingTime = 0;
		this.car = car;
	}
	
	/**
	 * Returns how long a car is currently in the workstation
	 * @return this.currentWorkingTime
	 */
	public int getCurrentWorkingTime() {
		return this.currentWorkingTime;
	}
	
	/**
	 * Checks if the part is chosen off the current car in workstation. (defined in the chosenOptions)
	 */
	public boolean isPartOfCurrentCarInWorkStation(String part) {
		Car car = this.getCar();
		if(car == null) return false;
		return car.getCarModel().getCarModelSpecification().isPartInChosenOptions(part);
	}
	
	/**
	 * Tries to get value of a carOptionCategory
	 * @param category the given carOptionCategory to get the value of
	 * @throws IllegalArgumentException if car == null || part == null
	 */
	public String getValueOfPart(String category) {
		if(this.car == null) throw new IllegalArgumentException("No car in station");
		if (category == null) throw new IllegalArgumentException("part is null");
		return this.getCar().getValueOfPart(category);

	}
	
	/**
	 * Tries to complete a task 
	 * @param task to complete
	 * @param time Time it took to complete the task
	 * @throws IllegalArgumentException if car == null || task == null
	 */
	public void completeTask(Task task, int time) {
		if(car == null) throw new IllegalArgumentException("No car in station");
		if (task == null) throw new IllegalArgumentException("task is null");
		this.getCar().completeTask(task);
		this.currentWorkingTime += time;
		this.triggerListenersTaskCompletion();
	}

	/**
	 * You can check if all tasks are completed of current work station.
	 * @return true if there is no car or all tasks are completed
	 */
	public boolean stationTasksCompleted() {
		return this.getCar() == null || Collections.disjoint(this.getCar().getUncompletedTasks(),
				this.getTasks()); //returns true if no tasks are uncompleted
	}
	
}

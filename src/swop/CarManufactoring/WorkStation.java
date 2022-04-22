package swop.CarManufactoring;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import swop.Car.Car;
import swop.Parts.CarOptionCategory;

public class WorkStation {
	private final String name;
	private Car car;
	private int currentWorkingTime = 0;

	public WorkStation(String name) {
		if (!isValidName(name)) {
			throw new IllegalArgumentException("Not a valid work station name"); 
		}
		this.name = name;
		for(Task t:this.getTasks()) t.setWorkStation(this);

	}

	/**
	 * checks if workstation contains this task
	 * @param task the task to check
	 * @return true if contains(task)
	 */
	public boolean containsTask(Task task) {
		if (task == null) throw new IllegalArgumentException("task is null");
		return this.getTasks().contains(task);
	}

	/**
	 * Returns a set of uncompleted tasks
	 * @return Set<Task>
	 */
	public Set<Task> getUncompletedTasks() {
		if(this.getCar() == null) {
			return null;
		}
		Set<Task> tasks = this.getTasks();
		tasks.retainAll(this.getCar().getUncompletedTasks()); 
		return tasks;
	}

	/**
	 * Returns a set of completed tasks
	 * @return Set<Task>
	 */
	public Set<Task> getCompletedTasks() {
		if(this.getCar() == null) {
			return null;
		}
		Set<Task> tasksOfWorkstation = this.getTasks();
		Set<Task> alltasksOfCar = this.getCar().getAllTasks();
		tasksOfWorkstation.retainAll(alltasksOfCar);
		//TODO filter tasks based on car in workstation
		tasksOfWorkstation.removeAll(this.getCar().getUncompletedTasks());
		return tasksOfWorkstation;
	}

	/**
	 * checks if a name is valid to be a name for workstation
	 */
	private boolean isValidName(String name) {
		return (Objects.equals(name, "Car Body Post")) ||
				(Objects.equals(name, "Drivetrain Post")) || (Objects.equals(name, "Accessories Post"));
	}

	/**
	 * returns the tasks that are part of this workstation
	 * @return tasks of workstation
	 */
	public Set<Task> getTasks() {
		return switch (this.getName()) {
			case "Car Body Post" -> new LinkedHashSet<>(Arrays.asList(Task.AssemblyCarBody, Task.PaintCar));
			case "Drivetrain Post" -> new LinkedHashSet<>(Arrays.asList(Task.InsertEngine, Task.InstallGearbox));
			case "Accessories Post" -> new LinkedHashSet<>(Arrays.asList(Task.InstallSeats, Task.InstallAirco, Task.MountWheels, Task.InstallSpoiler));
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
	 * @return
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
		return car.getCarModel().getCarModelSpecification().isPartinChosenOptions(part);
	}
	
	/**
	 * Tries to get value of a part
	 * @param category
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
	 * @param time 
	 * @throws IllegalArgumentException if car == null || task == null
	 */
	public void completeTask(Task task, int time) {
		if(car == null) throw new IllegalArgumentException("No car in station");
		if (task == null) throw new IllegalArgumentException("task is null");
		this.getCar().completeTask(task);
		this.currentWorkingTime += time;
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

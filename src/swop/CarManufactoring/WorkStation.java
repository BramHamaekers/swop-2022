package swop.CarManufactoring;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import swop.Parts.Part;

public class WorkStation {
	private final String name;
	private Car car;

	public WorkStation(String name) {
		if (!isValidName(name)) {
			throw new IllegalArgumentException("Not a valid work station name"); 
		}
		this.name = name;

	}

	public boolean containsTask(Task task) {
		if (task == null) throw new IllegalArgumentException("task is null");
		return this.getTasks().contains(task);
	}

	public Set<Task> getUncompletedTasks() {
		if(this.getCar() == null) {
			System.out.println("There are no Tasks (No car in work station)");
			return null;
		}
		Set<Task> tasks = this.getTasks();
		tasks.retainAll(this.getCar().getUncompletedTasks()); 
		return tasks;
	}

	private boolean isValidName(String name) {
		return (Objects.equals(name, "Car Body Post")) ||
				(Objects.equals(name, "Drivetrain Post")) || (Objects.equals(name, "Accessories Post"));
	}

	public Set<Task> getTasks() {
		return switch (this.getName()) {
			case "Car Body Post" -> new LinkedHashSet<>(Arrays.asList(Task.AssemblyCarBody, Task.PaintCar));
			case "Drivetrain Post" -> new LinkedHashSet<>(Arrays.asList(Task.InsertEngine, Task.InstallGearbox));
			case "Accessories Post" -> new LinkedHashSet<>(Arrays.asList(Task.InstallSeats, Task.InstallAirco, Task.MountWheels));
			default -> null;
		};
	}

	public String getName() {
		return name;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	public String getValueOfPart(Part part) {
		if(car == null) throw new IllegalArgumentException("No car in station");
		if (part == null) throw new IllegalArgumentException("part is null");
		return this.getCar().getValueOfPart(part);

	}
	public void completeTask(Task task) {
		if(car == null) throw new IllegalArgumentException("No car in station");
		if (task == null) throw new IllegalArgumentException("task is null");
		this.getCar().completeTask(task);
	}
}

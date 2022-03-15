package swop.CarManufactoring;

import swop.Exceptions.NotAllTasksCompleteException;
import swop.Parts.Part;

import java.util.*;

public class AssemblyLine {


	private LinkedList<Car> carQueue; // Queue of cars that still have to be assembled but are not yet on the assembly line
	private final LinkedList<WorkStation> workStations;
	private Scheduler scheduler;


	public AssemblyLine() {
		this.setCarQueue(new LinkedList<>());
		this.workStations = createWorkStations();
		this.scheduler = new Scheduler(this);
	}

	/**
	 * Function creates all the workstations that are part of the assemblyLine as a linked list so that they have the
	 * right order.
	 * @return LinkedList<WorkStation> of all the workstations of this assemblyLine
	 */
	private LinkedList<WorkStation> createWorkStations () {
		LinkedList<WorkStation> workStations = new LinkedList<>();
		workStations.add(new WorkStation("Car Body Post"));
		workStations.add(new WorkStation("Drivetrain Post"));
		workStations.add(new WorkStation("Accessories Post"));
		return workStations;
	}

	/**
	 * Add a CarOrder to the First-Come-First-Serve carQueue
	 * @param carOrder the carOrder to add to this.carQueue
	 */
	public String addToAssembly(CarOrder carOrder) {
		this.getCarQueue().add(carOrder.getCar());
		this.scheduler.getCompletionTime();

		return null;
	}

	public void advanceAssemblyLine() throws NotAllTasksCompleteException {
		// check if possible to advance AssemblyLine
		checkAdvance();
		// Move all cars on assembly by 1 position
		for (int i = this.workStations.size() - 1; i > 0; i--)
			this.workStations.get(i).setCar(this.workStations.get(i-1).getCar());

		// Set first workstation to first element from the queue
		try {this.workStations.getFirst().setCar(this.getCarQueue().removeFirst());}
		catch (NoSuchElementException e) {this.workStations.getFirst().setCar(null);}
	}

	private void checkAdvance() throws NotAllTasksCompleteException {
		for (WorkStation workStation: this.workStations)
			if (!allTasksCompleted(workStation))
				throw new NotAllTasksCompleteException("Not all tasks completed in: ", workStation.getName());
	}

	private boolean allTasksCompleted(WorkStation workStation) {
		return workStation.getCar() == null || Collections.disjoint(workStation.getCar().getUncompletedTasks(),
				workStation.getTasks()); //returns true if no tasks are uncompleted
	}
	
	/////////////////////////// Functions used 2 get data for manager use case //////////////////////////////

	public List<String> getCurrentStatus() {
		List<String> status = new LinkedList<>();
		this.workStations.forEach(w -> {
			String s = w.getName();
			if(w.getCar() == null) s = s.concat(": EMPTY");
			else {
				s = s.concat(": " + w.getCar().getCarModel().getPartsMap()); //TODO cars msschien toch ID geven?
				s = this.allTasksCompleted(w) ? s.concat(" (FINISHED)") : s.concat(" (PENDING)");
			}
			status.add(s);
		});
		return status;
	}

	public List<String> getAdvancedStatus() { //// Kan ook met list werken, geen idee waarom ik array kies.
		List<String> status = new LinkedList<>();
		for(int i = 0; i < workStations.size(); i++){
			WorkStation w = this.workStations.get(i);
			String s = w.getName();
			if(0<i) {
				w = this.workStations.get(i-1);
				s = w.getCar() == null ? s.concat(": EMPTY") :
						s.concat(": " + w.getCar().getCarModel().getPartsMap() + " (PENDING)");
			}
			else {
				s = this.getCarQueue().isEmpty() ? s.concat(": EMPTY") :
						s.concat(": " + getCarQueue().get(0).getCarModel().getPartsMap() + " (PENDING)");
			}
			status.add(s);
		}
		return status;
	}

	/////////////////////////////// Functions used Car Mechanic use case ////////////////////////////////

	/**
	 * returns list of strings with names of all workstations.
	 * @return
	 */
	public List<String> getWorkstations(){
		List<String> names = new LinkedList<>();
		for(WorkStation station: this.workStations) {
			names.add(station.getName());
		}
		return names;
	}

	private WorkStation getWorkStation(String station) {
		for(WorkStation wStation: this.workStations) {
			if(wStation.getName().equals(station)) return wStation;
		}
		throw new IllegalArgumentException("Not a valid workstation name"); 

	}
	
	private WorkStation getWorkStation(Task task) {
		for(WorkStation wStation: this.workStations) {
			if(wStation.containsTask(task)) return wStation;
		}
		throw new IllegalArgumentException("No workstation preforms this task"); 

	}


	public Set<Task> getAvailableTasks(String station) {
			WorkStation workStation = this.getWorkStation(station);
			return workStation.getUncompletedTasks();
	}
	
	public void completeTask(Task task) {
		WorkStation station = this.getWorkStation(task);
		station.completeTask(task);
		
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////:

	public LinkedList<Car> getCarQueue() {
		return carQueue;
	}

	public void setCarQueue(LinkedList<Car> carQueue) {
		this.carQueue = carQueue;
	}

	public String getTaskDescription(Task task) {
		WorkStation ws = this.getWorkStation(task);
		List<Part> parts = task.getParts();
		String value = "Empty";
		for(Part p : parts) {
			value = task.getDescription(p) + ws.getValueOfPart(p); //als er meerdere parts bij een task horen geef je maar een array van strings terug
		}
		return value;
		
	}
}

class WorkStation {
	private final String name;
	private Car car;

	public WorkStation(String name) {
		if (!isValidName(name)) {
			throw new IllegalArgumentException("Not a valid work station name"); 
		}
		this.name = name;

	}

	public boolean containsTask(Task task) {
		return this.getTasks().contains(task);
	}

	public Set<Task> getUncompletedTasks() {
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
			case "Car Body Post" -> new HashSet<>(Arrays.asList(Task.AssemblyCarBody, Task.PaintCar));
			case "Drivetrain Post" -> new HashSet<>(Arrays.asList(Task.InsertEngine, Task.InstallGearbox));
			case "Accessories Post" -> new HashSet<>(Arrays.asList(Task.InstallSeats, Task.InstallAirco, Task.MountWheels));
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
		return this.getCar().getValueOfPart(part);

	}
	public void completeTask(Task task) {
		if(car == null) throw new IllegalArgumentException("No car in station"); 
		this.getCar().completeTask(task);
	}
}

class Scheduler {

	private final AssemblyLine assemblyLine;
	private int overTime; // Amount of hours of overtime the day before
	private int completedCars; // Amount of cars completed the day before

	public Scheduler(AssemblyLine assemblyLine) {
		this.assemblyLine = assemblyLine;
		this.overTime = 0;
		this.completedCars = 0;
	}

	public String getCompletionTime() {
		// 0 = day 1 , hour 0 = 06:00
		// 1 = day 1, hour 1 = 07:00
		// 16 = day 1, hour 16 = 22:00
		int day = 0;
		int hour = 3;
		for (int i = 0; i < this.assemblyLine.getCarQueue().size()-1; i++) {
			hour += 1;
			if (hour > 16 - this.overTime + 2) {
				day += 1;
				hour = 3;
				this.overTime = 2;
			}
		}
		int n = this.assemblyLine.getCarQueue().size();
		/**
		int day = (3 + (n-1)) / 16;
		int hours = (3 + (n-1)) % 16;
		if (hours == 0) {
			day -=1;
			hours = 16 - this.overTime;
		}
		if (day != 0) hours += 2;
		 */


		System.out.printf("%n car: %s, day: %s, time: %s%n",n, day, hour);

		// 0 = day 1 , hour 0 = 06:00
		// 1 = day 1, hour 1 = 07:00
		// 16 = day 1, hour 16 = 22:00
		// 17 = day 2, hour 1 = 7:00

		// processing car takes 3 hours
		// fist car added to conveyor will take 3 hours
		// second 4
		// third 5
		// 4 -> 6
		// ..
		// 14 -> 16 = 22:00
		// 15 -> 17 = 23:00 OT
		// 16 -> 18 = 24:00 OT
		return null;
	}
}


package swop.CarManufactoring;

import swop.Exceptions.NotAllTasksCompleteException;
import swop.Parts.Part;

import java.util.*;

public class AssemblyLine {


	private LinkedList<Car> carQueue; // Queue of cars that still have to be assembled but are not yet on the assembly line
	private final LinkedList<WorkStation> workStations;
	private final Scheduler scheduler;


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
	public void addToAssembly(CarOrder carOrder) {
		if (carOrder== null) throw new IllegalArgumentException("carOrder is null");
		this.getCarQueue().add(carOrder.getCar());
		carOrder.getCar().setEstimatedCompletionTime(this.scheduler.getEstimatedCompletionTime());
	}

	/**
	 * advance the assembly line if a manager orders and all tasks are done
	 * @param minutes minutes past since start of the task
	 * @throws NotAllTasksCompleteException thrown when there are still tasks to do
	 */
	public void advanceAssemblyLine(int minutes) throws NotAllTasksCompleteException {
		// check if possible to advance AssemblyLine
		checkAdvance();
		this.scheduler.addTime(minutes);
		// Move all cars on assembly by 1 position
		for (int i = this.workStations.size() - 1; i > 0; i--) {
			WorkStation workStation = this.workStations.get(i);
			// Check if completed
			if (workStation.getCar() != null && workStation.getCar().isCompleted() )
				workStation.getCar().setCompletionTime(this.scheduler.getMinutesPast());

			this.workStations.get(i).setCar(this.workStations.get(i-1).getCar());


		}
			

		// Set first workstation to first element from the queue
		try {this.workStations.getFirst().setCar(this.getCarQueue().removeFirst());}
		catch (NoSuchElementException e) {this.workStations.getFirst().setCar(null);}
	}

	/**
	 * Checks if it is possible to advance the assembly line
	 * @throws NotAllTasksCompleteException thrown if there are still tasks to do
	 */
	private void checkAdvance() throws NotAllTasksCompleteException {
		LinkedList<String> w = new LinkedList<>();
		for (WorkStation workStation: this.workStations)
			if (!allTasksCompleted(workStation)) w.add(workStation.getName());
		
		if(!w.isEmpty())throw new NotAllTasksCompleteException("Not all tasks completed in: ", w);
	}

	/**
	 * Returns boolean whether or not all tasks are completed in given work station
	 * @param workStation a specified workstation
	 * @return whether all tasks are completed at given workstation
	 */
	private boolean allTasksCompleted(WorkStation workStation) {
		if (workStation== null) throw new IllegalArgumentException("workStation is null");
		return workStation.getCar() == null || Collections.disjoint(workStation.getCar().getUncompletedTasks(),
				workStation.getTasks()); //returns true if no tasks are uncompleted
	}
	
	/////////////////////////// Functions used 2 get data for manager use case //////////////////////////////
	
	
	/**
	 * returns for all works stations current state. 
	 * Empty = no car, Finished = all tasks completed, Pending = tasks need 2 be completed
	 * @return a list completion statuses
	 */
	public List<String> getCurrentStatus() {
		List<String> status = new LinkedList<>();
		this.workStations.forEach(w -> {
			String s = w.getName();
			if(w.getCar() == null) s = s.concat(": EMPTY");
			else {
				s = s.concat(": " + w.getCar().getCarModel().getPartsMap());
				s = this.allTasksCompleted(w) ? s.concat(" (FINISHED)") : s.concat(" (PENDING)");
			}
			status.add(s);
		});
		return status;
	}

	/**
	 * returns for all works stations state if an advance would happen. 
	 * Empty = no car, Finished = all tasks completed, Pending = tasks need 2 be completed
	 * @return a list completion statuses if the assembly line is to advance
	 */
	public List<String> getAdvancedStatus() { 
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
	 * returns list with all workstations
	 * @return this.workStations
	 */
	public List<WorkStation> getWorkStations() {
		return this.workStations;
	}


	/**
	 * returns list of strings with names of all workstations.
	 * @return workStations
	 */
	public List<String> getWorkstationNames(){
		List<String> workStations = new LinkedList<>();
		for(WorkStation station: this.workStations) {
			workStations.add(station.getName());
		}
		return workStations;
	}

	/**
	 * Get workstation with the given name
	 * @param station the name of the workstation
	 * @return the station out of possible stations
	 */
	private WorkStation getWorkStation(String station) {
		if (station== null) throw new IllegalArgumentException("station is null");
		for(WorkStation wStation: this.workStations) {
			if(wStation.getName().equals(station)) return wStation;
		}
		throw new IllegalArgumentException("Not a valid workstation name"); 

	}

	/**
	 * Get workstation that performs the given task
	 * @param task The task of the workstation
	 * @return workstation that performs the given task
	 */
	private WorkStation getWorkStation(Task task) {
		if (task == null) throw new IllegalArgumentException("task is null");
		for(WorkStation wStation: this.workStations) {
			if(wStation.containsTask(task)) return wStation;
		}
		throw new IllegalArgumentException("No workstation preforms this task"); 

	}

	/**
	 * Gets all uncompleted tasks of the car at the given workstation
	 * @param station the name "string" of the given workstation
	 * @return all tasks that are uncompleted at station
	 */
	public Set<Task> getUncompletedTasks(String station) {
		if (station== null) throw new IllegalArgumentException("station is null");
		WorkStation workStation = this.getWorkStation(station);
		return workStation.getUncompletedTasks();
	}

	/**
	 * Completes the given task at the associated workstation
	 * @param task the task to complete
	 */
	public void completeTask(Task task) {
		if (task == null) throw new IllegalArgumentException("task is null");
		WorkStation station = this.getWorkStation(task);
		station.completeTask(task);
		
	}


	/**
	 * Returns CarQueue
	 */
	public LinkedList<Car> getCarQueue() {
		return this.carQueue;
	}

	/**
	 * Set this.carQueue to a new LinkedList<Car>
	 * @param carQueue the new LinkedList<Car>
	 */
	public void setCarQueue(LinkedList<Car> carQueue) {
		this.carQueue = carQueue;
	}

	/**
	 * Get the description of the given task
	 * @param task the given task
	 * @return The description of the given Task
	 */
	public String getTaskDescription(Task task) {
		if (task == null) throw new IllegalArgumentException("task is null");
		WorkStation ws = this.getWorkStation(task);
		List<Part> parts = task.getParts();
		String value = "Empty";
		for(Part p : parts) {
			value = task.getDescription(p) + ws.getValueOfPart(p); //als er meerdere parts bij een task horen geef je maar een array van strings terug
		}
		return value;
		
	}

	/**
	 * Add time to the minutesPast of the scheduler
	 * @param minutes minutes to add to the minutesPast
	 */
	public void advanceAssemblyTime(int minutes) {
		this.scheduler.addTime(minutes);
	}
}

/////////////////////////////////////////////////// WORKSTATION CLASS //////////////////////////////////////////////////

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

/////////////////////////////////////////////////// SCHEDULAR CLASS /////////////////////////////////////////////////////

class Scheduler {

	private final AssemblyLine assemblyLine;
	private int minutesPast;

	public Scheduler(AssemblyLine assemblyLine) {
		this.assemblyLine = assemblyLine;
		this.minutesPast = 0;
	}

	/**
	 * Calculates the estimated completion time based on the CarQueue and overtime done on previous days
	 * @return Time formatted as string
	 */
	public String getEstimatedCompletionTime() {

		int hoursPast = (getMinutesPast() / 60);
		hoursPast += (getMinutesPast() % 60 != 0) ? 1 : 0;

		int overTime = 0;
		int day = 0;
		int hour = 3 + hoursPast % 16; //TODO: Assumes no overtime is made on previous days

		for (int i = 0; i < this.assemblyLine.getCarQueue().size()-1; i++) {
			hour += 1;
			if (hour > 16 - overTime + 2) {
				day += 1;
				hour = 3;
				overTime = 2;
			}
		}
		hour += 6;
		return String.format("day: %s, time: %s:00%n", day, hour);

	}

	/**
	 * Add time in minutes to this.minutes
	 * @param minutes Minutes to add to this.minutes
	 */
	public void addTime(int minutes) {
		this.minutesPast = this.getMinutesPast() + minutes;
	}

	public int getMinutesPast() {
		return minutesPast;
	}
}


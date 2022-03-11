package swop.CarManufactoring;

import swop.Exceptions.NotAllTasksCompleteException;

import java.util.*;

public class AssemblyLine {


	private LinkedList<Car> carQueue; // Queue of cars that still have to be assembled but are not yet on the assembly line
	private final LinkedList<WorkStation> workStations;


	public AssemblyLine() {
		this.carQueue = new LinkedList<>();
		this.workStations = createWorkStations();

	}

	private LinkedList<WorkStation> createWorkStations () {
		LinkedList<WorkStation> workStations = new LinkedList<>();
		workStations.add(new WorkStation("Car Body Post"));
		workStations.add(new WorkStation("Drivetrain Post"));
		workStations.add(new WorkStation("Accessories Post"));
		return workStations;
	}

	public void addToAssembly(CarOrder carOrder) {
		this.carQueue.add(carOrder.getCar());
		System.out.println(carQueue.get(0).getCarModel().getParts());
	}

	public void advanceAssemblyLine() throws NotAllTasksCompleteException {
		// check if possible to advance AssemblyLine
		checkAdvance();
		// Move all cars on assembly by 1 position
		for (int i = this.workStations.size() - 1; i > 0; i--)
			this.workStations.get(i).setCar(this.workStations.get(i-1).getCar());

		// Set first workstation to first element from the queue
		try {this.workStations.getFirst().setCar(this.carQueue.removeFirst());}
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
	
	public String[] getCurrentStatus() {
		List<String> status = new LinkedList<String>();
		this.workStations.forEach(w -> {
			String s;
			s = w.getName();
			if(w.getCar() == null) s = s.concat(": EMPTY");
			else {
				s = s.concat(": " + w.getCar().toString()); //TODO cars msschien toch ID geven?
				if(this.allTasksCompleted(w)) s = s.concat(" (FINISHED)");
				else s = s.concat(" (PENDING)");
			}
			status.add(s);
		});
		String[] array = new String[status.size()];
		return status.toArray(array);
	}
	
	public String[] getAdvancedStatus() { //// Kan ook met list werken, geen idee waarom ik array kies.
		List<String> status = new LinkedList<String>();
		for(int i = 0; i < workStations.size(); i++){
			String s;
			WorkStation w = this.workStations.get(i);
			s = w.getName();
			if(0<i) {
				w = this.workStations.get(i-1);
				if(w.getCar() == null) s = s.concat(": EMPTY");
				else s = s.concat(": " + w.getCar().toString() + " (PENDING)");
			}
			else {
				if(this.carQueue.isEmpty()) s = s.concat(": EMPTY");
				else s = s.concat(": " + carQueue.get(0).toString() + " (PENDING)");
			}
			
			status.add(s);
		}
		String[] array = new String[status.size()];
		return status.toArray(array);
	}
	
	/////////////////////////////// Functions used Car Mechanic use case ////////////////////////////////
	
	public List<String> getWorkstations(){ 
		List<String> names = new LinkedList<String>();
		for(WorkStation station: this.workStations) {
			names.add(station.getName());
		}
		return names;
	}
	
	private WorkStation getWorkStation(String station) {
		for(WorkStation wStation: this.workStations) {
			if(wStation.getName() == station) return wStation;
		}
		
		return null;
		
	}
	
	public Map<String,Set<String>> getStationAndTasks(){ //niet gebruikt op het moment
		Map<String,Set<String>> map = new HashMap<String,Set<String>>();
		for (WorkStation station :workStations) {
			map.put(station.getName(), station.getTasks());
		}
		return map;
	}
	public Set<String> getAvailableTasks(String station) {
		WorkStation wStation = this.getWorkStation(station);
		Set<String> tasks = wStation.getTasks();
		Car car = wStation.getCar();
		if(car == null) return new HashSet<>(Arrays.asList("No tasks need completion (no car in station)"));
		else {
			Set<String> carTasks = car.getUncompletedTasks();
			tasks.retainAll(carTasks);
			return tasks;
		}	
	}
	
	public String getTaskInfo(String task) {
		TaskInfo info = TaskInfo.getTaksInfo(task);
		String part = info.getPartOfTask();
		WorkStation station = this.getWorkStation(info.getWorkStation());
		if(station.getCar() == null) return null;
		String partValue = station.getCar().getCarModel().getValueOfPart(part);
		return info.getDescription() + partValue;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////:	
	

	// TEST FUNCTION
	public void printAssembly() {
		System.out.println("queue:");
		this.carQueue.forEach(c -> System.out.print(c.getCarModel().getParts() + ", "));
		this.workStations.forEach(w -> {
			System.out.println(w.getName() + ":");
			try {System.out.println(w.getCar().getCarModel().getParts());}
			catch (Exception e){System.out.println("null");}

		});
	}
}

class WorkStation {
	private final String name;
	private Car car;

	public WorkStation(String name) {
		if (!isValidName(name)) {
			System.out.println("Not a valid workstation"); //TODO should throw error
		}
		this.name = name;

	}

	private boolean isValidName(String name) {
		return (Objects.equals(name, "Car Body Post")) ||
				(Objects.equals(name, "Drivetrain Post")) || (Objects.equals(name, "Accessories Post"));
	}

	public Set<String> getTasks() {
		return switch (this.getName()) {
			case "Car Body Post" -> new HashSet<>(Arrays.asList("Assembly car body", "Paint car"));
			case "Drivetrain Post" -> new HashSet<>(Arrays.asList("Insert engine", "Insert gearbox"));
			case "Accessories Post" -> new HashSet<>(Arrays.asList("Install seats", "Install airco", "Mount wheels"));
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
}


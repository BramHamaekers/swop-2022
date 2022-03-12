package swop.CarManufactoring;

import swop.Exceptions.NotAllTasksCompleteException;

import java.util.*;

public class AssemblyLine {


	private LinkedList<Car> carQueue; // Queue of cars that still have to be assembled but are not yet on the assembly line
	private final LinkedList<WorkStation> workStations;


	public AssemblyLine() {
		this.carQueue = new LinkedList<>();
		this.workStations = createWorkStations();

		////////////////////// for testing car mechanic ////////////////////////////
		Map<String, String> options = Map.of("body", "sedan", "color", "red", "engine", "standard 2l 4 cilinders",
	            "gearBox", "6 speed manual", "seats", "leather black", "airco", "manual", "wheels", "comfort");
	    CarModel model = new CarModel(options);
	    CarOrder order = new CarOrder(model);
	    this.addToAssembly(order);
	    try {
			this.advanceAssemblyLine();
		} catch (NotAllTasksCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    /////////////////////////////////////////////////////////////////////
	    
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
		List<String> status = new LinkedList<>();
		this.workStations.forEach(w -> {
			String s = w.getName();
			if(w.getCar() == null) s = s.concat(": EMPTY");
			else {
				s = s.concat(": " + w.getCar().getCarModel().getParts()); //TODO cars msschien toch ID geven?
				s = this.allTasksCompleted(w) ? s.concat(" (FINISHED)") : s.concat(" (PENDING)");
			}
			status.add(s);
		});
		return status.toArray(new String[status.size()]);
	}

	public String[] getAdvancedStatus() { //// Kan ook met list werken, geen idee waarom ik array kies.
		List<String> status = new LinkedList<>();
		for(int i = 0; i < workStations.size(); i++){
			WorkStation w = this.workStations.get(i);
			String s = w.getName();
			if(0<i) {
				w = this.workStations.get(i-1);
				s = w.getCar() == null ? s.concat(": EMPTY") :
						s.concat(": " + w.getCar().getCarModel().getParts() + " (PENDING)");
			}
			else {
				s = this.carQueue.isEmpty() ? s.concat(": EMPTY") :
						s.concat(": " + carQueue.get(0).toString() + " (PENDING)");
			}
			status.add(s);
		}
		return status.toArray(new String[status.size()]);
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
			if(Objects.equals(wStation.getName(), station)) return wStation;
		}
		System.out.println("There is no workstation with this name"); //throw error?
		return null;

	}

	/*public Map<String,Set<String>> getStationAndTasks(){ //niet gebruikt op het moment
		Map<String,Set<String>> map = new HashMap<>();
		for (WorkStation station :workStations) {
			map.put(station.getName(), station.getTasks());
		}
		return map;
	}*/

	public Set<String> getAvailableTasks(String station) {
			WorkStation workStation = this.getWorkStation(station);
			if(workStation == null) {
				System.out.print("no workStation");
				return null;
			}
			return workStation.getUncompletedTasks();
	}
	
	public void completeTask(String task) {
		//upper case sensitive (task need to be lower case in order 2 work)
		TaskInfo info = TaskInfo.getTaksInfo(task);
		if(info == null) {
			System.out.println("Invalid Task"); // throw error?
			return;
		}
		WorkStation station = this.getWorkStation(info.getWorkStation());
		station.completeTask(task);
		
	}
	
	/**
	 * Returns a string with info for completing a given task
	 * @param task
	 * @return String with info
	 */
	public String getTaskInfo(String task) {
		//upper case sensitive (task need to be lower case in order 2 work)
		TaskInfo info = TaskInfo.getTaksInfo(task);
		if(info == null) {
			System.out.println("Invalid Task"); // throw error?
			return null;
		}
		WorkStation station = this.getWorkStation(info.getWorkStation());
		String partValue = station.getValueOfPart(info.getPartOfTask());
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

	public Set<String> getUncompletedTasks() {
		try {
			Set<String> tasks = this.getTasks();
			tasks.retainAll(this.getCar().getUncompletedTasks()); //upper case sensitive (every task is in lower case in order 2 work)
			return tasks;
			
		} catch(Exception e) {
			System.out.println("There are no available tasks (No car in station)");
			return null;
		}
	}

	private boolean isValidName(String name) {
		return (Objects.equals(name, "Car Body Post")) ||
				(Objects.equals(name, "Drivetrain Post")) || (Objects.equals(name, "Accessories Post"));
	}

	public Set<String> getTasks() {
		return switch (this.getName()) {
			case "Car Body Post" -> new HashSet<>(Arrays.asList("assembly car body", "paint car"));
			case "Drivetrain Post" -> new HashSet<>(Arrays.asList("insert engine", "insert gearbox"));
			case "Accessories Post" -> new HashSet<>(Arrays.asList("install seats", "install airco", "mount wheels"));
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
	public String getValueOfPart(String part) {
		try {
			return this.getCar().getValueOfPart(part);
		} catch(Exception e) {
			System.out.println("There is no car in this workstation");
			return null;
		}
	}
	public void completeTask(String task) {
		try {
			this.getCar().completeTask(task);
		} catch(Exception e) {
			System.out.println("There is no car in this workstation");
		}
	}
}


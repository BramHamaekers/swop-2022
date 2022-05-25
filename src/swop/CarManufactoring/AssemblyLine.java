package swop.CarManufactoring;

import swop.Car.Car;
import swop.Exceptions.NotAllTasksCompleteException;

import java.util.*;

/**
 * A assembly line in a factory consisting of multiple workstations
 */
public class AssemblyLine {

	private final LinkedList<WorkStation> workStations;

	/**
	 * Initialises the assembly line with a list of {@code WorkStation}
	 * @param workStations a list of workstations
	 */
	public AssemblyLine(LinkedList<WorkStation> workStations) {
		if (workStations == null)
			throw new IllegalArgumentException("list of workstations is empty");
		if (workStations.contains(null))
			throw new IllegalArgumentException("empty workstation in list of workstations");
		this.workStations = workStations;
	}

	/**
	 * advance the assembly line (next car may be null)
	 * @param nextCar Next car on the assemblyLine, or null if there is no next car
	 * @throws NotAllTasksCompleteException thrown when there are still tasks to do
	 * @return A finished car or null if there is no finished car
	 */
	public Car advance(Car nextCar) throws NotAllTasksCompleteException{
		// check if possible to advance AssemblyLine
		this.checkAdvance();
		return carToNextWorkStation(nextCar);
	}

	/**
	 * This method will move the cars to the next workstation
	 * @param nextCar is the new car that will be put on the first workstation, or null if there is no further order
	 * @return returns the car leaving the last workstation
	 */
	private Car carToNextWorkStation(Car nextCar) {
		Car completedCar = this.workStations.getLast().getCar();
		// Move all cars on assembly by 1 position
		for (int i = this.workStations.size() - 1; i > 0; i--) {
			Car previous = this.workStations.get(i-1).getCar();
			this.workStations.get(i).setCar(previous);
		}
		this.workStations.getFirst().setCar(nextCar);
		return completedCar;
	}

	/**
	 * Checks if it is possible to advance the assembly line
	 * @throws NotAllTasksCompleteException thrown if there are still tasks to do on the cars on the workstations
	 */
	private void checkAdvance() throws NotAllTasksCompleteException {
		LinkedList<String> w = new LinkedList<>();
		for (WorkStation workStation: this.workStations)
			if (!workStation.stationTasksCompleted()) w.add(workStation.getName());
		
		if(!w.isEmpty()) {
			throw new NotAllTasksCompleteException("Not all tasks completed in: ", w);
		}
	}

	/**
	 * returns list with all workstations
	 * @return a copy of the list of workstations
	 */
	public List<WorkStation> getWorkStations() {
		return List.copyOf(this.workStations);
	}

	/**
	 * returns list of strings with names of all workstations.
	 * @return a list of names of the workstations
	 */
	public List<String> getWorkstationNames(){
		return new LinkedList<>(this.workStations.stream().map(WorkStation::getName).toList());
	}

	/**
	 * Gets all uncompleted tasks of the car at the given workstation
	 * @param station the name "string" of the given workstation
	 * @return all tasks that are uncompleted at station
	 */
	public List<Task> getUncompletedTasks(WorkStation station) {
		if (station == null) throw new IllegalArgumentException("station is null");
		return station.getUncompletedTasks();
	}

	/**
	 * Checks if the assembly Line is empty -> Check if all workstations of assembly line are null
	 * @return True: all workstations are empty | False: not all workstations are empty
	 */
	public boolean isEmptyAssemblyLine() {
		return this.getWorkStations().stream().allMatch(s -> s.getCar() == null);
	}

	/**
	 * Function returns all unFinishedCars on the assemblyLine
	 * @return all unFinishedCars on the assemblyLine
	 */
	public List<Car> getUnfinishedCars() {
		List<WorkStation> unFinishedStations = this.getWorkStations().stream()
				.filter(w -> !w.stationTasksCompleted()).toList();
		return unFinishedStations.stream().map(WorkStation::getCar).toList();
	}

	/**
	 *
	 * @param stationName
	 * @return
	 */
	public WorkStation getStationByName(String stationName) {
		if (this.workStations == null) throw new IllegalStateException("no workstations on the assemblyline");
		if (!this.workStations.stream().map(WorkStation::getName).toList().contains(stationName)) throw new IllegalArgumentException("provided station name not valid");
		for (WorkStation station : this.workStations) {
			if (station.getName().equals(stationName)) {
				return station;
			}
		}
		return null;
	}

	/**
	 *
	 * @param stationName
	 * @return
	 */
	public List<Task> getUncompletedTasksByName(String stationName) {
		if (this.getStationByName(stationName) == null) throw new IllegalArgumentException("provided station name not valid");
		return this.getStationByName(stationName).getUncompletedTasks();
	}

	/**
	 *
	 * @param stationName
	 * @return
	 */
	public List<Task> getCompletedTasksByName (String stationName) {
		if (this.getStationByName(stationName) == null) throw new IllegalArgumentException("provided station name not valid");
		return this.getStationByName(stationName).getCompletedTasks();
	}
}




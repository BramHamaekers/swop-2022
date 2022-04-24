package swop.CarManufactoring;

import swop.Car.Car;
import swop.Exceptions.NotAllTasksCompleteException;

import java.util.*;

public class AssemblyLine {

	private final LinkedList<WorkStation> workStations;

	public AssemblyLine(LinkedList<WorkStation> workStations) {
		this.workStations = workStations;
	}

	/**
	 * advance the assembly line
	 * @param nextCar Next car on the assemblyLine
	 * @throws NotAllTasksCompleteException thrown when there are still tasks to do
	 */
	public Car advance(Car nextCar) throws NotAllTasksCompleteException{
		// check if possible to advance AssemblyLine
		checkAdvance();
		Car completedCar = this.workStations.getLast().getCar();
		//updating completion time of finished car
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
	 * @throws NotAllTasksCompleteException thrown if there are still tasks to do
	 */
	private void checkAdvance() throws NotAllTasksCompleteException {
		LinkedList<String> w = new LinkedList<>();
		for (WorkStation workStation: this.workStations)
			if (!workStation.stationTasksCompleted()) w.add(workStation.getName());
		
		if(!w.isEmpty())
			throw new NotAllTasksCompleteException("Not all tasks completed in: ", w);
	}

	/**
	 * returns list with all workstations
	 * @return this.workStations
	 */
	public List<WorkStation> getWorkStations() {
		return List.copyOf(this.workStations);
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
	 * Gets all uncompleted tasks of the car at the given workstation
	 * @param station the name "string" of the given workstation
	 * @return all tasks that are uncompleted at station
	 */
	public Set<Task> getUncompletedTasks(String station) {
		if (station== null) throw new IllegalArgumentException("station is null");
		for(WorkStation wStation: this.workStations) {
			if(wStation.getName().equals(station)) return wStation.getUncompletedTasks();
		}
		throw new IllegalArgumentException("Not a valid workstation name"); 
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
}




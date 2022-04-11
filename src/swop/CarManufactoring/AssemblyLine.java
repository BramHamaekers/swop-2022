package swop.CarManufactoring;

import swop.Exceptions.NotAllTasksCompleteException;

import java.util.*;

public class AssemblyLine {

	private final LinkedList<WorkStation> workStations;

	public AssemblyLine(LinkedList<WorkStation> workStations) {
		this.workStations = workStations;
	}

	/**
	 * advance the assembly line if a manager orders and all tasks are done
	 * @param minutes minutes past since start of the task
	 * @throws NotAllTasksCompleteException thrown when there are still tasks to do
	 */
	public void advance(Car car, int minutes) throws NotAllTasksCompleteException{
		// check if possible to advance AssemblyLine
		checkAdvance();
		Car completedCar = this.workStations.getLast().getCar();
		//updating completion time of finished car
		//TODO add methed to schedular to get current time?
		//TODO this way of setting completion time is wrong
		if(!(completedCar == null)) completedCar.setDeliveryTime(completedCar.getDeliveryTime()+minutes);
		// Move all cars on assembly by 1 position
		for (int i = this.workStations.size() - 1; i > 0; i--) {
			this.workStations.get(i).setCar(this.workStations.get(i-1).getCar());
		}
		this.workStations.getFirst().setCar(car);

	}

	/**
	 * Checks if it is possible to advance the assembly line
	 * @throws NotAllTasksCompleteException thrown if there are still tasks to do
	 */
	private void checkAdvance() throws NotAllTasksCompleteException {
		LinkedList<String> w = new LinkedList<>();
		for (WorkStation workStation: this.workStations)
			if (!workStation.stationTasksCompleted()) w.add(workStation.getName());
		
		if(!w.isEmpty())throw new NotAllTasksCompleteException("Not all tasks completed in: ", w);
	}
	
	/**
	 * Check if all tasks on all workstations of the assembly line are completed
	 */

	public boolean allTasksCompleted() {
		return this.workStations.stream().allMatch(e -> e.stationTasksCompleted());

	}
	
	/////////////////////////// Functions used 2 get data for manager use case //////////////////////////////
	
	/**
	 * returns for all works stations current state. 
	 * Empty = no car, Finished = all tasks completed, Pending = tasks need 2 be completed
	 * @return list of states from each work station
	 */
	public List<String> getCurrentStatus() {
		List<String> status = new LinkedList<>();
		this.workStations.forEach(w -> {
			String s = w.getName();
			if(w.getCar() == null) s = s.concat(": EMPTY");
			else {
				s = s.concat(": " + w.getCar().getCarModel().getPartsMap());
				s = w.stationTasksCompleted() ? s.concat(" (FINISHED)") : s.concat(" (PENDING)");
			}
			status.add(s);
		});
		return status;
	}
	
	/**
	 * returns for all works stations state if an advance would happen. 
	 * Empty = no car, Finished = all tasks completed, Pending = tasks need 2 be completed
	 * @return list of states from each work station if an advance would take place
	 */

	public List<String> getAdvancedStatus(Car car) { 
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
				s = (car == null) ? s.concat(": EMPTY") :
						s.concat(": " + car.getCarModel().getPartsMap() + " (PENDING)");
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
}


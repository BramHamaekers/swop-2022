package swop.CarManufactoring;

import swop.Exceptions.NotAllTasksCompleteException;
import swop.Parts.Part;

import java.util.*;

public class AssemblyLine {


	 // Queue of cars that still have to be assembled but are not yet on the assembly line
	private final LinkedList<WorkStation> workStations;

	public AssemblyLine(LinkedList<WorkStation> workStations) {
		this.workStations = workStations;
	}


	/**
	 * advance the assembly line if a manager orders and all tasks are done
	 * @param minutes minutes past since start of the task
	 * @throws NotAllTasksCompleteException thrown when there are still tasks to do
	 */
	public void advance(Car car, int minutes) throws NotAllTasksCompleteException {
		if (car == null) throw new IllegalArgumentException("Couldn't advance since car is null");
		// check if possible to advance AssemblyLine
		checkAdvance();
		// Move all cars on assembly by 1 position
		for (int i = this.workStations.size() - 1; i > 0; i--) {
			this.workStations.get(i).setCar(this.workStations.get(i-1).getCar());
		}

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
	 * @return list of states from each work station
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
	 * @return list of states from each work station if an advance would take place
	 */
	/*
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
	*/

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


	public void updateCarCompletionTime() {
		// TODO Auto-generated method stub
		
	}
}

/////////////////////////////////////////////////// WORKSTATION CLASS //////////////////////////////////////////////////



/////////////////////////////////////////////////// SCHEDULAR CLASS /////////////////////////////////////////////////////


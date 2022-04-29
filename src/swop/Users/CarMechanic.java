package swop.Users;

import java.util.*;

import swop.CarManufactoring.Task;
import swop.CarManufactoring.WorkStation;
import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.UI.CarMechanicUI;

/**
 * A car mechanic user
 */
public class CarMechanic extends User{
	/**
	 * initializes a car mechanic user
	 * @param id a given id for the car mechanic
	 */
    public CarMechanic(String id) {
        super(id);
    }
    
    /**
     * Called when logging in as CarMechanic
	 * @param assemAssist given the main program
     */
	@Override
	public void load(AssemAssist assemAssist) {
		if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
		try {
			CarMechanicUI.init(this.getId());
			this.selectAction(assemAssist);
		} catch (CancelException e) {
			e.printMessage();
		}
	}

	/**
	 * Function that handles selecting an action for CarMechanic
	 * @param assemAssist the central system the action is performed on
	 */
	@Override
	public void selectAction(AssemAssist assemAssist) throws CancelException {
		List<String> actions = Arrays.asList("performAssemblyTask", "checkAssemblyLineStatus", "Exit");
		int action = CarMechanicUI.selectAction(actions, "What would you like to do?");

		switch (action) {
			case 0 -> this.performAssemblyTask(assemAssist);
			case 1 -> this.checkAssemblyLineStatus(assemAssist);
			case 2 -> {
				// Do Nothing
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	/**
	 * Helper function to display all tasks for each station
	 * @param assemAssist given the main program
	 */
	private void checkAssemblyLineStatus(AssemAssist assemAssist) {
		if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
		List<WorkStation> workStations = assemAssist.getStations();

		workStations.forEach(station -> {
			List<Task> pendingTasks = station.getUncompletedTasks();
			//TODO finishedTasks should be filtered in station.getCompletedTasks()
			List<Task> finishedTasks = station.getCompletedTasks();
			CarMechanicUI.displayStationStatus(station, pendingTasks, finishedTasks);
		});
	}

	/**
	 * Helper function to perform a task for this mechanic
	 * @param assemAssist given the main program
	 * @throws CancelException when "CANCEL" is the input
	 */
	private void performAssemblyTask(AssemAssist assemAssist) throws CancelException {
		WorkStation workStation = this.selectStation(assemAssist);
		if (workStation == null) throw new IllegalArgumentException("workstation is invalid");
		this.performTaskAtWorkStation(assemAssist, workStation);
	}

	/**
	 * Allows user to perform tasks at a given workstation on a given central system
	 * @param assemAssist the given central system
	 * @param workStation the workstation the user wants to perform tasks on
	 * @throws CancelException when the user types "CANCEL"
	 */
	private void performTaskAtWorkStation(AssemAssist assemAssist, WorkStation workStation) throws CancelException {
		List<Task> taskList = workStation.getUncompletedTasks();
		//returns selected task by user
		Task task = this.selectTask(taskList);
		//return list of all the tasks
		if (task != null) {
			//Show the information for given task 2 user
			this.showInfo(assemAssist, task);
			this.completeTask(assemAssist, task);
			this.performTaskAtWorkStation(assemAssist, workStation);
		}
		else {
			CarMechanicUI.noTasks();
		}
	}

	/**
	 * Helper function to complete a task in assemAssist
	 * @param assemAssist given the main program
	 * @param task task from the tasklist
	 * @throws CancelException when "CANCEL" is the input
	 */
	private void completeTask(AssemAssist assemAssist, Task task) throws CancelException {
		if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
		if (task == null) throw new IllegalArgumentException("task is null");
		int time = CarMechanicUI.askTimeToCompleteTask();
		assemAssist.completeTask(task, time);
	}

	/**
	 * Shows info of given task
	 * 	this consists of instruction of each part + value concerning current task.
	 * @param assemAssist given the main program
	 * @param task task from the tasklist
	 * @throws CancelException when "CANCEL" is the input
	 */
	private void showInfo(AssemAssist assemAssist, Task task) throws CancelException {
		if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
		if (task == null) throw new IllegalArgumentException("task is null");
		List<String> info = assemAssist.getTaskDescription(task);
		CarMechanicUI.displayTaskInfo(info);
	}

	/**
	 * Selects a task from available task list.
	 * @param taskList List of all available tasks
	 * @return return a task selected from the available tasks
	 * @throws CancelException when "CANCEL" is the input
	 */
	private Task selectTask(List<Task> taskList) throws CancelException {
		if (taskList == null || taskList.isEmpty()) return null;
		CarMechanicUI.displayAvailableTasks(taskList);
		int option = CarMechanicUI.askOption("Select task: ", taskList.size());
		return taskList.get(option);
	}

	/**
	 * Select a station for the car mechanic to work at.
	 * @param assemAssist given the main program
	 * @return return a station selected from the available stations
	 * @throws CancelException when "CANCEL" is the input
	 */
	private WorkStation selectStation(AssemAssist assemAssist) throws CancelException {
		if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
		List<WorkStation> workStations = assemAssist.getStations();
		//asks user for workstation
		CarMechanicUI.displayAvailableStations(workStations);
		int option = CarMechanicUI.askOption("Select station: ", workStations.size());	
		return workStations.get(option);
	}
}

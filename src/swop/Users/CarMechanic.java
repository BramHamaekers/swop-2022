package swop.Users;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import swop.CarManufactoring.Task;
import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.UI.CarMechanicUI;

public class CarMechanic extends User{
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
		CarMechanicUI.init(this.getId());
		try {
			//returns work station selected by the user
			String workStation = this.selectStation(assemAssist);
			
			this.performTasks(workStation, assemAssist);

		} catch (CancelException e) {
			e.printMessage();
		}
	}

	/**
	 * Helper function to perform a task for this mechanic
	 * @param workStation workstation as a string from the available list of workstations
	 * @param assemAssist given the main program
	 * @throws CancelException CancelException when "CANCEL" is the input
	 */
	private void performTasks(String workStation, AssemAssist assemAssist) throws CancelException {
		if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
		if (workStation == null) throw new IllegalArgumentException("workstation is invalid");
		List<Task> taskList = getAvailableTasks(assemAssist, workStation);
		//returns selected task by user
		Task task = this.selectTask(taskList);
		//return list of all the tasks
		if (task != null) {
			//Show the information for given task 2 user
			this.showInfo(assemAssist, task);
			this.completeTask(assemAssist, task);
			this.performTasks(workStation,assemAssist);
		}
	}

	/**
	 * Helper function to complete a task in assemAssist
	 * @param assemAssist given the main program
	 * @param task task from the tasklist
	 */
	private void completeTask(AssemAssist assemAssist, Task task) {
		if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
		if (task == null) throw new IllegalArgumentException("task is null");
		assemAssist.completeTask(task);
	}

	/**
	 * Shows info of given task
	 * 	this consists of instruction of each part + value concerning current task.
	 * @param assemAssist given the main program
	 * @param task task from the tasklist
	 * @throws CancelException CancelException when "CANCEL" is the input
	 */
	private void showInfo(AssemAssist assemAssist, Task task) throws CancelException {
		if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
		if (task == null) throw new IllegalArgumentException("task is null");
		String info = assemAssist.getTaskDescription(task);
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
	private String selectStation(AssemAssist assemAssist) throws CancelException {
		if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
		List<String> workStations = assemAssist.getStations();
		//asks user for workstation
		CarMechanicUI.displayAvailableStations(workStations);
		int option = CarMechanicUI.askOption("Select station: ", workStations.size());	
		return workStations.get(option);
	}

	/**
	 * Get all available tasks
	 * @param assemAssist given the main program
	 * @param workStation workstation as a string from the available list of workstations
	 * @return returns all available task at current workstation.
	 */
	private List<Task> getAvailableTasks(AssemAssist assemAssist, String workStation) {
		if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
		if (workStation == null) throw new IllegalArgumentException("workstation is invalid");
		Set<Task> tasks = assemAssist.getsAvailableTasks(workStation);
		if(tasks == null) return null; //Throw error?
		return new LinkedList<>(tasks);
	}

}

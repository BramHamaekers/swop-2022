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

    private String workStation; //temp tot betere oplossing
    
    /**
     * Called when logging in as CarMechanic
     */
	@Override
	public void load(AssemAssist assemAssist) {
		try {
			//returns work station selected by the user
			if(workStation == null) workStation = this.selectStation(assemAssist);
			//return list of all the tasks
			List<Task> taskList = getAvailableTasks(assemAssist, workStation);
			//returns selected task by user
			Task task;
			task = this.selectTask(taskList);
			//Show the information for given task 2 user
			if (task != null) {
				this.showInfo(assemAssist, task);
				this.completeTask(assemAssist, task);
				this.load(assemAssist);
			}
		} catch (CancelException e) {
			e.printMessage();
		}
		workStation = null;
	}

	/**
	 * Will try to complete a task
	 */
	private void completeTask(AssemAssist assemAssist, Task task) {
		assemAssist.completeTask(task);
		
	}
	
	/**
	 * Shows info of given task
	 * this consists of instruction of each part + value concerning current task.
	 */
	private void showInfo(AssemAssist assemAssist, Task task) {
		String info = assemAssist.getTaskDescription(task);
		CarMechanicUI.displayTaskInfo(info);
	}
	
	/**
	 * Selects a task from available task list.
	 * @throws CancelException when cancelled.
	 */
	private Task selectTask(List<Task> taskList) throws CancelException {
		if(taskList == null || taskList.isEmpty()) return null; //throw error?
		CarMechanicUI.displayAvailableTasks(taskList);
		int option = CarMechanicUI.askOption("Select task: ", taskList.size());
		return taskList.get(option);
		
	}

	/**
	 * Selects a station for the car mechanic to work at.
	 * @throws CancelException when cancelled.
	 */
	private String selectStation(AssemAssist assemAssist) throws CancelException {
		List<String> workStations = assemAssist.getStations();
		//asks user for workstation
		CarMechanicUI.displayAvailableStations(workStations);
		int option = CarMechanicUI.askOption("Select station: ", workStations.size());	
		return workStations.get(option);
	}

	/**
	 * returns all available task at current workstation.
	 */
	private List<Task> getAvailableTasks(AssemAssist assemAssist, String workStation) {
		Set<Task> tasks = assemAssist.getsAvailableTasks(workStation);
		if(tasks == null) return null; //Throw error?
		return new LinkedList<>(tasks);
	}

}

package swop.Users;

import java.util.*;

import swop.CarManufactoring.Task;
import swop.CarManufactoring.WorkStation;
import swop.Main.AssemAssist;

/**
 * A car mechanic user
 */
public class CarMechanic extends User{
	/**
	 * initializes a car mechanic user
	 * @param id a given id for the car mechanic
	 */
    public CarMechanic(String id, AssemAssist assemAssist) {
        super(id, assemAssist);
    }

	/**
	 * Get a list of names of all the workstations on the assembly line
	 * @return list of strings, strings are names of all the workstations on the assembly line
	 */
	public List<String> getStationNames() {
		return this.assemAssist.getController().getAssembly().getWorkstationNames();
	}

	/**
	 * Get all the uncompleted tasks at a workstation given the name of that station
	 * @param stationName the given workstation name
	 * @return Copy of list of all tasks that are uncompleted at the workstation
	 */
	public List<Task> getUncompletedTasks(String stationName) {
		List<Task> copyOfUncompletedTasks = new ArrayList<>();
		this.assemAssist.getController().getAssembly().getUncompletedTasksByName(stationName).forEach(t -> copyOfUncompletedTasks.add(t.clone()));
		return copyOfUncompletedTasks;
	}

	/**
	 * Get all the completed tasks at a workstation given the name of that station
	 * @param stationName the given workstation name
	 * @return Copy of list of all tasks that are completed at the workstation
	 */
	public List<Task> getCompletedTasks(String stationName) {
	List<Task> copyOfUncompletedTasks = new ArrayList<>();
		this.assemAssist.getController().getAssembly().getCompletedTasksByName(stationName).forEach(t -> copyOfUncompletedTasks.add(t.clone()));
		return copyOfUncompletedTasks;
	}

	/**
	 * Methods that lets a carMechanic complete a task with the given name at a workstation with the given name
	 * @param stationName the given name of a workstation to complete a task at
	 * @param taskName the name of the task you want to complete at a workstation
	 * @param passedTime the time it took to complete the task
	 */
	public void completeTask(String stationName, String taskName, int passedTime){
		if(!isUncompletedTaskAtStation(stationName, taskName)) {
			throw new IllegalArgumentException("Cannot complete task " + taskName + ", " +
					"task does not exits at " + stationName + "or is already completed");
		}
		WorkStation workstation = this.assemAssist.getController().getAssembly().getStationByName(stationName);
		Task task = this.assemAssist.getController().getAssembly().getUncompletedTasksByName(stationName).stream()
				.filter(t -> t.getName().equals(taskName))
				.findFirst().orElse(null);
		workstation.completeTask(task, passedTime);
	}

	/**
	 * Check if the given task is an uncompleted task at the station with the given stationName
	 * @param stationName the given name of a station
	 * @param taskName the given name of a task you want to complete
	 * @return True if the given task is not yet completed at the station with the given stationName
	 */
	public boolean isUncompletedTaskAtStation(String stationName, String taskName) {
		return this.getUncompletedTasks(stationName).stream().map(Task::getName).toList().contains(taskName);
	}
}

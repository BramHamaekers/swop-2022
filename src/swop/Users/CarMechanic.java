package swop.Users;

import java.util.*;

import swop.CarManufactoring.Task;
import swop.CarManufactoring.WorkStation;
import swop.Main.AssemAssist;

/**
 * A car mechanic user
 */
public class CarMechanic extends User{
	private AssemAssist assemAssist;
	/**
	 * initializes a car mechanic user
	 * @param id a given id for the car mechanic
	 */
    public CarMechanic(String id, AssemAssist assemAssist) {
        super(id);
		this.assemAssist = assemAssist;
    }

	public List<String> getStationNames() {
		return this.assemAssist.getController().getAssembly().getWorkstationNames();
	}

	public List<Task> getUncompletedTasks(String stationName) {
		return this.assemAssist.getController().getAssembly().getUncompletedOfStationTasksByName(stationName);
	}

	public List<Task> getCompletedTasks(String stationName) {
		return this.assemAssist.getController().getAssembly().getCompletedTasksOfStationByName(stationName);
	}

	public void completeTask(String stationName, Task task, int timepassed){
		WorkStation workstation = this.assemAssist.getController().getAssembly().getStationByName(stationName);
		workstation.completeTask(task, timepassed);
	}
}

package swop.Users;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import swop.Main.AssemAssist;
import swop.UI.CarMechanicUI;

public class CarMechanic extends User{
    public CarMechanic(String id) {
        super(id);
    }

    private String workStation; //temp tot betere oplossing
	@Override
	public void load(AssemAssist assemAssist) {
		//returns work station selected by the user
		if(workStation == null) workStation = this.selectStation(assemAssist);
		//return list of all the tasks
		List<String> taskList = getAvailableTasks(assemAssist, workStation);
		//returns selected task by user
		String task = this.selectTask(taskList);
		//Show the information for given task 2 user
		if (task != null) {
			this.showInfo(assemAssist, task);
			this.completeTask(assemAssist, task);
			this.load(assemAssist);
		}
		workStation = null;
	}


	private void completeTask(AssemAssist assemAssist, String task) {
		assemAssist.completeTask(task);
		
	}

	private void showInfo(AssemAssist assemAssist, String task) {
		String info = this.getTaskInfo(assemAssist, task);
		CarMechanicUI.displayTaskInfo(info);
	}

	private String selectTask(List<String> taskList) {
		if(taskList == null || taskList.isEmpty()) return null; //throw error?
		CarMechanicUI.displayAvailableTasks(taskList);
		int option = CarMechanicUI.askOption("Select task: ", taskList.size());
		return taskList.get(option);
		
	}

	private String selectStation(AssemAssist assemAssist) {
		List<String> workStations = assemAssist.getStations();
		//asks user for workstation
		CarMechanicUI.displayAvailableStations(workStations);
		int option = CarMechanicUI.askOption("Select station: ", workStations.size());	
		return workStations.get(option);
	}

	
	private List<String> getAvailableTasks(AssemAssist assemAssist, String workStation) {
		Set<String> tasks = assemAssist.getsAvailableTasks(workStation);
		if(tasks == null) return null; //Throw error?
		return new LinkedList<>(tasks);
	}

	private String getTaskInfo(AssemAssist assemAssist, String task) {
		return assemAssist.getTaskInfo(task);
	}

}

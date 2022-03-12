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

	@Override
	public void load(AssemAssist assemAssist) {
		//returns work station selected by the user
		String workStation = this.selectStation(assemAssist);
		//return list of all the tasks
		List<String> taskList = getAvailableTasks(assemAssist, workStation);
		//returns selected task by user
		String task = this.selectTask(taskList);
		//Show the information for given task 2 user
		if (task != null) this.showInfo(assemAssist, task);
	}


	private void showInfo(AssemAssist assemAssist, String task) {
		String info = this.getTaskInfo(assemAssist, task);
		CarMechanicUI.displayTaskInfo(info);
	}

	private String selectTask(List<String> taskList) {
		if(taskList == null) return null; //throw error?
		CarMechanicUI.displayAvailableTasks(taskList);
		String option = CarMechanicUI.askOption("Select task: ");
		while(!isValidString(option, taskList.size())) option = CarMechanicUI.askOption("Give Valid Option: ");
		return taskList.get(Integer.parseInt(option));
		
	}

	private String selectStation(AssemAssist assemAssist) {
		List<String> workStations = assemAssist.getStations();
		//asks user for workstation
		CarMechanicUI.displayAvailableStations(workStations);
		String option = CarMechanicUI.askOption("Select station: ");
		//keeps asking untill valid awnser
		while(!isValidString(option, workStations.size()))
			option = CarMechanicUI.askOption("Give Valid Option: ");
		
		return workStations.get(Integer.parseInt(option));
	}

	
	private List<String> getAvailableTasks(AssemAssist assemAssist, String workStation) {
		Set<String> tasks = assemAssist.getsAvailableTasks(workStation);
		if(tasks == null) return null; //Throw error?
		return new LinkedList<>(tasks);
	}

	private String getTaskInfo(AssemAssist assemAssist, String task) {
		return assemAssist.getTaskInfo(task);
	}


	//TODO rename/rewrite function please
	private boolean isValidString(String intg, int size) {
		try{
            int number = Integer.parseInt(intg);
			return number < size && number >= 0;
		}
        catch (NumberFormatException ex){
            return false;
        }
	}
}

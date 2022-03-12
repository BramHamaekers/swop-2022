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
		List<String> workStations = assemAssist.getStations();
		CarMechanicUI.displayAvailableStations(workStations);

		String option = CarMechanicUI.askNumber("Select station: ");
		while(!isValidString(option, workStations.size()))
			option = CarMechanicUI.askNumber("Give Valid Option: ");
		String workStation = workStations.get(Integer.parseInt(option));
		List<String> taskList = getAvailableTasks(assemAssist, workStation);

		CarMechanicUI.displayAvailableTasks(taskList);
		option = CarMechanicUI.askNumber("Select task: ");

		while(!isValidString(option, taskList.size())) option = CarMechanicUI.askNumber("Give Valid Option: ");
		String info = this.getTaskInfo(assemAssist, taskList.get(Integer.parseInt(option)));
		CarMechanicUI.displayTaskInfo(info);
	}

	/**
	 *
	 * @param assemAssist
	 * @param workStation Name of the selected workStation
	 * @return List of available tasks in the selected workstation
	 */
	private List<String> getAvailableTasks(AssemAssist assemAssist, String workStation) {
		List<String> taskList = new LinkedList<>();
		try {
			Set<String> tasks = assemAssist.getsAvailableTasks(workStation);
			taskList = new LinkedList<>(tasks);
		}
		catch (NullPointerException e) {
			System.out.println("No Tasks to complete");
		}
		return taskList;
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

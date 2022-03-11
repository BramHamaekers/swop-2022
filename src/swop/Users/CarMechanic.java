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
		List<String> stations = assemAssist.getStations();
		CarMechanicUI.displayAvailableStations(stations);
		String st = CarMechanicUI.askNumber("Select station: ");
		while(!isvalidString(st, stations.size())) st = CarMechanicUI.askNumber("Give Valid Option: ");
		Set<String> tasks = getAvailableTasks(assemAssist, stations.get(Integer.parseInt(st))); //returns the still 2 complete tasks at the station on current car.
		List<String> taskList = new LinkedList<String>(tasks);
		//TODO opsplitsen in helper methods
		CarMechanicUI.displayAvailableTasks(taskList);
		st = CarMechanicUI.askNumber("Select task: ");
		while(!isvalidString(st, tasks.size())) st = CarMechanicUI.askNumber("Give Valid Option: ");
		String info = this.getTaskInfo(assemAssist, taskList.get(Integer.parseInt(st)));
		CarMechanicUI.displayTaskInfo(info);
	}

	private String getTaskInfo(AssemAssist assemAssist, String task) {
		return assemAssist.getTaskInfo(task);
	}

	private Set<String> getAvailableTasks(AssemAssist assemAssist, String station) {
		return assemAssist.getsAvailableTask(station);
	}

	private boolean isvalidString(String intg, int size) {
		try{
            int number = Integer.parseInt(intg);
            if(number < size && number >= 0) return true;
            return false;
        }
        catch (NumberFormatException ex){
            return false;
        }
	}
}

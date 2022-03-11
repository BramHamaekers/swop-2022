package swop.Users;

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
		String station = CarMechanicUI.askNumber();
		while(!isvalidstation(station, stations.size())) station = CarMechanicUI.askNumber();
		Set<String> tasks = getAvailableTasks(assemAssist, Integer.parseInt(station), stations);
		CarMechanicUI.displayAvailableTasks(tasks);
	}

	private Set<String> getAvailableTasks(AssemAssist assemAssist, int parseInt, List<String> stations) {
		return assemAssist.getsAvailableTask(stations.get(parseInt));
	}

	private boolean isvalidstation(String intg, int size) {
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

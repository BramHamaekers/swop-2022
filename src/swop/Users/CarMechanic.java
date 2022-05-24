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
	private AssemAssist assemAssist;
	/**
	 * initializes a car mechanic user
	 * @param id a given id for the car mechanic
	 */
    public CarMechanic(String id, AssemAssist assemAssist) {
        super(id);
		this.assemAssist = assemAssist;
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


	@Override
	public void selectAction(AssemAssist assemAssist) throws CancelException {
//		List<String> actions = Arrays.asList("performAssemblyTask", "checkAssemblyLineStatus", "Exit");
//		int action = CarMechanicUI.selectAction(actions, "What would you like to do?");
//
//		switch (action) {
//			case 0 -> this.performAssemblyTask(assemAssist);
//			case 1 -> this.checkAssemblyLineStatus(assemAssist);
//			case 2 -> {
//				// Do Nothing
//			}
//			default -> throw new IllegalArgumentException("Unexpected value: " + action);
//		}
	}

	public List<WorkStation> getStations(){
		return this.assemAssist.getStations();
	}
}

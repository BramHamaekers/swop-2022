package swop.Users;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import swop.CarManufactoring.Car;
import swop.Exceptions.NotAllTasksCompleteException;
import swop.Main.AssemAssist;
import swop.UI.ManagerUI;

public class Manager extends User{
	

    public Manager(String id) {
        super(id);
    }

	@Override
	public void load(AssemAssist assemAssist) {
		ManagerUI.init(getId());
		//1.
		String indicate = ManagerUI.indicateAdvance();
		while (!isValidYesNo(indicate)) {
			indicate = ManagerUI.indicateAdvance();
		}
		if (Objects.equals(indicate, "n")) return;
		this.addvanceAssemblyLine(assemAssist);

	}
	private void addvanceAssemblyLine(AssemAssist assemAssist) {
		ManagerUI.displayAssemblyLine(assemAssist.getCurrentAssemblyStatus(), assemAssist.getAdvancedAssemblyStatus());
		String indicate = ManagerUI.confirmAdvance();
		while (!isValidYesNo(indicate)) {
			ManagerUI.confirmAdvance();
		}
		if (Objects.equals(indicate, "n")) return;
		String time = ManagerUI.askTime(); //TODO check valid time
		
		try {
			assemAssist.advanceAssembly();
			ManagerUI.exit(assemAssist.getCurrentAssemblyStatus());
			} //TODO give time with advanceAssembly() to assemblyline.
		catch (NotAllTasksCompleteException e) {
			ManagerUI.printException(e);
		}
	}
    
}

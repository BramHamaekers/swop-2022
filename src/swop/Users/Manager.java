package swop.Users;

import java.util.Objects;

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
		//let's now user wants 2 advance
		String indicate = ManagerUI.indicateAdvance();
		if (Objects.equals(indicate, "n")) return;
		//advance assembly
		this.advanceAssemblyLine(assemAssist);

	}
	private void advanceAssemblyLine(AssemAssist assemAssist) {
		ManagerUI.displayAssemblyLine(assemAssist.getCurrentAssemblyStatus(), assemAssist.getAdvancedAssemblyStatus());
		//confirm advance
		String indicate = ManagerUI.confirmAdvance();
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

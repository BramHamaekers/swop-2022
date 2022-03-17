package swop.Users;

import java.util.Objects;

import swop.Exceptions.CancelException;
import swop.Exceptions.NotAllTasksCompleteException;
import swop.Main.AssemAssist;
import swop.UI.ManagerUI;

public class Manager extends User{
	

    public Manager(String id) {
        super(id);
    }

    /**
     * Called when logging in as Manager
     */
	@Override
	public void load(AssemAssist assemAssist) {
		ManagerUI.init(getId());
		//let's know user wants 2 advance
		try {
			String indicate = ManagerUI.indicateAdvance();
			if (Objects.equals(indicate, "n")) return;
		//advance assembly
			this.advanceAssemblyLine(assemAssist);
		} catch (CancelException e) {
			e.printMessage();
		}

	}
	
	/**
	 * Class handling everything advancement of assembly line
	 * @throws CancelException
	 */
	private void advanceAssemblyLine(AssemAssist assemAssist) throws CancelException {
		ManagerUI.displayAssemblyLine(assemAssist.getCurrentAssemblyStatus(), assemAssist.getAdvancedAssemblyStatus());
		//confirm advance
		String indicate = ManagerUI.confirmAdvance();
		if (Objects.equals(indicate, "n")) return;
		int time = ManagerUI.askTime();

		try {
			assemAssist.advanceAssembly(time);
			ManagerUI.exit(assemAssist.getCurrentAssemblyStatus());
			} 
		catch (NotAllTasksCompleteException e) {
			ManagerUI.printException(e);
		}
	}
    
}

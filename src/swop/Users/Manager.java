package swop.Users;

import java.util.Arrays;
import java.util.List;

import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.UI.ManagerUI;

public class Manager extends User{
	

    public Manager(String id) {
        super(id);
    }

    /**
     * Called when logging in as Manager
	 * @param assemAssist assemAssist given the main program
     */
	@Override
	public void load(AssemAssist assemAssist) {
		if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");

		try {
			ManagerUI.init(this.getId());
			this.selectAction(assemAssist);
		} catch (CancelException e) {
			e.printMessage();
		}
		}

	/**
	 * Function that handles selecting an action for Manager
	 * @param assemAssist the central system the action is performed on
	 */
	@Override
	public void selectAction(AssemAssist assemAssist) throws CancelException {
		List<String> actions = Arrays.asList("Check Production Statistics", "Adapt Scheduling Algorithm", "Exit");
		int action = ManagerUI.selectAction(actions);

		switch (action) {
			case 0 -> this.checkProductionStatistics();
			case 1 -> this.AdaptSchedulingAlgorithm();
			case 2 -> {
				// Do Nothing
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	private void AdaptSchedulingAlgorithm() {
		System.out.println("Adapt Scheduling Algorithm not yet implemented");
	}

	private void checkProductionStatistics() {
		System.out.println("checkProductionStatistics not yet implemented");
	}
}

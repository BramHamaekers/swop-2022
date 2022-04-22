package swop.UI;

import swop.Exceptions.CancelException;
import swop.UI.Generators.ManagerGenerator;

import java.util.List;

public class ManagerUI implements UI {

	private static final ManagerGenerator managerGenerator = new ManagerGenerator();

    public static void init(String id) {
        System.out.println("Welcome Manager: " + id + " (You can cancel any action by typing: CANCEL)");
    }

	/**
	 * Asks which action the user wants to take
	 * @return int indicating the chosen action
	 * @param actions available actions for the user
	 * @throws CancelException when the user types 'Cancel'
	 */
	public static int selectAction(List<String> actions, String question) throws CancelException {
		return UI.selectAction(managerGenerator, actions, question);
	}
}

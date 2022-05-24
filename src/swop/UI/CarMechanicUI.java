package swop.UI;

import java.util.List;

import swop.CarManufactoring.Task;
import swop.CarManufactoring.WorkStation;
import swop.Exceptions.CancelException;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.CarMechanicGenerator;

/**
 * Class handles all UI interaction with the user for CarMechanic
 */
public class CarMechanicUI implements UI {

	private static final CarMechanicGenerator carMechanicGenerator = new CarMechanicGenerator();

	/**
	 * welcomes user with a welcome message
	 * @param id string of the id of the user
	 */
	public static void init(String id) {
		System.out.println("Welcome Car Mechanic: (You can cancel any action by typing: CANCEL)");
	}

	/**
	 * Asks which action the user wants to take
	 *
	 * @param actions available actions for the user
	 * @param question an initial question for which to display options
	 * @return int indicating the chosen action
	 * @throws CancelException when the user types 'Cancel'
	 */
	public static int selectAction(List<String> actions, String question) throws CancelException {
		return UI.selectAction(carMechanicGenerator, actions, question);
	}

	/**
	 * Ask the CarMechanic how long it took him to complete a task
	 * @return scanner.scanNextLineOfTypeInt();
	 * @throws CancelException when the user types "Cancel"
	 */
	public static int askTimeToCompleteTask() throws CancelException {
		System.out.println("How much time did it take to finish the task? (in min)");
		return scanner.scanNextLineOfTypeInt();
	}

	/**
	 * Display that no tasks need to be completed
	 */
	public static void noTasks() {
		System.out.println("No tasks need to be completed!");

	}
}
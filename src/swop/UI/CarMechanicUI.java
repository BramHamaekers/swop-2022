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
		System.out.println("Welcome Car Mechanic: " + id + " (You can cancel any action by typing: CANCEL)");
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
	 * Display all the workstations of the given station list
	 * @param stations the given list
	 */
	public static void displayAvailableStations(List<WorkStation> stations) {
		DisplayStatus builder = new DisplayStatus();
		carMechanicGenerator.generateStationList(builder, stations);
		System.out.print(builder.getDisplay());
	}

	/**
	 * Get the input from the user
	 * @param s a string to display
	 * @param numberOfOptions the number of options as an integer
	 * @return return the input from the user
	 * @throws CancelException if the user types "cancel"
	 */
	public static int askOption(String s, int numberOfOptions) throws CancelException {
		return scanner.scanNextLineOfTypeInt(0, numberOfOptions);

	}

	/**
	 * Display all tasks of the given tasklist
	 * @param taskList the given taskList
	 */
	public static void displayAvailableTasks(List<Task> taskList) {
		DisplayStatus builder = new DisplayStatus();
		carMechanicGenerator.generateAvailableTasks(builder, taskList);
		System.out.print(builder.getDisplay());
	}

	/**
	 * display taskInfo
	 * @param info the given info
	 * @throws CancelException when user types "CANCEL"
	 */
	public static void displayTaskInfo(List<String> info) throws CancelException {
		DisplayStatus builder = new DisplayStatus();
		carMechanicGenerator.generateTaskInfo(builder, info);
		System.out.println(builder.getDisplay());
		scanner.scanNextLineOfTypeString();
	}

	/**
	 * Display the status of a station including pending tasks and finished tasks
	 * @param workStation the given workstation to print the status of
	 * @param pendingTasks the pending tasks on the given workstation
	 * @param finishedTasks the finished tasks on the given workstation
	 */
	public static void displayStationStatus(WorkStation workStation, List<Task> pendingTasks, List<Task> finishedTasks) {
		DisplayStatus builder = new DisplayStatus();
		carMechanicGenerator.generateWorkStationStatus(builder, workStation.getName(), pendingTasks, finishedTasks);
		System.out.print(builder.getDisplay());
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
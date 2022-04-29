package swop.UI;

import swop.Exceptions.CancelException;
import swop.Records.AllStats;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.ManagerGenerator;

import java.util.List;
import java.util.Map;
/**
 * Class handles all UI interaction with the user for Manager
 */
public class ManagerUI implements UI {

	private static final ManagerGenerator managerGenerator = new ManagerGenerator();

	/**
	 * welcomes user with a welcome message
	 * @param id string of the id of the user
	 */
    public static void init(String id) {
        System.out.println("Welcome Manager: " + id + " (You can cancel any action by typing: CANCEL)");
    }

	/**
	 * Asks which action the user wants to take
	 * @return int indicating the chosen action
	 * @param actions available actions for the user
	 * @param currentAlgo the current algorithm in use
	 * @throws CancelException when the user types 'Cancel'
	 */
	public static int selectAction(List<String> actions, String currentAlgo) throws CancelException {
		DisplayStatus builder = new DisplayStatus();
		managerGenerator.generateAlgorithmSelection(builder, actions, currentAlgo);
		System.out.println(builder.getDisplay());
		//TODO: check if we cant move these to generator aswell
		return scanner.scanNextLineOfTypeInt(0, actions.size());
	}

	/**
	 * Display all batch options and get the selection
	 * @param possibleBatch a list of all possible batches to display
	 * @return the selected batchoption
	 * @throws CancelException when the user types 'Cancel'
	 */
	public static Map<String,String> getBatchSelection(List<Map<String, String>> possibleBatch) throws CancelException {
		DisplayStatus builder = new DisplayStatus();
		managerGenerator.generateBatchSelection(builder, possibleBatch);
		System.out.println(builder.getDisplay());
		int selection = scanner.scanNextLineOfTypeInt(0, possibleBatch.size());
		System.out.println("Batch changed to: " + selection);
		return possibleBatch.get(selection);
	}
	/**
	 * Prints an error message
	 * @param e the error message to print
	 */
	public static void printError(String e) {
		UI.printError(e);
	}

	/**
	 * Asks which action the user wants to take
	 * @return int indicating the chosen action
	 * @param actions available actions for the user
	 * @param question an initial question for which to display options
	 * @throws CancelException when the user types 'Cancel'
	 */
	public static int selectFlow(List<String> actions, String question) throws CancelException {
		return UI.selectAction(managerGenerator, actions, question);
	}

	/**
	 * Generates to production statistics
	 * @param stats AllStats record with all statistics
	 * @throws CancelException when the user types 'Cancel'
	 */
	public static void showProductionStatistics(AllStats stats) throws CancelException {
		DisplayStatus builder = new DisplayStatus();
		managerGenerator.generateProductionStatistics(builder, stats);
		System.out.println(builder.getDisplay());
		scanner.scanNextLineOfTypeString();
	}
}

package swop.UI;

import swop.Exceptions.CancelException;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Builders.FormBuilder;
import swop.UI.Generators.ManagerGenerator;

import java.util.List;
import java.util.Map;

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
	public static int selectAction(List<String> actions, String currentAlgo) throws CancelException {
		DisplayStatus builder = new DisplayStatus();
		managerGenerator.generateAlgorithmSelection(builder, actions, currentAlgo);
		System.out.println(builder.getDisplay());
		//TODO: check if we cant move these to generator aswell
		return scanner.scanNextLineOfTypeInt(0, actions.size());
	}

	public static Map<String,String> getBatchSelection(List<Map<String, String>> possibleBatch) throws CancelException {
		DisplayStatus builder = new DisplayStatus();
		managerGenerator.generateBatchSelection(builder, possibleBatch);
		System.out.println(builder.getDisplay());
		int selection = scanner.scanNextLineOfTypeInt(0, possibleBatch.size());
		System.out.println("Batch changed to: " + selection);
		return possibleBatch.get(selection);
	}

	public static void printError(String e) {
		UI.printError(e);
	}

	public static int selectFlow(List<String> actions, String question) throws CancelException {
		return UI.selectAction(managerGenerator, actions, question);
	}
}

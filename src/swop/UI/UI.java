package swop.UI;

import java.util.List;
import java.util.Scanner;

import swop.Exceptions.CancelException;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.UserGenerator;

interface UI {
	InputScanner scanner = new InputScanner(new Scanner(System.in));

	/**
	 * Ask user a Yes/No question and return the response
	 * @param action the question to ask
	 * @return "y" if yes, "n" if no
	 * @throws CancelException when the user types "Cancel"
	 */
	static String indicateYesNo(String action) throws CancelException {
		System.out.println("");
		System.out.printf("%s %n[y] Yes [n] No%n", action);
		return scanner.scanNextLineOfTypeString(new String[]{"y","n"});
	}

	/**
	 * Prints an error message
	 * @param e the error message to print
	 */
	static void printError(String e) {
		System.out.println(e);
	}

	/**
	 * Asks which action the user wants to take
	 * @return int indicating the chosen action
	 * @param actions available actions for the user
	 * @throws CancelException when the user types 'Cancel'
	 */
	static int selectAction(UserGenerator generator, List<String> actions, String question) throws CancelException {
		DisplayStatus builder = new DisplayStatus();
		generator.selectAction(builder, actions, question);
		System.out.println(builder.getDisplay());
		return scanner.scanNextLineOfTypeInt(0, actions.size());
	}
}

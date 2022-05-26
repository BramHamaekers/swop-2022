package swop.UI;

import java.util.List;
import java.util.Scanner;

import swop.Exceptions.CancelException;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.UserGenerator;

/**
 * Interface used to make abstraction between the program and the user IO
 */
public interface UI {
	InputScanner scanner = new InputScanner(new Scanner(System.in));
	//todo: implement this?

	/**
	 * Ask user a Yes/No question and return the response
	 * @param action the question to ask
	 * @return "y" if yes, "n" if no
	 * @throws CancelException when the user types "Cancel"
	 */
	static String indicateYesNo(String action) throws CancelException {
		if (action == null) throw new IllegalArgumentException("null string provided");
		System.out.println();
		System.out.printf("%s %n[y] Yes [n] No%n", action);
		return scanner.scanNextLineOfTypeString(new String[]{"y","n"});
	}

	/**
	 * Prints an error message
	 * @param e the error message to print
	 */
	static void printError(String e) {
		if (e == null) throw new IllegalArgumentException("null string provided");
		System.out.println(e);
	}

	/**
	 * Prints an error message followed by a new line
	 * @param e the error message to print
	 */
	static void printErrorln(String e) {
		if (e == null) throw new IllegalArgumentException("null string provided");
		System.out.println(e);
	}

	/**
	 * Asks which action the user wants to take
	 * @return int indicating the chosen action
	 * @param actions available actions for the user
	 * @param generator the generator to specify what to build for the string
	 * @param question an initial question for which to display options
	 * @throws CancelException when the user types 'Cancel'
	 */
	static int selectAction(UserGenerator generator, List<String> actions, String question) throws CancelException {
		if (actions == null) throw new IllegalArgumentException("list of actions is null");
		if (question == null) throw new IllegalArgumentException("null string provided");
		DisplayStatus builder = new DisplayStatus();
		generator.selectAction(builder, actions, question);
		System.out.println(builder.getDisplay());
		return scanner.scanNextLineOfTypeInt(0, actions.size());
	}
}

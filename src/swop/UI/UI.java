package swop.UI;

import java.util.Scanner;

import swop.Exceptions.CancelException;

interface UI {
	InputScanner scanner = new InputScanner(new Scanner(System.in));

	/**
	 * Ask user a Yes/No question and return the response
	 * @param action the question to ask
	 * @return "y" if yes, "n" if no
	 * @throws CancelException when the user types "Cancel"
	 */
	static String indicateYesNo(String action) throws CancelException {
		System.out.printf("Do you want to %s? %n[y] Yes [n] No%n", action);
		return scanner.scanNextLineOfTypeString(new String[]{"y","n"});
	}

	/**
	 * Prints an error message
	 * @param e the error message to print
	 */
	static void printError(String e) {
		System.out.println();
		System.out.println(e);
	}
}

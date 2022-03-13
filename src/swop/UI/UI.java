package swop.UI;

import java.util.Scanner;

import swop.Exceptions.CancelException;

interface UI {
	Scanner inputScanner = new Scanner(System.in);
	static InputScanner scanner = new InputScanner();

	static String indicateYesNo(String action) throws CancelException {
		System.out.println();
		System.out.printf("Do you want to %s? %n[y] Yes [n] No%n", action);
		return scanner.scanNextLineOfTypeString(new String[]{"y","n"});
	}
}

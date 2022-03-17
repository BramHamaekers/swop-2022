package swop.UI;

import java.util.Scanner;

import swop.Exceptions.CancelException;

interface UI {
	static InputScanner scanner = new InputScanner(new Scanner(System.in));

	static String indicateYesNo(String action) throws CancelException {
		System.out.println();
		System.out.printf("Do you want to %s? %n[y] Yes [n] No%n", action);
		return scanner.scanNextLineOfTypeString(new String[]{"y","n"});
	}
}

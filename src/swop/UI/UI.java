package swop.UI;

import java.util.Scanner;

interface UI {
	Scanner inputScanner = new Scanner(System.in);
	static InputScanner scanner = new InputScanner();

	static String indicateYesNo(String action) {
		System.out.println();
		System.out.printf("Do you want to %s? %n[y] Yes [n] No%n", action);
		return inputScanner.nextLine();
	}
}

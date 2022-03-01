package swop.UI;

import java.util.Scanner;

public final class LoginUI implements UI{

	private static Scanner inputScanner;

	public static void init() {
		System.out.println("Welcome!");
		inputScanner = new Scanner(System.in);
	}

	public static String getUserID() {
		System.out.print("Please login with userID: ");
		return inputScanner.nextLine(); // returns id
	}
}

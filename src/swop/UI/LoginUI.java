package swop.UI;

import swop.Exceptions.CancelException;

public final class LoginUI implements UI{


	public static void init() {
		System.out.println("Welcome!");
	}

	public static String getUserID() {
		System.out.print("Please login with userID, type QUIT to exit: ");
		try {
			return scanner.scanNextLineOfTypeString();
		} catch (CancelException e) {
			return "";
		} 
	}
}

package swop.UI;

import swop.Exceptions.CancelException;

/**
 * UI class used to implement the login
 */
public final class LoginUI implements UI{

	/**
	 * Greets the user with a welcome message
	 */
	public static void init() {
		System.out.println("Welcome!");
	}

	/**
	 * Gets the user id from the input
	 * @return return the userID or an empty string if {@code CancelException}
	 */
	public static String getUserID() {
		System.out.print("Please login with userID, type QUIT to exit: ");
		try {
			return scanner.scanNextLineOfTypeString();
		} catch (CancelException e) {
			return "";
		} 
	}
}

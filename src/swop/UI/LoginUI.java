package swop.UI;


public final class LoginUI implements UI{


	public static void init() {
		System.out.println("Welcome!");
	}

	public static String getUserID() {
		System.out.print("Please login with userID, type QUIT to exit: ");
		return inputScanner.nextLine(); // returns id
	}
}

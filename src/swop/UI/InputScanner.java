package swop.UI;

import java.util.Scanner;

import swop.Exceptions.CancelException;

/**
 * A class used to scan and get the user input
 */
public class InputScanner {
	/**
	 * The scanner used for scanning input by a user
	 */
	Scanner inputScanner;

	/**
	 * initializes the inputscanner
	 * @param inputscanner a {@code Scanner}
	 */
	public InputScanner(Scanner inputscanner) {
		if (inputscanner == null)
			throw new IllegalArgumentException("invalid inputscanner");
		this.inputScanner = inputscanner;
	}
	/**
	 * Will scan next line for an integer, keeps asking for input when given string does not meet requirements.
	 * @return valid string as int
	 * @throws CancelException when the user types 'Cancel'
	 */
	public int scanNextLineOfTypeInt() throws CancelException {
		String s;
		if (inputScanner.hasNextLine()) s = this.inputScanner.nextLine();
		else s = this.inputScanner.next();
		if (s.equalsIgnoreCase("cancel")) throw new CancelException();
		try {
			return Integer.parseInt(s);
		} catch(Exception e) {
			System.out.println("Please give valid input");
			return scanNextLineOfTypeInt();
		}
		 
		
	}
	/**
	 * Will scan next line for an integer, keeps asking for input when given string does not meet requirements.
	 * @param leftborder the lowest integer possible
	 * @param rightBorder the highest integer
	 * @return valid string as int
	 * @throws CancelException when the user types 'Cancel'
	 */
	public int scanNextLineOfTypeInt(int leftborder, int rightBorder) throws CancelException {
		if (leftborder<0 || rightBorder<=leftborder)
			throw new IllegalArgumentException("invalid border range");
		String s;
		if (inputScanner.hasNextLine()) s = this.inputScanner.nextLine();
		else s = this.inputScanner.next();
		if (s.equalsIgnoreCase("CANCEL")) throw new CancelException();
		try {
			int number = Integer.parseInt(s);
			while (number < leftborder || number >= rightBorder) {
				System.out.print("Please give valid input:");
				s = this.inputScanner.nextLine();
				number = Integer.parseInt(s);
			}
			return number;
		} catch(Exception e) {
			System.out.print("Please give valid input:");
			return scanNextLineOfTypeInt(leftborder, rightBorder);
		}
		
	}
	/**
	 * Will scan next line for a string, keeps asking for input when given string does not meet requirements.
	 * @return a valid string
	 * @throws CancelException when a user wants to cancel his operation by typing "cancel"
	 */
	public String scanNextLineOfTypeString() throws CancelException {
		String s;
		if (inputScanner.hasNextLine()) s = this.inputScanner.nextLine();
		else s = this.inputScanner.next();
		if (s.equalsIgnoreCase("CANCEL")) throw new CancelException();
		return s;
	}
	/**
	 * Will scan next line for a string, keeps asking for input when given string does not meet requirements.
	 * @param strings array of Strings that input may be equal to.
	 * @return a valid string
	 * @throws CancelException when a user wants to cancel his operation by typing "cancel"
	 */
	public String scanNextLineOfTypeString(String[] strings) throws CancelException {
		if (strings == null)
			throw new IllegalArgumentException("null stringlist provided");
		String s;
		if (inputScanner.hasNextLine()) s = this.inputScanner.nextLine();
		else s = this.inputScanner.next();
		if (s.equalsIgnoreCase("CANCEL")) throw new CancelException();
		if(strings == null) {
			System.out.println("Invalid Array");//throw error?
			return null;
		}
		for(String str : strings) {
			if(str.equals(s)) return s;
		}
		System.out.print("Please give valid input:");
		return scanNextLineOfTypeString(strings);
		
	}

	/**
	 * Assigns a new input to the inputScanner
	 */
	public void updateScanner() {	
		this.inputScanner = new Scanner(System.in);
	}
}

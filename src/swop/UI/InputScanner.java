package swop.UI;

import java.util.Scanner;

public class InputScanner {
	Scanner inputScanner = new Scanner(System.in);
	
	/**
	 * Will scan next line for an integer, keeps asking for input when given string does not meet requirements.
	 * @return valid string as int
	 */
	public int scanNextLineOfTypeInt() {
		String s = this.inputScanner.nextLine();
		try {
			int number = Integer.parseInt(s);
			return number;
		} catch(Exception e) {
			System.out.println("Please give valid input");
			return scanNextLineOfTypeInt();
		}
		 
		
	}
	/**
	 * Will scan next line for an integer, keeps asking for input when given string does not meet requirements.
	 * @param leftborder
	 * @param rightBorder
	 * @return valid string as int
	 */
	public int scanNextLineOfTypeInt(int leftborder, int rightBorder) {
		String s = this.inputScanner.nextLine();
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
	 * @return valid string
	 */
	public String scanNextLineOfTypeString() {
		String s = this.inputScanner.nextLine();
		return s;
	}
	/**
	 * Will scan next line for a string, keeps asking for input when given string does not meet requirements.
	 * @param strings array[] of Strings that input may be equal to.
	 * @return valid string
	 */
	public String scanNextLineOfTypeString(String[] strings) {
		//TODO Deze functie implementeren.
		String s = this.inputScanner.nextLine();
		return s;
		
	}
}

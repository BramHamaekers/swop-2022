package swop.UI;

import java.util.Scanner;

import swop.Exceptions.CancelException;

public class InputScanner {
	Scanner inputScanner = new Scanner(System.in);
	
	/**
	 * Will scan next line for an integer, keeps asking for input when given string does not meet requirements.
	 * @return valid string as int
	 * @throws CancelException 
	 */
	public int scanNextLineOfTypeInt() throws CancelException {
		String s = this.inputScanner.nextLine();
		if (s.equals("cancel")) throw new CancelException();
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
	 * @throws CancelException 
	 */
	public int scanNextLineOfTypeInt(int leftborder, int rightBorder) throws CancelException {
		String s = this.inputScanner.nextLine();
		if (s.equals("cancel")) throw new CancelException();
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
	 * @throws CancelException 
	 */
	public String scanNextLineOfTypeString() throws CancelException {
		String s = this.inputScanner.nextLine();
		if (s.equals("cancel")) throw new CancelException();
		return s;
	}
	/**
	 * Will scan next line for a string, keeps asking for input when given string does not meet requirements.
	 * @param strings array[] of Strings that input may be equal to.
	 * @return valid string
	 * @throws CancelException 
	 */
	public String scanNextLineOfTypeString(String[] strings) throws CancelException {
		String s = this.inputScanner.nextLine();
		if (s.equals("cancel")) throw new CancelException();
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
}

package swop.Exceptions;

/**
 * A CancelException occurs when a user wants to cancel what he is doing
 */
public class CancelException extends Exception{
	/**
	 * The default message after a CancelException has occurred
	 */
	public void printMessage() {
		System.out.println("CANCELLED");
	}
}

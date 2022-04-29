package swop.Exceptions;

/**
 * When a user tries to access a method for another user
 */
public class IllegalUserException extends IllegalArgumentException{

	/**
	 * When a user tries to access a method for another user
	 * @param methodName the name of the method
	 */
	public IllegalUserException(String methodName) {
		super("User has no rights to use: " + methodName);
	}
}

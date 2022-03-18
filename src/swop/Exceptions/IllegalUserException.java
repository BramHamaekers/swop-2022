package swop.Exceptions;

public class IllegalUserException extends IllegalArgumentException{
	
	
	public IllegalUserException(String methodName) {
		super("User has no rights to use: " + methodName);
	}
}

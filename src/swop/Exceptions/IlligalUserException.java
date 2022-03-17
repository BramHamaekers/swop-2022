package swop.Exceptions;

public class IlligalUserException extends IllegalArgumentException{
	
	
	public IlligalUserException(String methodName) {
		super("User has no rights to use: " + methodName);
	}
}

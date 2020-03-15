package customExceptions;

public class IsNotRegisteredException extends Exception{

	public IsNotRegisteredException() {
		super("The user is not registered, please add him at menu.");	
		}
	
	@Override
	public String getMessage() {
		String msg;
		
		msg=super.getMessage();
		
		return msg;
	}
	
}

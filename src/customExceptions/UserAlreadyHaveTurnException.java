package customExceptions;

public class UserAlreadyHaveTurnException extends Exception{

	private String user;

	public UserAlreadyHaveTurnException(String name,String turn) {
		super("Turn is active.");

		user="The user  "+name+"already have a active turn, is |"+turn+"|";
		
	}
	
	@Override
	public String getMessage() {
		String msg;
		msg = super.getMessage() +user ;
		return msg;
	}
	
	
}

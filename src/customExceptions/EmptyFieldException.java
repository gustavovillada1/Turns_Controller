package customExceptions;

public class EmptyFieldException extends Exception {
	

	
	private String empty;
	private String wichIsEmpty;
	
	public EmptyFieldException(String n,String ln,String d,long id,long phone) {
		super("There are fields empty: ");

		
		detectedFieldEmpty(n,ln,d,id,phone);
		
	}
	
	/**
	 * This method is responsible for detecting which fields have been empty, in order to inform the user.
	 * @param n name
	 * @param ln lastName
	 * @param d direction
	 * @param id identification
	 * @param phone phone
	 */
	public void detectedFieldEmpty(String n,String ln,String d,long id,long phone) {
		empty="|";
		
		if(n.isEmpty()) {
			empty=empty+" Name |";
			}
		if(ln.isEmpty()) {
			empty=empty+" LastName |";
			}
		if(d.isEmpty()) {
			empty=empty+" Direction |";
			}
		wichIsEmpty="Sorry, fields are empty: "+empty;		
	}
	

	
	@Override
	public String getMessage() {
		String msg;
		msg = super.getMessage() + wichIsEmpty;
		return msg;
	}
	
	
	

}

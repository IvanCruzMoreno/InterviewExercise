package oracle.interview.exceptions;


public class RetryException extends Exception{

	private static final long serialVersionUID = -1539336438557592380L;
	private String message;
	
	public RetryException(String name, int tries) {
		this.message = "Retry Failed [" + name +"] tries: " + tries;
	}
	
	public String getErrorMessage() {
		return "RetryException [message=" + message + "]";
	}
	
	
}

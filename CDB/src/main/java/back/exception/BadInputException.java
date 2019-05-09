package back.exception;

public class BadInputException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public BadInputException(String message) {
		super(message);
	}

}

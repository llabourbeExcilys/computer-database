package exception;

public class BadCompanyIdException extends RuntimeException{
	public BadCompanyIdException(String message) {
		super(message);
	}
}

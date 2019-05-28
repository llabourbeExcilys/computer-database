package com.excilys.cdb.core.exception;

public class BadCompanyIdException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public BadCompanyIdException(String message) {
		super(message);
	}
}

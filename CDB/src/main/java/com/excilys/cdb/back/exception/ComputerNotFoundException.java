package com.excilys.cdb.back.exception;

public class ComputerNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ComputerNotFoundException(String message) {
		super(message);
	}

}

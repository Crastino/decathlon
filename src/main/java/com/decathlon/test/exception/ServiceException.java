package com.decathlon.test.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		
	}
	
	public ServiceException(String message, Throwable cause) {
		
		super(message, cause);
	}
	
	public ServiceException(String message) {
		
		super(message);
	}
}

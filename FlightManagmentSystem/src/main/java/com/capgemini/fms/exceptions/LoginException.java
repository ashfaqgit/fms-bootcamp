package com.capgemini.fms.exceptions;

@SuppressWarnings("serial")
public class LoginException extends Exception {
	
	public LoginException() {
		
		super();
	}
	
	public LoginException(String message) {
		
		super(message);
	}

}
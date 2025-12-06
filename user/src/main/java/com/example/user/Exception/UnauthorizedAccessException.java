package com.example.user.Exception;

public class UnauthorizedAccessException extends RuntimeException {
	private static final long serialVersionUID = 1L;	//Unique ID for serialization compatibility
	
	public UnauthorizedAccessException (String message) {
		super (message);
	}
}

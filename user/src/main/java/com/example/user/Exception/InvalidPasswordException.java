package com.example.user.Exception;

public class InvalidPasswordException extends RuntimeException {
	private static final long serialVersionUID = 1L;	//Unique ID for serialization compatibility

	public InvalidPasswordException (String message) {
		super (message);
	}
}
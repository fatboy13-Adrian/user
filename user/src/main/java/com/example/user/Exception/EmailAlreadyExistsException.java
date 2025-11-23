package com.example.user.Exception;

/** Custom exception thrown when an email already exists */
public class EmailAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;	//Unique ID for serialization compatibility

	public EmailAlreadyExistsException (String email) {
		super ("Email address" + email + "already exists in the system");
	}
}
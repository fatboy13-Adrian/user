package com.example.user.Exception;

public class UsernameAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;	//Unique ID for serialization compatibility

	public UsernameAlreadyExistsException (String username) {
		super ("Username " + username + "already exists in the system");
	}
}
package com.example.user.Exception;

public class EmailNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;	//Unique ID for serialization compatibility

	public EmailNotFoundException (String email) {
		super ("Email address" + email + "does not exists in the system");
	}
}

package com.example.user.Exception;

public class UsernameNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;	//Unique ID for serialization compatibility
	
	public UsernameNotFoundException (String username) {
		super ("Username " + username + "does not exists in the system");
	}
}
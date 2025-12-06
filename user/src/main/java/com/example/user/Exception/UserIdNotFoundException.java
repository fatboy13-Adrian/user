package com.example.user.Exception;

public class UserIdNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;	//Unique ID for serialization compatibility
	
	public UserIdNotFoundException (Long userId) {
		super ("User ID " + userId + "not found in DB!");
	}
}
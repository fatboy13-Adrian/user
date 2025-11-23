package com.example.user.Exception;

public class MobileNumberAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;	//Unique ID for serialization compatibility
	
	public MobileNumberAlreadyExistsException (String mobileNo) {
		super ("Mobile number " + mobileNo + "already exists in the system");
	}
}
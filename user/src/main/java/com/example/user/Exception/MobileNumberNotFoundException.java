package com.example.user.Exception;

public class MobileNumberNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;	//Unique ID for serialization compatibility

	public MobileNumberNotFoundException (String mobileNo) {
		super ("Mobile number " + mobileNo + "does not exists in the system");
	}
}
package com.example.user.SecurityUtil;
import org.springframework.security.core.Authentication;

public class SecurityUtil {
	//Get the logged in user ID
	public static Long getLoggedInUserId(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;	//Return null if logged in user ID is not authenticated or authentication is null
		}
		
		return null;
	}
	
	//Get the logged in user role
	public static String getLoggedInUserRole(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;	//Return null if role is not authenticated or authentication is null
		}
		
		//Iterates oer the authorities and returns the 1st authority
		for (int i = 0; i <= authentication.getAuthorities().size();) {
			return authentication.getAuthorities().toArray()[i].toString();	//Return 1st authority
		}
		
		return null;
	}
}
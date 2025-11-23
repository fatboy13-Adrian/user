package com.example.user.Entity;

public class ForgotLoginCredential {
	//User attributes
	private String email, username, password;

	//No argument constructor
	public ForgotLoginCredential() {}

	//Constructor with all fields
	public ForgotLoginCredential(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}

	//Getters
	public String getEmail() {
		return email;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword () {
		return password;
	}

	//Setters
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword (String password) {
		this.password = password;
	}
}

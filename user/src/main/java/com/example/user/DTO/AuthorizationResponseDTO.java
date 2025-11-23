package com.example.user.DTO;

public class AuthorizationResponseDTO {
	//User attributes
	private String username, password;

	//No argument constructor
	public AuthorizationResponseDTO() {}

	//Constructor with all fields
	public AuthorizationResponseDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	//Getters
	public String getUsername() {
		return username;
	}

	public String getPassword () {
		return password;
	}

	//Setters
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword (String password) {
		this.password = password;
	}
}
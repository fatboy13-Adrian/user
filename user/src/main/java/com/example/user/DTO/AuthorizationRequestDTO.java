package com.example.user.DTO;

public class AuthorizationRequestDTO {
	//Username and password for authentication
	private String username, password;

	//No argument constructor
	public AuthorizationRequestDTO() {}

	//Constructor with all fields
	public AuthorizationRequestDTO(String username, String password) {
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
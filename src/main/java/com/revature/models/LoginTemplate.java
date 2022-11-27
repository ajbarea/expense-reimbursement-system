package com.revature.models;

// Convert HTTP login requests into Java objects
public class LoginTemplate {
	
	String username;
	String password;
	
	// Constructors from superclass
	public LoginTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// Constructor using all fields
	public LoginTemplate(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	// Getters and Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// toString()
	@Override
	public String toString() {
		return "LoginTemplate [username=" + username + ", password=" + password + "]";
	}
}
package com.sosquestion.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {
	
	private String userID;
	private String password;
	private String email;
	
	public User() {
	}
		 
	public User(String userID, String password, String email) {
		super();
		this.userID = userID;
		this.password = password;
		this.email = email;
	}
	
	public User(String userID, String email) {
		super();
		this.userID = userID;
		this.email = email;
	}

	@XmlElement(name = "userID")
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}


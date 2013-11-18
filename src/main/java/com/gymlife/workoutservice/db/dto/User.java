package com.gymlife.workoutservice.db.dto;

public class User {
	
	int id;
	long joined;
	String userName;
	String firstName;
	String lastName;
	String email;
	
	public User(){}

	public int getId() {
		return id;
	}

	public long getJoined() {
		return joined;
	}

	public String getUserName() {
		return userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setJoined(long joined) {
		this.joined = joined;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

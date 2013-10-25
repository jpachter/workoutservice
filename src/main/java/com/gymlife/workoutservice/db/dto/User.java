package com.gymlife.workoutservice.db.dto;

public class User {
	
	int id;
	String routines;
	String username;
	String email;
	long salt;
	String hash;
	
	public User(){}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRoutines() {
		return routines;
	}
	
	public void setRoutines(String routines) {
		this.routines = routines;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public long getSalt() {
		return salt;
	}
	
	public void setSalt(long salt) {
		this.salt = salt;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}

}

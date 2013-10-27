package com.gymlife.workoutservice.db.dto;

public class User {
	
	int id;
	Routines routines;
	String username;
	String email;
	
	public User(){}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Routines getRoutines() {
		return routines;
	}
	
	public void setRoutines(Routines routines) {
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
}

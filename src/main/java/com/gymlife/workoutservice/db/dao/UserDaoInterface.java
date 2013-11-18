package com.gymlife.workoutservice.db.dao;

import java.sql.SQLException;

import com.gymlife.workoutservice.db.dto.User;

public interface UserDaoInterface {

	public boolean signIn(String username, String password);
	public boolean register(String username, String password, 
			                String firstName, String lastName, String email) throws SQLException;
	public User getById(String id) throws SQLException;
	public User getByUsername(String username) throws SQLException;
}

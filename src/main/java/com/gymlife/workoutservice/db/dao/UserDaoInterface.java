package com.gymlife.workoutservice.db.dao;

import java.sql.SQLException;

import com.gymlife.workoutservice.db.dto.User;

public interface UserDaoInterface {

	public boolean signIn(String username, String password) throws SQLException;
	public boolean register(String username, String password, String email) throws SQLException;
	User getById(String id) throws SQLException;
	User getByUsername(String username) throws SQLException;
}

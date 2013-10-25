package com.gymlife.workoutservice.db.dao;

import java.sql.SQLException;

public interface UserDaoInterface {

	public boolean signIn(String username, String password) throws SQLException;
	public boolean register(String username, String password, String email) throws SQLException;
}

package com.gymlife.workoutservice.db.dao;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gymlife.workoutservice.db.util.ConnectionFactory;
import com.gymlife.workoutservice.db.util.DBUtil;
import com.gymlife.workoutservice.db.util.SecureRandomFactory;
import com.gymlife.workoutservice.db.util.SecurityUtil;

public class UserDaoImp implements UserDaoInterface{

	private Connection connection;
	private PreparedStatement getSaltAndPass;
	private PreparedStatement registerUser;
	private PreparedStatement userExists;


	
	@Override
	public boolean signIn(String username, String password) throws SQLException {
		ResultSet result = null;
		try{
			connection = ConnectionFactory.getConnection();
			getSaltAndPass = connection.prepareStatement("select salt, hash from user where username = ?");
			getSaltAndPass.setString(1, username);
			result = getSaltAndPass.executeQuery();
			if(!result.next())
				return false;
			
			String salt = result.getString("salt");
			String realHash = result.getString("hash");
			String userHash = SecurityUtil.getHash(salt, password);
			
			return realHash.equals(userHash) ? true : false;
			
		} finally{
			DBUtil.close(result);
			DBUtil.close(getSaltAndPass);
			DBUtil.close(connection);
		}
	}

	@Override
	public boolean register(String username, String password, String email) throws SQLException {
		ResultSet result = null;
		try{
			connection = ConnectionFactory.getConnection();
			userExists = connection.prepareStatement("select COUNT(*) AS count from user where username = ?");
			userExists.setString(1, username);
			result = userExists.executeQuery();
			
			if(!result.next())
				return false;
			
			if(result.getInt("count") != 0)
				return false;
			
			SecureRandom rand = SecureRandomFactory.getRandom();
		    String salt = new Integer(rand.nextInt()).toString();
			String hash = SecurityUtil.getHash(salt, password);
			
			registerUser = connection.prepareStatement("insert into user (username, email, salt, hash) values (?,?,?,?)");
			registerUser.setString(1, username);
			registerUser.setString(2, email);
			registerUser.setString(3, salt);
			registerUser.setString(4, hash);
			registerUser.executeUpdate();
			
			return true;
			
		} finally{
			DBUtil.close(result);
			DBUtil.close(getSaltAndPass);
			DBUtil.close(connection);
		}
		
	}

}

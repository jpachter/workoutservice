package com.gymlife.workoutservice.db.dao;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gymlife.workoutservice.db.dto.User;
import com.gymlife.workoutservice.db.util.ConnectionFactory;
import com.gymlife.workoutservice.db.util.DBUtil;
import com.gymlife.workoutservice.db.util.SecureRandomFactory;
import com.gymlife.workoutservice.db.util.SecurityUtil;

public class UserDaoImp implements UserDaoInterface{

	private Connection connection;

	@Override
	public User getById(String id){
		PreparedStatement getUserById  = null;
		User u = null;
		ResultSet result = null;
		
		try{
			connection = ConnectionFactory.getConnection();
			getUserById = connection.prepareStatement("select * from Users where UserID = ?");
			getUserById.setString(1, id);
			result = getUserById.executeQuery();
			if(!result.next())
				return null;
			
			u = new User();
			u.setEmail(result.getString("Email"));
			u.setUserName(result.getString("UserName"));
			u.setId(result.getInt("UserID"));
			u.setFirstName(result.getString("FirstName"));
			u.setLastName(result.getString("LastName"));
		} 
		catch(SQLException e){
			System.out.println("SQLException error on getByID.");
		}			
		finally{
			DBUtil.close(result);
			DBUtil.close(getUserById);
			DBUtil.close(connection);
		}
		return u;
	}
	
	@Override
	public User getByUsername(String username){
		PreparedStatement getUserByName = null;
		User u = null;
		ResultSet result = null;
		
		try{
			connection = ConnectionFactory.getConnection();
			getUserByName = connection.prepareStatement("select * from Users where UserName = ?");
			getUserByName.setString(1, username);
			result = getUserByName.executeQuery();
			if(!result.next())
				return null;
			
			u = new User();
			u.setEmail(result.getString("Email"));
			u.setUserName(result.getString("UserName"));
			u.setId(result.getInt("UserID"));
			u.setFirstName(result.getString("FirstName"));
			u.setLastName(result.getString("LastName"));			
		} 	
		catch(SQLException e){
			System.out.println("SQLException error on getByUsername.");
		}	
		finally{
			DBUtil.close(result);
			DBUtil.close(getUserByName);
			DBUtil.close(connection);
		}
		return u;
	}
	
	@Override
	public boolean signIn(String username, String password){
		PreparedStatement getSaltAndPass = null;
		ResultSet result = null;
		String salt = null;
		String hash = null;
		String inputHash = null;
		
		try{
			connection = ConnectionFactory.getConnection();
			getSaltAndPass = connection.prepareStatement("select Salt, Hash from Users where UserName = ?");
			getSaltAndPass.setString(1, username);
			result = getSaltAndPass.executeQuery();
			if(!result.next())
				return false;
			
			salt = result.getString("Salt");
			hash = result.getString("Hash");
			inputHash = SecurityUtil.getHash(salt, password);
		} 
		catch(SQLException e){
			System.out.println("SQLException error on signIn.");
		}	
		finally{
			DBUtil.close(result);
			DBUtil.close(getSaltAndPass);
			DBUtil.close(connection);
		}
		return hash.equals(inputHash) ? true : false;
	}

	@Override
	public boolean register(String username, String password, 
                            String firstName, String lastName, String email){
		PreparedStatement userExists = null;
		PreparedStatement registerUser = null;
		ResultSet result = null;
		String salt = null;
		String hash = null;
		String inputHash = null;
		SecureRandom rand;
		
		try{
			connection = ConnectionFactory.getConnection();
			userExists = connection.prepareStatement("select COUNT(*) AS count from Users where UserName = ?");
			userExists.setString(1, username);
			result = userExists.executeQuery();
			
			if(!result.next() || (result.getInt("count") != 0))
				return false;
			
			rand = SecureRandomFactory.getRandom();
		    salt = new Integer(rand.nextInt()).toString();
			hash = SecurityUtil.getHash(salt, password);
			
			registerUser = connection.prepareStatement("insert into User (UserName, Email, Salt, Hash, FirstName, LastName) values (?,?,?,?,?,?)");
			registerUser.setString(1, username);
			registerUser.setString(2, email);
			registerUser.setString(3, salt);
			registerUser.setString(4, hash);
			registerUser.setString(5, firstName);
			registerUser.setString(6, lastName);
			registerUser.executeUpdate();
		}
		catch(SQLException e){
			System.out.println("SQLException error on register.");
		}	
		finally{
			DBUtil.close(result);
			DBUtil.close(userExists);
			DBUtil.close(registerUser);
			DBUtil.close(connection);
		}
		return true;
	}
}

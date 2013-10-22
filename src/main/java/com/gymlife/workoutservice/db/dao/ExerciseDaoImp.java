package com.gymlife.workoutservice.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.gymlife.workoutservice.db.dto.Exercise;
import com.gymlife.workoutservice.db.util.ConnectionFactory;
import com.gymlife.workoutservice.db.util.DBUtil;

public class ExerciseDaoImp {
	
	private Connection connection;
	private PreparedStatement loadAllStmt;
	private PreparedStatement loadByIdStmt;

	public ExerciseDaoImp() {}

	public Exercise getWorkout(int id) throws SQLException {
		Exercise e = new Exercise();
		ResultSet result = null;
		try{
			connection = ConnectionFactory.getConnection();
			loadByIdStmt = connection.prepareStatement("select * from exercise where id = ?");
			loadByIdStmt.setString(1, Integer.toString(id));
			result = loadByIdStmt.executeQuery();
			if(!result.next())
				return null;
			
			e.setId(result.getInt("id"));
			e.setName(result.getString("name"));
			e.setMuscleGroup(result.getString("muscle_group"));
			e.setPrimaryMuscle(result.getString("primary_muscle"));
			e.setSecondaryMuscles(result.getString("secondary_muscles"));
			e.setDifficulty(result.getString("difficulty"));
			e.setType(result.getString("type"));
		} finally{
			DBUtil.close(result);
			DBUtil.close(loadByIdStmt);
			DBUtil.close(connection);
		}
		
		return e;
	}

	public List<Exercise> getAllWorkouts() throws SQLException {
		List<Exercise> all = new ArrayList<Exercise>();
		ResultSet result = null;

		try{
			connection = ConnectionFactory.getConnection();
			loadAllStmt = connection.prepareStatement("select * from exercise");
			result = loadAllStmt.executeQuery();
			
			while (result.next()) {
				Exercise e = new Exercise();
				e.setId(result.getInt("id"));
				e.setName(result.getString("name"));
				e.setMuscleGroup(result.getString("muscle_group"));
				e.setPrimaryMuscle(result.getString("primary_muscle"));
				e.setSecondaryMuscles(result.getString("secondary_muscles"));
				e.setDifficulty(result.getString("difficulty"));
				e.setType(result.getString("type"));
				all.add(e);
			}
		} finally{
			DBUtil.close(result);
			DBUtil.close(loadAllStmt);
			DBUtil.close(connection);
		}
		
		return all;
	}
}
package com.gymlife.workoutservice.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gymlife.workoutservice.Difficulty;
import com.gymlife.workoutservice.MuscleGroup;
import com.gymlife.workoutservice.Type;
import com.gymlife.workoutservice.db.dto.Exercise;
import com.gymlife.workoutservice.db.dto.User;
import com.gymlife.workoutservice.db.util.ConnectionFactory;
import com.gymlife.workoutservice.db.util.DBUtil;

public class ExerciseDaoImp {
	
	private Connection connection;

	public ExerciseDaoImp() {}

	public Exercise getExercise(int exerciseID){
		PreparedStatement loadByIdStmt  = null;
		PreparedStatement loadMuscleGroup  = null;
		PreparedStatement loadPrimary  = null;
		PreparedStatement loadSecondary  = null;
		Exercise e = null;
		ResultSet result = null;
		ResultSet result2 = null;
		ResultSet result3 = null;
		ResultSet result4 = null;
		
		try{
			connection = ConnectionFactory.getConnection();
			loadByIdStmt = connection.prepareStatement("select * from Exercises where ExerciseID = ?");
			loadByIdStmt.setString(1, Integer.toString(exerciseID));
			result = loadByIdStmt.executeQuery();
			if(!result.next())
				return null;
			
			e = new Exercise();
			e.setId(result.getInt("ExerciseID"));
			e.setName(result.getString("Name"));
			e.setDifficulty(Difficulty.forValue(result.getString("Difficulty")));
			e.setType(Type.forValue(result.getString("Type")));
			
			loadMuscleGroup = connection.prepareStatement("SELECT MuscleGroup.Name " +
								"FROM Exercises " +
								"INNER JOIN ExerciseMuscleGroupRelation " +
								"ON Exercises.ExerciseID=ExerciseMuscleGroupRelation.ExerciseID " +
								"INNER JOIN MuscleGroup " +
								"ON MuscleGroup.MuscleGroupID=ExerciseMuscleGroupRelation.MuscleGroupID " +
								"WHERE Exercises.ExerciseID=?");
			loadMuscleGroup.setInt(1, exerciseID);
			result2 = loadMuscleGroup.executeQuery();

			while(result2.next()){
				e.addMuscleGroup(result2.getString("Name"));
			}
			
			loadPrimary = connection.prepareStatement("SELECT Muscles.Name " +
							"FROM Exercises " +
							"INNER JOIN ExercisePrimaryMuscleRelation " +
							"ON ExercisePrimaryMuscleRelation.ExerciseID=Exercises.ExerciseID " +
							"INNER JOIN Muscles " +
							"ON Muscles.MuscleID=ExercisePrimaryMuscleRelation.MuscleID " +
							"WHERE Exercises.ExerciseID=?");
			loadPrimary.setString(1, Integer.toString(exerciseID));
			result3 = loadPrimary.executeQuery();

			while(result3.next()){
				e.addPrimaryMuscle(result3.getString("Name"));
			}
			
			loadSecondary = connection.prepareStatement("SELECT Muscles.Name " +
							"FROM Exercises " +
							"INNER JOIN ExerciseSecondaryMuscleRelation " +
							"ON ExerciseSecondaryMuscleRelation.ExerciseID=Exercises.ExerciseID " +
							"INNER JOIN Muscles " +
							"ON Muscles.MuscleID=ExerciseSecondaryMuscleRelation.MuscleID " +
							"WHERE Exercises.ExerciseID=?;");
			loadSecondary.setString(1, Integer.toString(exerciseID));
			result4 = loadSecondary.executeQuery();
			while(result4.next()){
				e.addSecondaryMuscle(result4.getString("Name"));
			}
		}
		catch(SQLException ex){
			System.out.println("SQLException error on getExercise.");
		}		
		finally{
			DBUtil.close(result);
			DBUtil.close(result2);
			DBUtil.close(result3);
			DBUtil.close(result4);
			DBUtil.close(loadByIdStmt);
			DBUtil.close(loadPrimary);
			DBUtil.close(loadSecondary);
			DBUtil.close(loadMuscleGroup);
			DBUtil.close(connection);
		}
		return e;
	}

	public List<Exercise> getAllExercises(){
		List<Exercise> all = new ArrayList<Exercise>();
		PreparedStatement loadAll  = null;
		PreparedStatement loadMuscleGroup  = null;
		PreparedStatement loadPrimary  = null;
		PreparedStatement loadSecondary  = null;
		ResultSet result = null;
		ResultSet result2 = null;
		ResultSet result3 = null;
		ResultSet result4 = null;
		
		try{
			connection = ConnectionFactory.getConnection();
			loadAll = connection.prepareStatement("select * from Exercises");
			result = loadAll.executeQuery();

			while (result.next()) {
				Exercise e = new Exercise();

				e.setId(result.getInt("ExerciseID"));
				e.setName(result.getString("Name"));
				e.setDifficulty(Difficulty.forValue(result.getString("Difficulty")));
				e.setType(Type.forValue(result.getString("Type")));

				loadMuscleGroup = connection.prepareStatement("SELECT MuscleGroup.Name " +
						"FROM Exercises " +
						"INNER JOIN ExerciseMuscleGroupRelation " +
						"ON Exercises.ExerciseID=ExerciseMuscleGroupRelation.ExerciseID " +
						"INNER JOIN MuscleGroup " +
						"ON MuscleGroup.MuscleGroupID=ExerciseMuscleGroupRelation.MuscleGroupID " +
						"WHERE Exercises.ExerciseID=?");
				loadMuscleGroup.setInt(1, e.getId());
				result2 = loadMuscleGroup.executeQuery();

				while(result2.next()){
					e.addMuscleGroup(result2.getString("Name"));
				}

				loadPrimary = connection.prepareStatement("SELECT Muscles.Name " +
						"FROM Exercises " +
						"INNER JOIN ExercisePrimaryMuscleRelation " +
						"ON ExercisePrimaryMuscleRelation.ExerciseID=Exercises.ExerciseID " +
						"INNER JOIN Muscles " +
						"ON Muscles.MuscleID=ExercisePrimaryMuscleRelation.MuscleID " +
						"WHERE Exercises.ExerciseID=?");
				loadPrimary.setInt(1, e.getId());
				result3 = loadPrimary.executeQuery();

				while(result3.next()){
					e.addPrimaryMuscle(result3.getString("Name"));
				}

				loadSecondary = connection.prepareStatement("SELECT Muscles.Name " +
						"FROM Exercises " +
						"INNER JOIN ExerciseSecondaryMuscleRelation " +
						"ON ExerciseSecondaryMuscleRelation.ExerciseID=Exercises.ExerciseID " +
						"INNER JOIN Muscles " +
						"ON Muscles.MuscleID=ExerciseSecondaryMuscleRelation.MuscleID " +
						"WHERE Exercises.ExerciseID=?");
				loadSecondary.setInt(1, e.getId());
				result4 = loadSecondary.executeQuery();
				
				while(result4.next()){
					e.addSecondaryMuscle(result4.getString("Name"));
				}

				all.add(e);
			}
		} 
		catch(SQLException ex){
			System.out.println("SQLException error on getAllExercises.");
		}	
		finally{
			DBUtil.close(result);
			DBUtil.close(result2);
			DBUtil.close(result3);
			DBUtil.close(result4);
			DBUtil.close(loadMuscleGroup);
			DBUtil.close(loadPrimary);
			DBUtil.close(loadSecondary);
			DBUtil.close(loadAll);
			DBUtil.close(connection);
		}
		
		return all;
	}
}
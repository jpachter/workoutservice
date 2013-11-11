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
import com.gymlife.workoutservice.db.util.ConnectionFactory;
import com.gymlife.workoutservice.db.util.DBUtil;

public class ExerciseDaoImp {
	
	private Connection connection;
	private PreparedStatement loadAllStmt;
	private PreparedStatement loadMuscleGroup;
	private PreparedStatement loadPrimary;
	private PreparedStatement loadSecondary;
	private PreparedStatement loadByIdStmt;

	public ExerciseDaoImp() {}

	public Exercise getExercise(int id) throws SQLException {
		Exercise e = new Exercise();
		ResultSet result = null;
		ResultSet result2 = null;
		ResultSet result3 = null;
		ResultSet result4 = null;
		try{
			connection = ConnectionFactory.getConnection();
			loadByIdStmt = connection.prepareStatement("select * from Exercises where ExerciseID = ?");
			loadByIdStmt.setString(1, Integer.toString(id));
			result = loadByIdStmt.executeQuery();
			if(!result.next())
				return null;
			
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
			loadMuscleGroup.setInt(1, id);
			result4 = loadMuscleGroup.executeQuery();

			while(result4.next()){
				e.addMuscleGroup(result4.getString("Name"));
			}
			
			loadPrimary = connection.prepareStatement("SELECT Muscles.Name " +
							"FROM Exercises " +
							"INNER JOIN ExercisePrimaryMuscleRelation " +
							"ON ExercisePrimaryMuscleRelation.ExerciseID=Exercises.ExerciseID " +
							"INNER JOIN Muscles " +
							"ON Muscles.MuscleID=ExercisePrimaryMuscleRelation.MuscleID " +
							"WHERE Exercises.ExerciseID=?");
			loadPrimary.setString(1, Integer.toString(id));
			result2 = loadByIdStmt.executeQuery();

			while(result2.next()){
				e.addPrimaryMuscle(result2.getString("Name"));
			}
			
			loadSecondary = connection.prepareStatement("SELECT Muscles.Name " +
							"FROM Exercises " +
							"INNER JOIN ExerciseSecondaryMuscleRelation " +
							"ON ExerciseSecondaryMuscleRelation.ExerciseID=Exercises.ExerciseID " +
							"INNER JOIN Muscles " +
							"ON Muscles.MuscleID=ExerciseSecondaryMuscleRelation.MuscleID " +
							"WHERE Exercises.ExerciseID=?;");
			loadSecondary.setString(1, Integer.toString(id));
			result3 = loadSecondary.executeQuery();
			while(result3.next()){
				e.addSecondaryMuscle(result3.getString("Name"));
			}
		} finally{
			DBUtil.close(result);
			DBUtil.close(loadByIdStmt);
			DBUtil.close(result2);
			DBUtil.close(loadPrimary);
			DBUtil.close(result3);
			DBUtil.close(loadSecondary);
			DBUtil.close(result4);
			DBUtil.close(connection);
		}
		return e;
	}

	public List<Exercise> getAllWorkouts() throws SQLException {
		List<Exercise> all = new ArrayList<Exercise>();
		ResultSet result = null;
		ResultSet result2 = null;
		ResultSet result3 = null;
		ResultSet result4 = null;
		
		try{
			connection = ConnectionFactory.getConnection();
			loadAllStmt = connection.prepareStatement("select * from Exercises");
			result = loadAllStmt.executeQuery();

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
				result4 = loadMuscleGroup.executeQuery();

				while(result4.next()){
					e.addMuscleGroup(result4.getString("Name"));
				}

				loadPrimary = connection.prepareStatement("SELECT Muscles.Name " +
						"FROM Exercises " +
						"INNER JOIN ExercisePrimaryMuscleRelation " +
						"ON ExercisePrimaryMuscleRelation.ExerciseID=Exercises.ExerciseID " +
						"INNER JOIN Muscles " +
						"ON Muscles.MuscleID=ExercisePrimaryMuscleRelation.MuscleID " +
						"WHERE Exercises.ExerciseID=?");
				loadPrimary.setInt(1, e.getId());
				result2 = loadPrimary.executeQuery();

				while(result2.next()){
					e.addPrimaryMuscle(result2.getString("Name"));
				}

				loadSecondary = connection.prepareStatement("SELECT Muscles.Name " +
						"FROM Exercises " +
						"INNER JOIN ExerciseSecondaryMuscleRelation " +
						"ON ExerciseSecondaryMuscleRelation.ExerciseID=Exercises.ExerciseID " +
						"INNER JOIN Muscles " +
						"ON Muscles.MuscleID=ExerciseSecondaryMuscleRelation.MuscleID " +
						"WHERE Exercises.ExerciseID=?");
				loadSecondary.setInt(1, e.getId());
				result3 = loadSecondary.executeQuery();
				
				while(result3.next()){
					e.addSecondaryMuscle(result3.getString("Name"));
				}

				all.add(e);
			}
		} finally{
			DBUtil.close(result);
			DBUtil.close(result2);
			DBUtil.close(result3);
			DBUtil.close(result4);
			DBUtil.close(loadAllStmt);
			DBUtil.close(connection);
		}
		
		return all;
	}
}
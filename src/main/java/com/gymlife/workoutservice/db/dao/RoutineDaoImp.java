package com.gymlife.workoutservice.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gymlife.workoutservice.Day;
import com.gymlife.workoutservice.Difficulty;
import com.gymlife.workoutservice.Type;
import com.gymlife.workoutservice.db.dto.CompletedWorkout;
import com.gymlife.workoutservice.db.dto.Exercise;
import com.gymlife.workoutservice.db.dto.ExerciseMetrics;
import com.gymlife.workoutservice.db.dto.FullExercise;
import com.gymlife.workoutservice.db.dto.Routine;
import com.gymlife.workoutservice.db.dto.Routines;
import com.gymlife.workoutservice.db.dto.Workout;
import com.gymlife.workoutservice.db.util.ConnectionFactory;
import com.gymlife.workoutservice.db.util.DBUtil;

public class RoutineDaoImp implements RoutineDaoInterface {

	private Connection connection;
	private PreparedStatement loadAllStmt;
	private PreparedStatement loadByIdStmt;
	private PreparedStatement loadByUserIdStmt;
	private ExerciseDaoImp exerciseDao;

	public RoutineDaoImp() {
		exerciseDao = new ExerciseDaoImp();
	}

	@Override
	public Routines getRoutines() throws SQLException {
		Routines routines = new Routines();
		List<Routine> routineList = new ArrayList<Routine>();
		ResultSet result = null;

		try {
			connection = ConnectionFactory.getConnection();
			loadAllStmt = connection.prepareStatement("select * from routine");
			result = loadAllStmt.executeQuery();
			while (result.next()) {
				Routine r = new Routine();
				//r.setId(result.getInt("id"));

				String[] exerciseIds = result.getString("exercises").split(",");
				List<Exercise> exers = new ArrayList<Exercise>();
				for (int i = 0; i < exerciseIds.length; i++) {
					exers.add(exerciseDao.getExercise(Integer
							.parseInt(exerciseIds[i])));
				}

				//r.setExercises(exers);
				//r.setDifficulty(Difficulty.forValue(result.getInt("difficulty")));
				//r.setNumDays(result.getInt("num_days"));
				//r.setName(result.getString("name"));
				routineList.add(r);
			}
		} finally {
			DBUtil.close(result);
			DBUtil.close(loadAllStmt);
			DBUtil.close(connection);
		}
		
		routines.setRoutines(routineList);
		return routines;
	}

	@Override
	public Routine getRoutineByUser(int userID) throws SQLException {
		Routine r = new Routine();
		ResultSet result = null;

		try {
			connection = ConnectionFactory.getConnection();
			loadByUserIdStmt = connection.prepareStatement("SELECT Exercises.ExerciseID, Routines.RoutineID, Routines.Name, Exercises.Name as ExerciseName, "
					+ "RoutineExerciseRelation.SetsGoal, RoutineExerciseRelation.RepsGoal, RoutineExerciseRelation.WeightGoal, "
					+ "RoutineExerciseRelation.SetsActual, RoutineExerciseRelation.RepsActual, RoutineExerciseRelation.WeightActual, "
					+ "Exercises.Difficulty, Exercises.Type, RoutineExerciseRelation.ScheduledDay, RoutineExerciseRelation.CompletedTime "
					+ "FROM Routines "
					+ "INNER JOIN RoutineExerciseRelation "
					+ "ON Routines.RoutineID=RoutineExerciseRelation.RoutineID "
					+ "INNER JOIN Users "
					+ "ON Users.UserID=RoutineExerciseRelation.UserID "
					+ "INNER JOIN Exercises "
					+ "ON Exercises.ExerciseID=RoutineExerciseRelation.ExerciseID "
					+ "WHERE Users.UserID=?");
					
			loadByUserIdStmt.setInt(1,  userID);
			result = loadByUserIdStmt.executeQuery();
			while(result.next()){
				long completedTime = result.getLong("CompletedTime");
				
				FullExercise currentFull = new FullExercise();
				ExerciseMetrics goal = new ExerciseMetrics();
				ExerciseMetrics actual = new ExerciseMetrics();
				Exercise currEx = new Exercise();
				currEx.setId(result.getInt("ExerciseID"));
				currEx.setDifficulty(Difficulty.forValue(result.getString("Difficulty")));
				currEx.setName(result.getString("ExerciseName"));
				currEx.setType(Type.forValue(result.getString("Type")));
				
				goal.setNumReps(result.getInt("RepsGoal"));
				goal.setNumSets(result.getInt("SetsGoal"));
				goal.setWeight(result.getInt("WeightGoal"));
				actual.setNumReps(result.getInt("RepsActual"));
				actual.setNumSets(result.getInt("SetsActual"));
				actual.setWeight(result.getInt("WeightActual"));
				currentFull.setExercise(currEx);
				currentFull.setActual(actual);
				currentFull.setGoal(goal);
				
				if(completedTime != 0){
					CompletedWorkout findCompleted = r.workoutAtTime(completedTime);
					if (findCompleted != null){
						findCompleted.addExercise(currentFull);
					}
					else{
						CompletedWorkout cWork = new CompletedWorkout();
						cWork.setDate(result.getLong("CompletedTime"));
						cWork.setName(result.getString("Name"));
						cWork.setDay(Day.forValue(result.getString("ScheduledDay")));
						cWork.addExercise(currentFull);
						cWork.setId(result.getInt("RoutineID"));
						r.addCompleteWorkout(cWork);
					}
				}
				else{
					Workout newWork = new Workout();
					newWork.setName(result.getString("Name"));
					newWork.setDay(Day.forValue(result.getString("ScheduledDay")));
					newWork.addExercise(currentFull);
					newWork.setId(result.getInt("RoutineID"));

					r.addWorkout(newWork);

				}
			}
			

		} finally {
			DBUtil.close(result);
			DBUtil.close(loadByUserIdStmt);
			DBUtil.close(connection);
		}
		
		return r;
	}
	
	@Override
	public Routine getRoutine(int id) throws SQLException {
		Routine r = new Routine();
		ResultSet result = null;

		try {
			connection = ConnectionFactory.getConnection();
			loadByIdStmt = connection.prepareStatement("SELECT Exercises.ExerciseID, Routines.RoutineID, Routines.Name, "
							+ "Exercises.Name as ExerciseName, Exercises.Difficulty, Exercises.Type, "
							+ "RoutineExerciseRelation.SetsGoal, RoutineExerciseRelation.RepsGoal, "
							+ "RoutineExerciseRelation.ScheduledDay "
							+ "FROM Routines "
							+ "INNER JOIN RoutineExerciseRelation "
							+ "ON Routines.RoutineID=RoutineExerciseRelation.RoutineID "
							+ "INNER JOIN Users "
							+ "ON Users.UserID=RoutineExerciseRelation.UserID "
							+ "INNER JOIN Exercises "
							+ "ON Exercises.ExerciseID=RoutineExerciseRelation.ExerciseID "
							+ "WHERE Users.UserID=0 AND Routines.RoutineID=?");
					
			loadByIdStmt.setInt(1, id);
			result = loadByIdStmt.executeQuery();
			while(result.next()){				
				FullExercise currentFull = new FullExercise();
				ExerciseMetrics goal = new ExerciseMetrics();
				Exercise currEx = new Exercise();
				currEx.setId(result.getInt("ExerciseID"));
				currEx.setDifficulty(Difficulty.forValue(result.getString("Difficulty")));
				currEx.setName(result.getString("ExerciseName"));
				currEx.setType(Type.forValue(result.getString("Type")));
				goal.setNumReps(result.getInt("RepsGoal"));
				goal.setNumSets(result.getInt("SetsGoal"));

				currentFull.setExercise(currEx);
				currentFull.setGoal(goal);
				
				Workout newWork = new Workout();
				newWork.setName(result.getString("Name"));
				newWork.setDay(Day.forValue(result.getString("ScheduledDay")));
				newWork.addExercise(currentFull);
				newWork.setId(result.getInt("RoutineID"));

				r.addWorkout(newWork);
			}		
		} finally {
			DBUtil.close(result);
			DBUtil.close(loadByIdStmt);
			DBUtil.close(connection);
		}
		
		return r;
	}

	@Override
	public void addWorkout(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteWorkout(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRoutine(int id) {
		// TODO Auto-generated method stub

	}

}

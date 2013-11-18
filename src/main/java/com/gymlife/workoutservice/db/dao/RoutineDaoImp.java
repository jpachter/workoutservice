package com.gymlife.workoutservice.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gymlife.workoutservice.Day;
import com.gymlife.workoutservice.Difficulty;
import com.gymlife.workoutservice.db.dto.Exercise;
import com.gymlife.workoutservice.db.dto.ExerciseCreator;
import com.gymlife.workoutservice.db.dto.Routine;
import com.gymlife.workoutservice.db.dto.RoutineWeek;
import com.gymlife.workoutservice.db.dto.Workout;
import com.gymlife.workoutservice.db.util.ConnectionFactory;
import com.gymlife.workoutservice.db.util.DBUtil;

public class RoutineDaoImp implements RoutineDaoInterface {

	private Connection connection;
	private PreparedStatement loadByIdStmt;
	private PreparedStatement loadByUserIdStmt;
	private ExerciseDaoImp exerciseDao;

	public RoutineDaoImp() {
		exerciseDao = new ExerciseDaoImp();
	}

	@Override
	public List<Routine> getRoutines(){
		PreparedStatement loadRoutines  = null;
		ResultSet result = null;
		List<Routine> routines = new ArrayList<Routine>();
		Routine curRoutine;
		int lastRoutine = -1;
		RoutineWeek routineWeek;
		Workout workout;
		int numExercises = 0;
		int difficultyTotal = 0;
		
		try {
			connection = ConnectionFactory.getConnection();
			loadRoutines = connection.prepareStatement("SELECT Routines.RoutineID, Routines.Name, "
					+ "Routines.CreatorID, Routines.Rating, RoutineExerciseRelation.ExerciseID, "
					+ "RoutineExerciseRelation.SetsGoal, RoutineExerciseRelation.RepsGoal, "
					+ "RoutineExerciseRelation.ScheduledDay, RoutineExerciseRelation.CompletedTime, "
					+ "RoutineExerciseRelation.WeekNumber "
					+ "FROM Routines "
					+ "INNER JOIN RoutineExerciseRelation "
					+ "ON Routines.RoutineID=RoutineExerciseRelation.RoutineID "
					+ "WHERE UserID=0 "
					+ "ORDER BY Routines.RoutineID ASC, RoutineExerciseRelation.WeekNumber ASC, RoutineExerciseRelation.ScheduledDay ASC ");
			result = loadRoutines.executeQuery();
			
			while (result.next()) {
				int routineID = result.getInt("RoutineID");
				
				if(lastRoutine == routineID)
					curRoutine = routines.get(routines.size()-1);
				else{
					curRoutine = new Routine();
					routines.add(curRoutine);
					numExercises = 0;
					difficultyTotal = 0;
					lastRoutine = routineID;
					
					curRoutine.setId(result.getInt("RoutineID"));
					curRoutine.setName(result.getString("Name"));
					curRoutine.setCreatorId(result.getString("CreatorID"));
					curRoutine.setRating(result.getDouble("Rating"));
				}
				
				int weekNum = result.getInt("WeekNumber");
				String schedDay = result.getString("ScheduledDay");

				if(curRoutine.getRoutineWeeks().size() > 0 && 
				   curRoutine.getRoutineWeeks().last().getWeekNumber() == weekNum){
					routineWeek = curRoutine.getRoutineWeeks().last();
				}
				else{
					routineWeek = new RoutineWeek();
					routineWeek.setWeekNumber(weekNum);
					curRoutine.addRoutineWeek(routineWeek);
				}
				
				if(routineWeek.getWorkouts().size() > 0 &&
				   routineWeek.getWorkouts().last().getScheduledDay().equals(Day.forValue(schedDay))){
					workout = routineWeek.getWorkouts().last();
				}
				else{
					workout = new Workout();
					workout.setScheduledDay(Day.forValue(schedDay));
					routineWeek.addWorkout(workout);
				}
				
				ExerciseCreator exer = new ExerciseCreator(exerciseDao.getExercise(result.getInt("ExerciseID")));
				exer.setRepsGoal(result.getInt("RepsGoal"));
				exer.setSetsGoal(result.getInt("SetsGoal"));
				difficultyTotal += exer.getDifficulty().getRank();
				numExercises++;
				workout.addExercise(exer);
				workout.addMuscleGroups(exer.getMuscleGroups());
				routineWeek.addDayOfWeek(Day.forValue(result.getString("ScheduledDay")));
				curRoutine.setDifficulty(Difficulty.forRank((int)difficultyTotal/numExercises));

			}

			//Calculate ROUTINE difficulty and percentFinished

		}
		catch(SQLException e){
			System.out.println("SQLException error on getRoutines.");
		}	
		finally {
			DBUtil.close(result);
			DBUtil.close(loadRoutines);
			DBUtil.close(connection);
		}
		
		return routines;
	}
/*
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
*/
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

	@Override
	public Routine getCurrentRoutine(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Routine> getCompletedRoutines(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

}

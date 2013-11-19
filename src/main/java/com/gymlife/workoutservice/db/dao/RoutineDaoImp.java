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
import com.gymlife.workoutservice.db.dto.ExerciseUser;
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

	@Override
	public List<Routine> getCurrentRoutine(int userID){
		PreparedStatement loadRoutines  = null;
		ResultSet result = null;
		List<Routine> routines = new ArrayList<Routine>();
		Routine curRoutine = null;
		int lastRoutine = -1;
		RoutineWeek routineWeek;
		Workout workout;
		int numExercises = 0;
		int numCompletedExercises = 0;
		int difficultyTotal = 0;
		
		try {
			connection = ConnectionFactory.getConnection();
			loadRoutines = connection.prepareStatement("SELECT Routines.RoutineID, Routines.Name, "
					+ "Routines.CreatorID, Routines.Rating, RoutineExerciseRelation.ExerciseID, "
					+ "RoutineExerciseRelation.SetsGoal, RoutineExerciseRelation.RepsGoal,RoutineExerciseRelation.WeightGoal, "
					+ "RoutineExerciseRelation.SetsActual, RoutineExerciseRelation.RepsActual, RoutineExerciseRelation.WeightActual, "
					+ "RoutineExerciseRelation.ScheduledDay, RoutineExerciseRelation.CompletedTime, RoutineExerciseRelation.WeekNumber "
					+ "FROM Routines "
					+ "INNER JOIN RoutineExerciseRelation "
					+ "ON Routines.RoutineID=RoutineExerciseRelation.RoutineID "
					+ "WHERE UserID=? AND RoutineExerciseRelation.CompletedTime IS NULL OR WEEKOFYEAR(RoutineExerciseRelation.CompletedTime) = WEEKOFYEAR(NOW()) "
					+ "ORDER BY Routines.RoutineID ASC, RoutineExerciseRelation.WeekNumber ASC, RoutineExerciseRelation.ScheduledDay ASC ");
			loadRoutines.setInt(1, userID);
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
				
				ExerciseUser exer = new ExerciseUser(exerciseDao.getExercise(result.getInt("ExerciseID")));
				exer.setRepsGoal(result.getInt("RepsGoal"));
				exer.setSetsGoal(result.getInt("SetsGoal"));
				exer.setWeightGoal(result.getInt("WeightGoal"));
				exer.setRepsActual(result.getInt("RepsActual"));
				exer.setSetsActual(result.getInt("SetsActual"));
				exer.setWeightActual(result.getInt("WeightActual"));
		
				workout.addExercise(exer);
				
				if(result.getDate("CompletedTime") != null){
					workout.setTimeCompleted(result.getDate("CompletedTime"));
					numCompletedExercises++;
				}
				workout.addMuscleGroups(exer.getMuscleGroups());
				routineWeek.addDayOfWeek(Day.forValue(result.getString("ScheduledDay")));
				difficultyTotal += exer.getDifficulty().getRank();
				numExercises++;
			}
			
			curRoutine.setDifficulty(Difficulty.forRank((int)difficultyTotal/numExercises));
			curRoutine.setPercentFinished((double)numCompletedExercises/numExercises);

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

	
	@Override
	public List<Routine> getCompletedRoutines(int userID){		
		PreparedStatement loadRoutines  = null;
		ResultSet result = null;
		List<Routine> routines = new ArrayList<Routine>();
		Routine curRoutine = null;
		int lastRoutine = -1;
		RoutineWeek routineWeek;
		Workout workout;
		int numExercises = 0;
		int numCompletedExercises = 0;
		int difficultyTotal = 0;
		
		try {
			connection = ConnectionFactory.getConnection();
			loadRoutines = connection.prepareStatement("SELECT Routines.RoutineID, Routines.Name, "
					+ "Routines.CreatorID, Routines.Rating, RoutineExerciseRelation.ExerciseID, "
					+ "RoutineExerciseRelation.SetsGoal, RoutineExerciseRelation.RepsGoal,RoutineExerciseRelation.WeightGoal, "
					+ "RoutineExerciseRelation.SetsActual, RoutineExerciseRelation.RepsActual, RoutineExerciseRelation.WeightActual, "
					+ "RoutineExerciseRelation.ScheduledDay, RoutineExerciseRelation.CompletedTime, RoutineExerciseRelation.WeekNumber "
					+ "FROM Routines "
					+ "INNER JOIN RoutineExerciseRelation "
					+ "ON Routines.RoutineID=RoutineExerciseRelation.RoutineID "
					+ "WHERE UserID=? AND RoutineExerciseRelation.CompletedTime IS NOT NULL AND WEEKOFYEAR(RoutineExerciseRelation.CompletedTime) != WEEKOFYEAR(NOW()) "
					+ "ORDER BY Routines.RoutineID ASC, RoutineExerciseRelation.WeekNumber ASC, RoutineExerciseRelation.ScheduledDay ASC ");
			loadRoutines.setInt(1, userID);
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
				
				ExerciseUser exer = new ExerciseUser(exerciseDao.getExercise(result.getInt("ExerciseID")));
				exer.setRepsGoal(result.getInt("RepsGoal"));
				exer.setSetsGoal(result.getInt("SetsGoal"));
				exer.setWeightGoal(result.getInt("WeightGoal"));
				exer.setRepsActual(result.getInt("RepsActual"));
				exer.setSetsActual(result.getInt("SetsActual"));
				exer.setWeightActual(result.getInt("WeightActual"));
		
				workout.addExercise(exer);
				
				if(result.getDate("CompletedTime") != null){
					workout.setTimeCompleted(result.getDate("CompletedTime"));
					numCompletedExercises++;
				}
				workout.addMuscleGroups(exer.getMuscleGroups());
				routineWeek.addDayOfWeek(Day.forValue(result.getString("ScheduledDay")));
				difficultyTotal += exer.getDifficulty().getRank();
				numExercises++;
			}
			
			curRoutine.setDifficulty(Difficulty.forRank((int)difficultyTotal/numExercises));
			curRoutine.setPercentFinished((double)numCompletedExercises/numExercises);
	
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

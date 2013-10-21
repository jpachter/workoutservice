package com.gymlife.workoutservice.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoutineDaoImp implements RoutineDaoInterface {
	 
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private ExerciseDaoImp exerciseDao = new ExerciseDaoImp();

	
	public RoutineDaoImp(){
		  try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      try {
				//connect = DriverManager
				//          .getConnection("jdbc:mysql://127.0.0.1:3306/workout_db?"
				//              + "user=root&password=mypass");
		    	connect = DriverManager
							.getConnection("jdbc:mysql://stevie.heliohost.org:3306/jpachter_gymlife?"
							 + "user=jpachter&password=juke23gl");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
	}
	
	@Override
	public List<Routine> getRoutines() throws SQLException {
		  List<Routine> all = new ArrayList<Routine>();
		  
			// Statements allow to issue SQL queries to the database
		      statement = connect.createStatement();
		      // Result set get the result of the SQL query
		      resultSet = statement
		          .executeQuery("select * from routine;");
		  
		      while(resultSet.next()){
				  int rId = resultSet.getInt("id");
			      String exercises = resultSet.getString("exercises");
			      
			      String[] exerciseIds = exercises.split(",");
			      List<Exercise> exers = new ArrayList<Exercise>();
			      for(int i = 0; i< exerciseIds.length; i++){
			    	  exers.add(exerciseDao.getWorkout(Integer.parseInt(exerciseIds[i])));
			      }
			      
			      int difficulty = resultSet.getInt("difficulty");
			      int numDays = resultSet.getInt("num_days");
			      String name = resultSet.getString("name");
			      all.add(new Routine(exers, name, difficulty, numDays));
		      }
		      
		      return all;
	}

	@Override
	public Routine getRoutine(int id) {
		// TODO Auto-generated method stub
		return null;
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

package com.gymlife.workoutservice.db;

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

public class ExerciseDaoImp {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;

  
  public ExerciseDaoImp(){
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

  public Exercise getWorkout(int id) throws SQLException{
	  // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // Result set get the result of the SQL query
      resultSet = statement
          .executeQuery("select * from exercise where id ='" + id + "';");
  
      resultSet.first();
	  int resId = resultSet.getInt("id");
      String name = resultSet.getString("name");
      String muscleGroup = resultSet.getString("muscle_group");
      String primaryMuscle = resultSet.getString("primary_muscle");
      String secondaryMuscles = resultSet.getString("secondary_muscles");
      String difficulty = resultSet.getString("difficulty");
      String type = resultSet.getString("type");
      close();
      return new Exercise(resId, name, muscleGroup, primaryMuscle, secondaryMuscles, difficulty, type);
  }
  
  public List<Exercise> getAllWorkouts() throws SQLException{
	  
	  List<Exercise> all = new ArrayList<Exercise>();
	  
	// Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // Result set get the result of the SQL query
      resultSet = statement
          .executeQuery("select * from exercise;");
  
      while(resultSet.next()){
		  int resId = resultSet.getInt("id");
	      String name = resultSet.getString("name");
	      String muscleGroup = resultSet.getString("muscle_group");
	      String primaryMuscle = resultSet.getString("primary_muscle");
	      String secondaryMuscles = resultSet.getString("secondary_muscles");
	      String difficulty = resultSet.getString("difficulty");
	      String type = resultSet.getString("type");
	      all.add(new Exercise(resId, name, muscleGroup, primaryMuscle, secondaryMuscles, difficulty, type));
      }
      close();
      return all;
  }

  // You need to close the resultSet
  private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }

} 
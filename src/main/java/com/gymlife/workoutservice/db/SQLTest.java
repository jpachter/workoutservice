package com.gymlife.workoutservice.db;

public class SQLTest {
  public static void main(String[] args) throws Exception {
    MySQLAccess dao = new MySQLAccess();
    dao.readDataBase();
    
    System.out.println(dao.getWorkouts());
  }


} 
package com.gymlife.workoutservice.db.dao;

import java.sql.SQLException;
import java.util.List;

import com.gymlife.workoutservice.db.dto.Routine;
import com.gymlife.workoutservice.db.dto.Routines;

public interface RoutineDaoInterface {

	public Routines getRoutines() throws SQLException;
	public Routine getRoutine(int id) throws SQLException;
	public void addWorkout(int id);
	public void deleteWorkout(int id);
	public void deleteRoutine(int id);
	
}

package com.gymlife.workoutservice.db.dao;

import java.sql.SQLException;
import java.util.List;

import com.gymlife.workoutservice.db.dto.Routine;

public interface RoutineDaoInterface {

	public List<Routine> getRoutines() throws SQLException;
	public Routine getRoutine(int id);
	public void addWorkout(int id);
	public void deleteWorkout(int id);
	public void deleteRoutine(int id);
	
}
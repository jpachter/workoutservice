package com.gymlife.workoutservice.db;

import java.sql.SQLException;
import java.util.List;

public interface RoutineDaoInterface {

	public List<Routine> getRoutines() throws SQLException;
	public Routine getRoutine(int id);
	public void addWorkout(int id);
	public void deleteWorkout(int id);
	public void deleteRoutine(int id);
	
}

package com.gymlife.workoutservice.db;

import java.util.List;

public interface RoutineDaoInterface {

	public List<Exercise> getRoutines(int id);
	public Routine getRoutine(int id);
	public void addWorkout(int id);
	public void deleteWorkout(int id);
	public void deleteRoutine(int id);
	
}

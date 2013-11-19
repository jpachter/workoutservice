package com.gymlife.workoutservice.db.dao;

import java.util.List;

import com.gymlife.workoutservice.db.dto.Routine;

public interface RoutineDaoInterface {

	public List<Routine> getRoutines();
	public List<Routine> getCurrentRoutine(int userID);
	public List<Routine> getCompletedRoutines(int userID);
	public void addWorkout(int id);
	public void deleteWorkout(int id);
	public void deleteRoutine(int id);	
}

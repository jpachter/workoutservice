package com.gymlife.workoutservice.db;

import java.util.List;

public interface ExerciseDaoInterface {

	public List<Exercise> getAllExercises();
	public Exercise getWorkout(int id);
	public Exercise getWorkout(String search);
	public void addExercise(Exercise ex);
	public void deleteExercise(int id);
	public void updateWorkout(Exercise ex);
	
	
}

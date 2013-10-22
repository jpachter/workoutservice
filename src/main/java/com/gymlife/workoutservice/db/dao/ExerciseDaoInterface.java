package com.gymlife.workoutservice.db.dao;

import java.util.List;

import com.gymlife.workoutservice.db.dto.Exercise;

public interface ExerciseDaoInterface {

	public List<Exercise> getAllExercises();
	public Exercise getWorkout(int id);
	public Exercise getWorkout(String search);
	public void addExercise(Exercise ex);
	public void deleteExercise(int id);
	public void updateWorkout(Exercise ex);
	
	
}

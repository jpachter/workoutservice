package com.gymlife.workoutservice.db.dto;

public class FullExercise {
	Exercise exercise;
	ExerciseMetrics goal;
	ExerciseMetrics actual;
	
	public FullExercise(){
		
	}
	
	public Exercise getExercise() {
		return exercise;
	}
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	public ExerciseMetrics getGoal() {
		return goal;
	}
	public void setGoal(ExerciseMetrics goal) {
		this.goal = goal;
	}
	public ExerciseMetrics getActual() {
		return actual;
	}
	public void setActual(ExerciseMetrics actual) {
		this.actual = actual;
	}
}

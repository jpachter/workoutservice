package com.gymlife.workoutservice.db;

import java.util.List;

public class Routine {
	List<Exercise> exercises;
	String name;
	int difficulty;
	int numDays;
	
	public Routine(List<Exercise> exercises, String name, int difficulty, int numDays){
		this.exercises = exercises;
		this.name = name;
		this.difficulty = difficulty;
		this.numDays = numDays;
	}
	
	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getNumDays() {
		return numDays;
	}

	public void setNumDays(int numDays) {
		this.numDays = numDays;
	}
}

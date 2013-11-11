package com.gymlife.workoutservice.db.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import com.gymlife.workoutservice.Day;
import com.gymlife.workoutservice.Difficulty;

public class Routine {
	TreeSet<Workout> workouts;
	TreeSet<CompletedWorkout> completedWorkouts;
	String name;
	Difficulty difficulty;
	TreeSet<Day> days;
	int id;
	
	public Routine(){
		workouts = new TreeSet<Workout>();
		completedWorkouts = new TreeSet<CompletedWorkout>();
		days = new TreeSet<Day>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void completeWorkout(Workout w){
		completedWorkouts.add(new CompletedWorkout(w));
	}
	
	public TreeSet<Workout> getWorkouts() {
		return workouts;
	}

	public void addWorkout(Workout workout){
		workouts.add(workout);
		days.add(workout.getDay());
	}
	
	public void removeWorkout(Workout workout){
		workouts.remove(workout);
		days.remove(workout.getDay());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public TreeSet<Day> getDays() {
		return days;
	}
}

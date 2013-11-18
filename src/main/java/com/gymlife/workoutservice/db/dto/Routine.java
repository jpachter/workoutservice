package com.gymlife.workoutservice.db.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import com.gymlife.workoutservice.Day;
import com.gymlife.workoutservice.Difficulty;

public class Routine {
	TreeSet<Workout> currentRoutine;
	TreeSet<CompletedWorkout> completedRoutines;
	int userId;
	
	public Routine(){
		currentRoutine = new TreeSet<Workout>();
		completedRoutines = new TreeSet<CompletedWorkout>();
	}
	
	public void addCompleteWorkout(CompletedWorkout w){
		completedRoutines.add(w);
	}
	
	public CompletedWorkout workoutAtTime(long time){
		for(CompletedWorkout work : completedRoutines){
			if (work.getDate() == time){
				return work;
			}
		}
		return null;
	}
	
	public TreeSet<Workout> getCurrentRoutine() {
		return currentRoutine;
	}
	
	public TreeSet<CompletedWorkout> getCompletedRoutines(){
		return completedRoutines;
	}

	public void addWorkout(Workout workout){
		currentRoutine.add(workout);
	}
	
	public void removeWorkout(Workout workout){
		currentRoutine.remove(workout);
	}

	
}

package com.gymlife.workoutservice.db.dto;

import java.util.LinkedList;
import java.util.List;

import com.gymlife.workoutservice.Day;
import com.gymlife.workoutservice.MuscleGroup;

public class Workout implements Comparable<Workout>{
	Day day;
	List<MuscleGroup> muscleGroup;
	List<FullExercise> exercises;
	
	public Workout(){
		muscleGroup = new LinkedList<MuscleGroup>();
		exercises = new LinkedList<FullExercise>();
	}
	
	public Day getDay() {
		return day;
	}
	
	public void setDay(Day day) {
		this.day = day;
	}
	
	public List<MuscleGroup> getFocus() {
		return muscleGroup;
	}
	
	public void addFocus(MuscleGroup f) {
		muscleGroup.add(f);
	}
	
	public void removeFocus(MuscleGroup f){
		muscleGroup.remove(f);
	}
	
	public List<FullExercise> getExercises() {
		return exercises;
	}
	
	public void addExercise(FullExercise ex){
		exercises.add(ex);
	}
	
	public void removeExercise(FullExercise ex){
		exercises.remove(ex);
	}

	@Override
	public int compareTo(Workout w) {
		return day.compareTo(w.day);
	}

}

package com.gymlife.workoutservice.db.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import com.gymlife.workoutservice.Day;
import com.gymlife.workoutservice.MuscleGroup;

public class Workout implements Comparable<Workout>{
	private Day scheduledDay;
	private TreeSet<MuscleGroup> muscleGroup;
	private List<Exercise> exercises;
	private long timeStarted;
	private long timeCompleted;
	
	public Workout(){
		muscleGroup = new TreeSet<MuscleGroup>();
		exercises = new LinkedList<Exercise>();
	}
	
	public Day getScheduledDay() {
		return scheduledDay;
	}
	
	public TreeSet<MuscleGroup> getMuscleGroup() {
		return muscleGroup;
	}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public long getTimeStarted() {
		return timeStarted;
	}

	public long getTimeCompleted() {
		return timeCompleted;
	}
	
	public void addMuscleGroups(TreeSet<MuscleGroup> mgs){
		muscleGroup.addAll(mgs);
	}

	public void addExercise(Exercise e){
		exercises.add(e);
	}
	
	public void addMuscleGroup(MuscleGroup mg){
		muscleGroup.add(mg);
	}
	
	public void setScheduledDay(Day scheduledDay) {
		this.scheduledDay = scheduledDay;
	}
	
	public void setMuscleGroup(TreeSet<MuscleGroup> muscleGroup) {
		this.muscleGroup = muscleGroup;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}
	
	public void setTimeStarted(long timeStarted) {
		this.timeStarted = timeStarted;
	}

	public void setTimeCompleted(long timeCompleted) {
		this.timeCompleted = timeCompleted;
	}

	@Override
	public int compareTo(Workout w) {
		return scheduledDay.compareTo(w.scheduledDay);
	}

}

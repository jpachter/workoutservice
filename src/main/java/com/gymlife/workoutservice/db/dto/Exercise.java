package com.gymlife.workoutservice.db.dto;

import java.util.LinkedList;
import java.util.List;

import com.gymlife.workoutservice.Difficulty;
import com.gymlife.workoutservice.MuscleGroup;
import com.gymlife.workoutservice.Type;


public class Exercise {
	private int id;
	private String name;
	private Difficulty difficulty;
	private Type type;
	private List<MuscleGroup> muscleGroups;
	private List<String> primaryMuscles;
	private List<String> secondaryMuscles;

	
	public Exercise(){
		primaryMuscles = new LinkedList<String>();
		secondaryMuscles = new LinkedList<String>();
		muscleGroups = new LinkedList<MuscleGroup>();
	}

	public List<String> getPrimaryMuscles() {
		return primaryMuscles;
	}
	
	public List<String> getSecondaryMuscles() {
		return secondaryMuscles;
	}

	public void addSecondaryMuscle(String group){
		secondaryMuscles.add(group);
	}
	
	public void addPrimaryMuscle(String group){
		primaryMuscles.add(group);
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type workoutType) {
		this.type = workoutType;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String workoutName) {
		this.name = workoutName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<MuscleGroup> getMuscleGroups() {
		return muscleGroups;
	}

	public void addMuscleGroup(String muscleGroup) {
		this.muscleGroups.add(MuscleGroup.forValue(muscleGroup));
	}
		
}

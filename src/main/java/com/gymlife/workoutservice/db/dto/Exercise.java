package com.gymlife.workoutservice.db.dto;

import java.util.TreeSet;

import com.gymlife.workoutservice.Difficulty;
import com.gymlife.workoutservice.Muscle;
import com.gymlife.workoutservice.MuscleGroup;
import com.gymlife.workoutservice.Type;


public class Exercise {
	private int id;
	private String name;
	private Difficulty difficulty;
	private Type type;
	private TreeSet<MuscleGroup> muscleGroups;
	private TreeSet<Muscle> primaryMuscles;
	private TreeSet<Muscle> secondaryMuscles;

	
	public Exercise(){
		primaryMuscles = new TreeSet<Muscle>();
		secondaryMuscles = new TreeSet<Muscle>();
		muscleGroups = new TreeSet<MuscleGroup>();
	}
	
	public TreeSet<Muscle> getPrimaryMuscles() {
		return primaryMuscles;
	}
	
	public TreeSet<Muscle> getSecondaryMuscles() {
		return secondaryMuscles;
	}
	
	public TreeSet<MuscleGroup> getMuscleGroups() {
		return muscleGroups;
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String workoutName) {
		this.name = workoutName;
	}

	public void setType(Type workoutType) {
		this.type = workoutType;
	}
	
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	public void addSecondaryMuscle(String sMuscle){
		secondaryMuscles.add(Muscle.forValue(sMuscle));
	}
	
	public void addPrimaryMuscle(String pMuscle){
		primaryMuscles.add(Muscle.forValue(pMuscle));
	}
	
	public void addMuscleGroup(String muscleGroup) {
		this.muscleGroups.add(MuscleGroup.forValue(muscleGroup));
	}

	public void setMuscleGroups(TreeSet<MuscleGroup> muscleGroups) {
		this.muscleGroups = muscleGroups;
	}

	public void setPrimaryMuscles(TreeSet<Muscle> primaryMuscles) {
		this.primaryMuscles = primaryMuscles;
	}

	public void setSecondaryMuscles(TreeSet<Muscle> secondaryMuscles) {
		this.secondaryMuscles = secondaryMuscles;
	}
	
	
}

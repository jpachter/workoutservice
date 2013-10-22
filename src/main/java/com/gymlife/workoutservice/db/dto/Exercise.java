package com.gymlife.workoutservice.db.dto;


public class Exercise {

	private int id;
	private String name;
	private String muscleGroup;
	private String primaryMuscle;
	private String secondaryMuscles;
	private String difficulty;
	private String type;
	
	public Exercise(){
		
	}

	@Override
	public String toString() {
		return "Workout [workoutName=" + name + ", primaryMuscle=" + primaryMuscle + "]";
	}

	public String getSecondaryMuscles() {
		return secondaryMuscles;
	}

	public void setSecondaryMuscles(String secondaryMuscles) {
		this.secondaryMuscles = secondaryMuscles;
	}

	public String getPrimaryMuscle() {
		return primaryMuscle;
	}

	public void setPrimaryMuscle(String primaryMuscle) {
		this.primaryMuscle = primaryMuscle;
	}
	
	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getType() {
		return type;
	}

	public void setType(String workoutType) {
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

	public String getMuscleGroup() {
		return muscleGroup;
	}

	public void setMuscleGroup(String muscleGroup) {
		this.muscleGroup = muscleGroup;
	}
		
}

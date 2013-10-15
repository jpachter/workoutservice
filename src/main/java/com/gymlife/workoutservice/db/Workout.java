package com.gymlife.workoutservice.db;


public class Workout {

	private String workoutName;
	private String primaryMuscle;
	private String secondaryMuscles;
	private String skillLevel;
	private String workoutType;
	
	public Workout() {}
	
	public Workout(String workoutName, String primaryMuscle, String secondaryMuscles, String skillLevel, String workoutType){
		this.setWorkoutName(workoutName);
		this.setPrimaryMuscle(primaryMuscle);
		this.setSecondaryMuscles(secondaryMuscles);
		this.setSkillLevel(skillLevel);
		this.setWorkoutType(workoutType);
	}
	

	@Override
	public String toString() {
		return "Workout [workoutName=" + workoutName + ", primaryMuscle=" + primaryMuscle + "]";
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
	
	public String getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getWorkoutType() {
		return workoutType;
	}

	public void setWorkoutType(String workoutType) {
		this.workoutType = workoutType;
	}
	
	public String getWorkoutName() {
		return workoutName;
	}

	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}
		
}

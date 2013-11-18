package com.gymlife.workoutservice.db.dto;

public class ExerciseUser extends ExerciseCreator {

	private int weightGoal;
	private int setsActual;
	private int repsActual;
	private int weightActual;
	
	public ExerciseUser(){
		super();
	}
	
	public int getWeightGoal() {
		return weightGoal;
	}
	
	public int getSetsActual() {
		return setsActual;
	}
	
	public int getRepsActual() {
		return repsActual;
	}
	
	public int getWeightActual() {
		return weightActual;
	}

	public void setWeightGoal(int weightGoal) {
		this.weightGoal = weightGoal;
	}
	
	public void setSetsActual(int setsActual) {
		this.setsActual = setsActual;
	}

	public void setRepsActual(int repsActual) {
		this.repsActual = repsActual;
	}

	public void setWeightActual(int weightActual) {
		this.weightActual = weightActual;
	}
}

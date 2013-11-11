package com.gymlife.workoutservice.db.dto;

public class ExerciseMetrics {
	int weight;
	int numSets;
	int numReps;
	
	public ExerciseMetrics(){}
	
	public ExerciseMetrics(int weight, int sets, int reps){
		this.weight = weight;
		this.numSets = sets;
		this.numReps = reps;
	}
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getNumSets() {
		return numSets;
	}

	public void setNumSets(int numSets) {
		this.numSets = numSets;
	}

	public int getNumReps() {
		return numReps;
	}

	public void setNumReps(int numReps) {
		this.numReps = numReps;
	}

	@Override
	public String toString(){
		return numSets + "x" + numReps;
	}
}

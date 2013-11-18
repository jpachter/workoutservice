package com.gymlife.workoutservice.db.dto;

import java.util.TreeSet;

import com.gymlife.workoutservice.Difficulty;
import com.gymlife.workoutservice.Muscle;
import com.gymlife.workoutservice.MuscleGroup;
import com.gymlife.workoutservice.Type;

public class ExerciseCreator extends Exercise{

	private int setsGoal;
	private int repsGoal;
	
	public ExerciseCreator(){
		super();
	}
	
	public ExerciseCreator(Exercise e){
		setId(e.getId());
		setName(e.getName());
		setDifficulty(e.getDifficulty());
		setType(e.getType());
		setMuscleGroups(e.getMuscleGroups());
		setPrimaryMuscles(e.getPrimaryMuscles());
		setSecondaryMuscles(e.getSecondaryMuscles());
	}
	
	public int getSetsGoal(){
		return this.setsGoal;
	}
	
	public int getRepsGoal(){
		return this.repsGoal;
	}
	
	public void setSetsGoal(int num){
		this.setsGoal = num;
	}
	
	public void setRepsGoal(int num){
		this.repsGoal = num;
	}

}

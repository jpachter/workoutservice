package com.gymlife.workoutservice.db.dto;

import java.util.Date;
import java.util.List;

import com.gymlife.workoutservice.Day;
import com.gymlife.workoutservice.MuscleGroup;

public class CompletedWorkout extends Workout{

	long date;  //epoch time
	
	public CompletedWorkout(){
		super();
	}
	
	public CompletedWorkout(Workout w){
		this.day = w.day;
		this.muscleGroup = w.muscleGroup;
		this.exercises = w.exercises;
		this.date = System.currentTimeMillis()/1000;

	}

	public String getDate() {
		return String.valueOf(date);
	}

	public void setDate(long epochString) {
		this.date = epochString;
	}
	
	public int compareTo(CompletedWorkout w) {
		if (date < w.date)
			return -1;
		else if (date == w.date)
			return 0;
		else
			return 1;
	}
	
}

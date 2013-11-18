package com.gymlife.workoutservice.db.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.gymlife.workoutservice.Day;

public class RoutineWeek implements Comparable<RoutineWeek>{
	
	private TreeSet<Day> daysOfWeek;
	private TreeSet<Workout> workouts;
	private int weekNumber;
	
	public RoutineWeek(){
		daysOfWeek = new TreeSet<Day>();
		workouts = new TreeSet<Workout>();
	}

	public int getWeekNumber(){
		return weekNumber;
	}
	
	public TreeSet<Day> getDaysOfWeek() {
		return daysOfWeek;
	}

	public TreeSet<Workout> getWorkouts() {
		return workouts;
	}
	
	public void addWorkout(Workout w){
		workouts.add(w);
	}
	
	public void addDayOfWeek(Day d){
		daysOfWeek.add(d);
	}

	public void setWeekNumber(int num){
		this.weekNumber = num;
	}
	
	public void setDaysOfWeek(TreeSet<Day> daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	public void setWorkouts(TreeSet<Workout> workouts) {
		this.workouts = workouts;
	}
	
	@Override
	public int compareTo(RoutineWeek w) {
		return this.weekNumber - w.weekNumber;
	}
	
}

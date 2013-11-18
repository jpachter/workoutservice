package com.gymlife.workoutservice.db.dto;

import java.util.TreeSet;

import com.gymlife.workoutservice.Day;
import com.gymlife.workoutservice.Difficulty;

public class Routine {
	private int id;
	private String name;
	private String creatorId;
	private double rating;
	private double percentFinished;
	private TreeSet<RoutineWeek> routineWeeks;
	private Difficulty difficulty;
	
	public Routine(){
		routineWeeks = new TreeSet<RoutineWeek>();
	}

	public Difficulty getDifficulty(){
		return difficulty;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public double getRating() {
		return rating;
	}

	public double getPercentFinished() {
		return percentFinished;
	}

	public TreeSet<RoutineWeek> getRoutineWeeks() {
		return routineWeeks;
	}
	
	public void addRoutineWeek(RoutineWeek rw){
		routineWeeks.add(rw);
	}
	
	public void setDifficulty(Difficulty d){
		this.difficulty = d;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public void setPercentFinished(double percentFinished) {
		this.percentFinished = percentFinished;
	}

	public void setRoutineWeeks(TreeSet<RoutineWeek> routineWeeks) {
		this.routineWeeks = routineWeeks;
	}
}

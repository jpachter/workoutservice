package com.gymlife.workoutservice.db.dto;

import java.util.List;

public class Routines {
	List<Routine> routines;
	
	public Routines(){
	}

	public List<Routine> getRoutines() {
		return routines;
	}

	public void setRoutines(List<Routine> routines) {
		this.routines = routines;
	}
}

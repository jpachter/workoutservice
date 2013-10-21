package com.gymlife.workoutservice.db;

/**
 * Enum for different difficulties.
 * 
 */
public enum Difficulty {
	BEGINNER("beginner"),
	INTERMEDIATE("intermediate"),
	ADVANCED("advanced");
	
	private String difficulty;

	private Difficulty(final String difficulty) {
		this.difficulty = difficulty;
  }
  
  @Override
  public String toString() {
    return difficulty;
  }
}

package com.gymlife.workoutservice;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum for different difficulties.
 * 
 */
public enum Difficulty {
	Beginner("Beginner"),
	Intermediate("Intermediate"),
	Advanced("Advanced");
	
    private static final Map<String, Difficulty> typesByValue = new HashMap<String, Difficulty>();

    static {
        for (Difficulty type : Difficulty.values()) {
            typesByValue.put(type.difficulty, type);
        }
    }
    
	private String difficulty;
	
	private Difficulty(final String difficulty) {
		this.difficulty = difficulty;
	}
  

    public static Difficulty forValue(String value) {
        return typesByValue.get(value);
    }
    
	@Override
	public String toString() {
		return difficulty;
	}
}

package com.gymlife.workoutservice;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum for different difficulties.
 * 
 */
public enum Difficulty {
	Beginner("Beginner", 1),
	Intermediate("Intermediate", 2),
	Advanced("Advanced", 3);
	
    private static final Map<String, Difficulty> typesByValue = new HashMap<String, Difficulty>();
    private static final Map<Integer, Difficulty> typesByRank = new HashMap<Integer, Difficulty>();

    static {
        for (Difficulty type : Difficulty.values()) {
            typesByValue.put(type.difficulty, type);
            typesByRank.put(type.rank, type);
        }
    }
    
	private String difficulty;
	private int rank;
	
	private Difficulty(final String difficulty, final int rank) {
		this.difficulty = difficulty;
		this.rank = rank;
	}
  
	public static Difficulty forRank(int rank){
        return typesByRank.get(rank);
	}

    public static Difficulty forValue(String value) {
        return typesByValue.get(value);
    }
    
    public int getRank(){
    	return rank;
    }
    
	@Override
	public String toString() {
		return difficulty;
	}
}

package com.gymlife.workoutservice;

import java.util.HashMap;
import java.util.Map;

public enum Muscle {
	Biceps("Biceps"),
	Triceps("Triceps"),
	Chest("Chest"),
	Abs("Abs"),
	Calves("Calves"),
	Glutes("Glutes");
	
    private static final Map<String, Muscle> typesByValue = new HashMap<String, Muscle>();

    static {
        for (Muscle group : Muscle.values()) {
            typesByValue.put(group.muscle, group);
        }
    }
	
	private String muscle;

	private Muscle(final String muscle) {
		this.muscle = muscle;
  }
	
    public static Muscle forValue(String value) {
        return typesByValue.get(value);
    }
    
  @Override
  public String toString() {
    return muscle;
  }
}

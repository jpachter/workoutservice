package com.gymlife.workoutservice;

import java.util.HashMap;
import java.util.Map;

public enum MuscleGroup {
	Chest("Chest"),
	Back("Back"),
	Arms("Arms"),
	Legs("Legs"),
	Biceps("Biceps"),
	Triceps("Triceps"),
	Calves("Calves");
	
    private static final Map<String, MuscleGroup> typesByValue = new HashMap<String, MuscleGroup>();

    static {
        for (MuscleGroup group : MuscleGroup.values()) {
            typesByValue.put(group.muscleGroup, group);
        }
    }
	
	private String muscleGroup;

	private MuscleGroup(final String muscleGroup) {
		this.muscleGroup = muscleGroup;
  }
	
    public static MuscleGroup forValue(String value) {
        return typesByValue.get(value);
    }
    
  @Override
  public String toString() {
    return muscleGroup;
  }
}

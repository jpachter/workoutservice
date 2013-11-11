package com.gymlife.workoutservice;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum for different difficulties.
 * 
 */
public enum Type {
	Dumbbell("Dumbbell"),
	Barbell("Barbell"),
	Machine("Machine");
	
    private static final Map<String, Type> typesByValue = new HashMap<String, Type>();

    static {
        for (Type type : Type.values()) {
            typesByValue.put(type.type, type);
        }
    }
    
	private String type;
	
	private Type(final String type) {
		this.type = type;
	}
  

    public static Type forValue(String value) {
        return typesByValue.get(value);
    }
    
	@Override
	public String toString() {
		return type;
	}
}

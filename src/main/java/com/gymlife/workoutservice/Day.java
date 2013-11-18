package com.gymlife.workoutservice;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public enum Day {
	MONDAY("Monday", 1), 
	TUESDAY("Tuesday", 2), 
	WEDNESDAY("Wednesday", 3), 
	THURSDAY("Thursday", 4), 
	FRIDAY("Friday", 5), 
	SATURDAY("Saturday", 6), 
	SUNDAY("Sunday", 7);
	
	private static final Map<String, Day> typesByValue = new HashMap<String, Day>();

	    static {
	        for (Day type : Day.values()) {
	            typesByValue.put(type.day, type);
	        }
	    }
	    
	private String day;
	private int order;

	private Day(final String day, final int order) {
		this.day = day;
		this.order = order;
	}
	
    public static Day forValue(String value) {
        return typesByValue.get(value);
    }

	class DayComparator implements Comparator<Day> {
		public int compare(Day one, Day two) {
			return one.order - two.order;
		}
	}

	@Override
	public String toString() {
		return day;
	}
}

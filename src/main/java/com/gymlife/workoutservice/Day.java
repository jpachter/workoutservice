package com.gymlife.workoutservice;

import java.util.Comparator;

public enum Day {
	MONDAY("Mo", 1), 
	TUESDAY("Tu", 2), 
	WEDNESDAY("We", 3), 
	THURSDAY("Th", 4), 
	FRIDAY("Fr", 5), 
	SATURDAY("Sa", 6), 
	SUNDAY("Su", 7);

	private String day;
	private int order;

	private Day(final String day, final int order) {
		this.day = day;
		this.order = order;
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

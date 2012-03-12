package de.dhbw.wbs;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeSpan {
	private Calendar startTime;
	private int duration;

	public TimeSpan() {
		this.startTime = Calendar.getInstance();
		this.duration = 0;
	}

	public TimeSpan(Calendar startTime, int duration) {
		this.startTime = startTime;
		this.duration = duration;
	}

	public Calendar getStartTime() {
		return (Calendar) this.startTime.clone();
	}

	public void setStartTime(Calendar start) {
		this.startTime = start;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Calendar getEndTime() {
		Calendar c = this.getStartTime();
		c.add(Calendar.HOUR_OF_DAY, this.getDuration());
		return c;
	}
}

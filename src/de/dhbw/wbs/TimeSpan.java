package de.dhbw.wbs;

import java.util.Calendar;

public class TimeSpan {
	private Calendar startTime = Calendar.getInstance();
	private int duration = 0;

	public Calendar getStartTime() {
		return startTime;
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

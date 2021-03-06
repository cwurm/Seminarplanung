package de.dhbw.wbs;

import java.util.ArrayList;
import java.util.Calendar;

public class Lecture {
	private int number;
	private String name;
	private Lecturer lecturer;
	private Group group;
	private Room room;
	private int duration;
	private Calendar startTime;
	private final ArrayList<Lecture> requiredLectures = new ArrayList<Lecture>();

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getName());

		sb.append("[number=" + this.getNumber() + ",");
		sb.append("name=\"" + this.getName() + "\",");
		sb.append("lecturer=" + this.getLecturer().getName() + ",");
		sb.append("group=" + this.getGroup().getNumber() + ",");
		sb.append("room=" + this.getRoom().getNumber() + ",");

		if (this.getStartTime() != null) {
			sb.append("start=" + this.getStartTime().get(Calendar.HOUR_OF_DAY) + ":"
						+ this.getStartTime().get(Calendar.MINUTE) + ",");
		}
		else {
			sb.append("start=null,");
		}

		sb.append("duration=" + this.getTimeSpan().getDuration() + "]");

		return sb.toString();
	}

	public String toLongString() {
		StringBuilder sb = new StringBuilder(this.toString());

		for (Lecture l: this.getRequiredLectures()) {
			sb.append("\n    Requires: " + l.toString());
		}

		return sb.toString();
	}

	public boolean isTakingPlace() {
		return this.startTime != null;
	}

	public int getNumber() {
		return this.number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Lecturer getLecturer() {
		return this.lecturer;
	}
	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}
	public Group getGroup() {
		return this.group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public Room getRoom() {
		return this.room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public TimeSpan getTimeSpan() {
		return new TimeSpan(this.getStartTime(), this.getDuration());
	}
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
	private Calendar getStartTime() {
		return this.startTime;
	}
	public void addRequiredLecture(Lecture lecture) {
		this.requiredLectures.add(lecture);
	}
	public Iterable<Lecture> getRequiredLectures() {
		return this.requiredLectures;
	}

	public boolean overlapsWith(Lecture aLecture) {
		AllenRelation allenRelation = AllenRelation.getAllenRelation(this.getTimeSpan(), aLecture.getTimeSpan());
		switch(allenRelation) {
		case BEFORE:
		case AFTER:
		case MEETS:
		case MEETS_INV:
			return false;
		default:
			return true;
		}
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getDuration() {
		return this.duration;
	}
}












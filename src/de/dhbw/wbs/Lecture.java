package de.dhbw.wbs;

import java.util.ArrayList;
import java.util.Calendar;

public class Lecture {
	private int number;
	private String name;
	private Lecturer lecturer;
	private Group group;
	private Room room;
	private Calendar startTime;
	private int duration;
	private final ArrayList<Lecture> requiredLectures = new ArrayList<Lecture>();

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Lecture: ");
		sb.append(" Nr(" + this.getNumber() + ")");
		sb.append(" Name(" + this.getName() + ")");
		sb.append(" Lecturer(" + this.getLecturer().getName() + ")");
		sb.append(" Group(" + this.getGroup().getNumber() + ")");
		sb.append(" Room(" + this.getRoom().getNumber() + ")");
		sb.append(" Start(" + this.getStartTime() + ")");
		sb.append(" Duration(" + this.getDuration() + ")");

		for (Lecture l: this.getRequiredLectures()) {
			sb.append("\n    Requires: " + l.toString());
		}

		return sb.toString();
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
	public Calendar getStartTime() {
		return this.startTime;
	}
	public void setStartTime(Calendar start) {
		this.startTime = start;
	}
	public int getDuration() {
		return this.duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public void addRequiredLecture(Lecture lecture) {
		this.requiredLectures.add(lecture);
	}
	public Iterable<Lecture> getRequiredLectures() {
		return this.requiredLectures;
	}
	public Calendar getEndTime() {
		Calendar c = this.getStartTime();
		c.add(Calendar.HOUR_OF_DAY, this.getDuration());
		return c;
	}
	public AllenRelation getAllenRelation(Lecture rhs) {
		if (this.getEndTime().before(rhs.getStartTime())) {
			return AllenRelation.BEFORE;
		}
		else if (this.getStartTime().after(rhs.getEndTime())) {
			return AllenRelation.AFTER;
		}
		else if (this.getStartTime().equals(rhs.getStartTime()) && this.getEndTime().equals(rhs.getEndTime())) {
			return AllenRelation.EQUAL;
		}
		else if (this.getEndTime().equals(rhs.getStartTime())) {
			return AllenRelation.MEETS;
		}
		else if (this.getStartTime().equals(rhs.getEndTime())) {
			return AllenRelation.MEETS_INV;
		}
		else if (this.getStartTime().before(rhs.getStartTime()) && this.getEndTime().after(rhs.getStartTime()) && this.getEndTime().before(rhs.getEndTime())) {
			return AllenRelation.OVERLAPS;
		}
		else if (this.getStartTime().after(rhs.getStartTime()) && this.getStartTime().before(rhs.getEndTime()) && this.getEndTime().after(rhs.getEndTime())) {
			return AllenRelation.OVERLAPS_INV;
		}
		else if (this.getStartTime().after(rhs.getStartTime()) && this.getEndTime().before(rhs.getEndTime())) {
			return AllenRelation.DURING;
		}
		else if (this.getStartTime().before(rhs.getStartTime()) && this.getEndTime().after(rhs.getEndTime())) {
			return AllenRelation.DURING_INV;
		}
		else if (this.getStartTime().equals(rhs.getStartTime()) && this.getEndTime().before(rhs.getEndTime())) {
			return AllenRelation.STARTS;
		}
		else if (this.getStartTime().equals(rhs.getStartTime()) && this.getEndTime().after(rhs.getEndTime())) {
			return AllenRelation.STARTS_INV;
		}
		else if (this.getEndTime().equals(rhs.getEndTime()) && this.getStartTime().after(rhs.getStartTime())) {
			return AllenRelation.FINISHES;
		}
		else if (this.getEndTime().equals(rhs.getEndTime()) && this.getStartTime().before(rhs.getStartTime())) {
			return AllenRelation.FINISHES_INV;
		}
		else {
			throw new RuntimeException("No fitting allen relation found for \n" + this.toString() + "\n" + rhs.toString());
		}
	}
}












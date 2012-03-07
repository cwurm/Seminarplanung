package de.dhbw.wbs;

import java.util.ArrayList;
import java.util.Iterator;

public class Lecture {
	private int number;
	private String name;
	private Lecturer lecturer;
	private Group group;
	private Room room;
	private int start;
	private int duration;
	private ArrayList<Lecture> requiredLectures;

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Lecture: ");
		sb.append(" Nr(" + this.getNumber() + ")");
		sb.append(" Name(" + this.getName() + ")");
		sb.append(" Lecturer(" + this.getLecturer().getName() + ")");
		sb.append(" Group(" + this.getGroup().getNumber() + ")");
		sb.append(" Room(" + this.getRoom().getNumber() + ")");
		sb.append(" Start(" + this.getStart() + ")");
		sb.append(" Duration(" + this.getDuration() + ")");
		
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
	public int getStart() {
		return this.start;
	}
	public void setStart(int start) {
		this.start = start;
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
	public Iterator<Lecture> getRequiredLectures() {
		return this.requiredLectures.iterator();
	}
}

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

	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Lecturer getLecturer() {
		return lecturer;
	}
	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public void addRequiredLecture(Lecture lecture) {
		requiredLectures.add(lecture);
	}
	public Iterator<Lecture> getRequiredLectures() {
		return requiredLectures.iterator();
	}
}

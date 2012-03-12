package de.dhbw.wbs.predicate;

import de.dhbw.wbs.Lecture;
import de.dhbw.wbs.Lecturer;

public class LecturerPredicate extends Predicate<Lecture> {
	final Lecturer lecturer;

	public LecturerPredicate(Lecturer lecture) {
		this.lecturer = lecture;
	}

	@Override
	public boolean matches(Lecture aLecture) {
		return aLecture.getLecturer().equals(lecturer);
	}
}

package de.dhbw.wbs.predicate;

import de.dhbw.wbs.Lecture;

public class DurationPredicate extends Predicate<Lecture> {
	private final int duration;

	public DurationPredicate(int duration) {
		this.duration = duration;
	}

	@Override
	public boolean matches(Lecture aLecture) {
		return aLecture.getDuration() == duration;
	}
}

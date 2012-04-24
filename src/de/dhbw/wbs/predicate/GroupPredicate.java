package de.dhbw.wbs.predicate;

import de.dhbw.wbs.Group;
import de.dhbw.wbs.Lecture;

public class GroupPredicate extends Predicate<Lecture> {
	private final Group group;

	public GroupPredicate(Group group) {
		this.group = group;
	}

	@Override
	public boolean matches(Lecture aLecture) {
		return aLecture.getGroup().equals(this.group);
	}
}

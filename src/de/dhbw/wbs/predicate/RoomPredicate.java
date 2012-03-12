package de.dhbw.wbs.predicate;

import de.dhbw.wbs.Lecture;
import de.dhbw.wbs.Room;

public class RoomPredicate extends Predicate<Lecture> {
	final Room room;

	public RoomPredicate(Room room) {
		this.room = room;
	}

	@Override
	public boolean matches(Lecture aLecture) {
		return aLecture.getRoom().equals(this.room);
	}
}

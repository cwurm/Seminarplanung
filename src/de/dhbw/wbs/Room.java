package de.dhbw.wbs;

public class Room {
	private int number;

	public Room(int number) {
		super();
		this.number = number;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public boolean equals(Object aRoom) {
		if (!(aRoom instanceof Room))
			return false;

		return ((Room) aRoom).number == this.number;
	}
}

package de.dhbw.wbs;

public class Group {
	private int number;

	public Group(int number) {
		this.number = number;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public boolean equals(Object aGroup) {
		if (!(aGroup instanceof Group))
			return false;

		return ((Group) aGroup).number == this.number;
	}
}

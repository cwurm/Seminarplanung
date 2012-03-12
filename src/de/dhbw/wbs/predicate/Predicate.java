package de.dhbw.wbs.predicate;

import java.util.ArrayList;

public abstract class Predicate<T> {
	public abstract boolean matches(T element);

	public ArrayList<T> apply(Iterable<T> rawList) {
		ArrayList<T> filteredList = new ArrayList<T>();
		for (T element : rawList) {
			if (this.matches(element))
				filteredList.add(element);
		}

		return filteredList;
	}


}

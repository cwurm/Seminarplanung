package de.dhbw.wbs;

public interface ParseFunction<T> {
	T parse(String[] values);
}

/**
 *
 */
package de.dhbw.wbs;

import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;

public class CSVParser {
	private final BufferedReader reader;

	public CSVParser(BufferedReader reader) {
		this.reader = reader;
	}

	public Iterable<String[]> parse() throws IOException {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		String line;

		while ((line = this.reader.readLine()) != null) {
			lines.add(line.split(";"));
		}

		return lines;
	}
}

/**
 *
 */
package de.dhbw.wbs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CSVParser {
	private final InputStream in;

	public CSVParser(InputStream in) {
		this.in = in;
	}

	public Iterable<String[]> parse() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		ArrayList<String[]> lines = new ArrayList<String[]>();
		String line;

		while ((line = reader.readLine()) != null) {
			lines.add(line.split(";"));
		}

		reader.close();

		return lines;
	}
}

/**
 * 
 */
package de.dhbw.wbs;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVParser {
	private BufferedReader in;
	
	public CSVParser(BufferedReader in) {
		this.in = in;
	}
	
	public Iterable<String[]> parse() throws IOException {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		String line;
		
		while ((line = in.readLine()) != null) {
			lines.add(line.split(";"));
		}
		
		return lines;
	}
}

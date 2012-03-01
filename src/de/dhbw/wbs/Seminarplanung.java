package de.dhbw.wbs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class Seminarplanung {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Expecting three file names as arguments.");
			System.exit(1);
		}
		
		// 1. Parsen
		try {
			Iterable<Lecture> lectures = new ArrayList<Lecture>();
			
			CSVParser lectureParser = new CSVParser(new BufferedReader(new FileReader(args[0])));
			
			for (String[] elems : lectureParser.parse()) {
				Lecture lecture = new Lecture();
				lecture.setNumber(Integer.parseInt(elems[0]));
				lecture.setName(elems[1]);
				lecture.setLecturer(new Lecturer(elems[2]));
				lecture.setGroup(new Group(Integer.parseInt(elems[3])));
				lecture.setDuration(Integer.parseInt(elems[4]));
				lecture.setRoom(new Room(Integer.parseInt(elems[5])));
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// 2. Konsistenzprüfung
		// 2.1 Eine Vorlesung darf erst dann stattfinden, wenn die Seminargruppe alle dafür
		//     vorausgesetzten Vorlesungen bereits gehört hat.
		// 2.2 Lehrveranstaltungen des gleichen Dozenten oder der gleichen Seminargruppe dürfen
		//     sich nicht überschneiden. Ebenso darf ein Raum nicht zeitgleich von zwei Vorlesungen
		//     genutzt werden.
		// 2.3 Zwischen zwei Lehrveranstaltungen der Länge 2 muss eine Pause sein, d.h. diese dürfen
		//     nicht unmittelbar hintereinander laufen (gilt für Dozenten und Seminargruppen).
	}

}

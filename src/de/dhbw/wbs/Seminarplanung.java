package de.dhbw.wbs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
			Collection<Lecture> lectures = new ArrayList<Lecture>();

			/*
			 * Parse 1st file - lectures
			 *
			 * Lecture No;subject;lecturer;group number;duration;room
			 */
			BufferedReader lectureReader = new BufferedReader(new FileReader(args[0]));
			// Skip the header line
			lectureReader.readLine();
			
			CSVParser lectureParser = new CSVParser(lectureReader);

			for (String[] elems : lectureParser.parse()) {
				Lecture lecture = new Lecture();
				
				lecture.setNumber(Integer.parseInt(elems[0]));
				lecture.setName(elems[1]);
				lecture.setLecturer(new Lecturer(elems[2]));
				lecture.setGroup(new Group(Integer.parseInt(elems[3])));
				lecture.setDuration(Integer.parseInt(elems[4]));
				lecture.setRoom(new Room(Integer.parseInt(elems[5])));
				
				lectures.add(lecture);
			}

			/*
			 * Parse 2nd file - lecture dependencies
			 *
			 * basic lecture;required lecture;;;;
			 */
			// TODO
		}
		catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}


		// 2. Konsistenzpruefung
		// 2.1 Eine Vorlesung darf erst dann stattfinden, wenn die Seminargruppe alle dafuer
		//     vorausgesetzten Vorlesungen bereits gehoert hat.
		// 2.2 Lehrveranstaltungen des gleichen Dozenten oder der gleichen Seminargruppe duerfen
		//     sich nicht ueberschneiden. Ebenso darf ein Raum nicht zeitgleich von zwei Vorlesungen
		//     genutzt werden.
		// 2.3 Zwischen zwei Lehrveranstaltungen der Laenge 2 muss eine Pause sein, d.h. diese duerfen
		//     nicht unmittelbar hintereinander laufen (gilt fuer Dozenten und Seminargruppen).
	}

}

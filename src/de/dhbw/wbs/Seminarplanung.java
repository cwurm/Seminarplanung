package de.dhbw.wbs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import de.dhbw.wbs.predicate.DurationPredicate;
import de.dhbw.wbs.predicate.GroupPredicate;
import de.dhbw.wbs.predicate.LecturerPredicate;
import de.dhbw.wbs.predicate.Predicate;
import de.dhbw.wbs.predicate.RoomPredicate;

public final class Seminarplanung {
	private static final SimpleDateFormat lectureTimeFormat = new SimpleDateFormat("hh:mm");

	private final ArrayList<Lecture> lectures = new ArrayList<Lecture>();

	public Seminarplanung() {

	}

	public void addLecture(Lecture lecture) {
		lectures.add(lecture);
	}

	public boolean isValid() {

		return validateDependencies() &&
				validateOverlaps() &&
				validateBreaks();
	}

	/*
	 *  2.1 A lecture may only take place of the group has already heard all lectures that this
	 *  lecture depends on
	 */
	private boolean validateDependencies() {
		for (Lecture dependentLecture : getHeldLectures()) {
			for (Lecture requiredLecture : dependentLecture.getRequiredLectures()) {
				if (!dependentLecture.getGroup().equals(requiredLecture.getGroup())) {
					logError("Lecture " + dependentLecture.getName() + "depends on lecture " +
							requiredLecture.getName() +
							", but the two lectures are held in different groups.");
					return false;
				}


				if (!requiredLecture.isTakingPlace()) {
					logError("Lecture " + dependentLecture + " depends on lecture " +
							requiredLecture + ", but this lecture is not taught at all.");
					return false;
				}
				else {
					AllenRelation allenRelation = AllenRelation.getAllenRelation(requiredLecture.getTimeSpan(),
							dependentLecture.getTimeSpan());
					switch (allenRelation) {
					case BEFORE:
					case MEETS:
						break;
					default:
						logError("Lecture " + dependentLecture + " depends on lecture " +
								requiredLecture + ", but this lecture is not taught before the other lecture\n" +
								"(Allen relation between required and dependent lecture is " + allenRelation.name() + ")");

						return false;
					}
				}
			}
		}

		return true;
	}

	/*
	 * 2.2 Lectures of the same group or the same lecturer may not overlap.
	 *     One room can be used for only one lecture at a given point of time.
	 */
	private boolean validateOverlaps() {
		final ArrayList<Lecture> heldLectures = getHeldLectures();

		for (Lecturer lecturer : getLecturers()) {
			if (haveOverlap((new LecturerPredicate(lecturer)).apply(heldLectures))) {
				logError("lectures of the same lecturer may not overlap");
				return false;
			}
		}

		for (Group group : getGroups()) {
			if (haveOverlap((new GroupPredicate(group)).apply(heldLectures))) {
				logError("lectures of the same group may not overlap");
				return false;
			}
		}

		for (Room room : getRooms()) {
			if (haveOverlap((new RoomPredicate(room)).apply(heldLectures))) {
				logError("lectures in the same room may not overlap");
				return false;
			}
		}

		return true;
	}

	/*
	 * 2.3 There has to be a break between two lectures of length two for both the lecturers and the
	 * seminar group.
	 */
	private boolean validateBreaks() {
		ArrayList<Lecture> lengthTwoLectures = (new DurationPredicate(2)).apply(getHeldLectures());
		for (Lecture lecture1 : lengthTwoLectures) {
			for (Lecture lecture2 : lengthTwoLectures) {
				if (lecture1 == lecture2)
					continue;

				AllenRelation rel = AllenRelation.getAllenRelation(lecture1.getTimeSpan(),
						lecture2.getTimeSpan());

				if (rel == AllenRelation.MEETS) {
					logError("Lecture " + lecture1.toString() + " is held directly after " + lecture2.toString() +
							", but both lectures are of length two.");
				}
			}
		}

		return true;
	}

	private boolean haveOverlap(ArrayList<Lecture> lectures) {
		for (Lecture lecture1 : lectures) {
			for (Lecture lecture2 : lectures) {
				if (lecture1 == lecture2)
					continue;

				if (lecture1.overlapsWith(lecture2)) {
					logError("Lecture " + lecture1.toString() + " overlaps with lecture " + lecture2.toString() + "\n");
					return true;
				}
			}
		}

		return false;
	}

	public ArrayList<Lecture> getHeldLectures() {
		ArrayList<Lecture> heldLectures = (new Predicate<Lecture>() {

			@Override
			public boolean matches(Lecture aLecture) {
				return aLecture.isTakingPlace();
			}

		}).apply(lectures);

		return heldLectures;
	}

	public ArrayList<Group> getGroups() {
		ArrayList<Group> groups = new ArrayList<Group>();

		for (Lecture lecture : lectures) {
			if (!groups.contains(lecture.getGroup()))
				groups.add(lecture.getGroup());
		}

		return groups;
	}

	public ArrayList<Lecturer> getLecturers() {
		ArrayList<Lecturer> lecturers = new ArrayList<Lecturer>();

		for (Lecture lecture : lectures) {
			if (!lecturers.contains(lecture.getLecturer()))
				lecturers.add(lecture.getLecturer());
		}

		return lecturers;
	}

	public ArrayList<Room> getRooms() {
		ArrayList<Room> rooms = new ArrayList<Room>();

		for (Lecture lecture : lectures) {
			if (!rooms.contains(lecture.getRoom()))
				rooms.add(lecture.getRoom());
		}

		return rooms;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Expecting three file names as arguments.");
			System.exit(1);
		}

		String lectureFileName = args[0];
		String depFileName = args[1];
		String timeFileName = args[2];

		Seminarplanung seminarplanung = Seminarplanung.loadFromFile(lectureFileName, depFileName, timeFileName);
		if (seminarplanung == null) {
			logError("A parse error occured.\n");
			System.exit(1);
		}

		if (!seminarplanung.isValid()) {
			logError("The loaded seminar plan is not valid.");
			System.exit(1);
		}

		System.out.println("All checks passed. File appears to be valid.");
		System.exit(0);
	}

	public static Seminarplanung loadFromFile(String lectureFileName, String depFileName,
			String timeFileName) {

		final Seminarplanung seminarplanung = new Seminarplanung();

		final HashMap<Integer, Lecture> lectures = new HashMap<Integer, Lecture>();
		final HashMap<Integer, Group> groups = new HashMap<Integer, Group>();

		try {
			/*
			 * Parse 1st file - lectures
			 *
			 * Lecture No;subject;lecturer;group number;duration;room
			 */
			BufferedReader lectureReader = new BufferedReader(new FileReader(lectureFileName));
			// Skip the header line
			lectureReader.readLine();

			CSVParser lectureParser = new CSVParser(lectureReader);

			for (String[] elems : lectureParser.parse()) {
				Lecture lecture = new Lecture();

				Integer groupNumber = new Integer(elems[3]);
				Group group = groups.get(groupNumber);
				if (group == null) {
					group = new Group(groupNumber);
					groups.put(groupNumber, group);
				}
				lecture.setGroup(group);

				Lecturer lecturer = new Lecturer(elems[2]);
				lecture.setLecturer(lecturer);

				Room room = new Room(Integer.parseInt(elems[5]));
				lecture.setRoom(room);

				lecture.setNumber(Integer.parseInt(elems[0]));
				lecture.setName(elems[1]);
				lecture.setDuration(Integer.parseInt(elems[4]));

				lectures.put(new Integer(lecture.getNumber()), lecture);
			}

			/*
			 * Parse 2nd file - lecture dependencies
			 *
			 * basic lecture;required lecture;;;;
			 */
			BufferedReader depReader = new BufferedReader(new FileReader(depFileName));
			// Skip the header line
			depReader.readLine();

			CSVParser depParser = new CSVParser(depReader);

			for (String[] elems : depParser.parse()) {
				Lecture basicLecture = lectures.get(new Integer(elems[0]));
				Lecture dependentLecture = lectures.get(new Integer(elems[1]));

				dependentLecture.addRequiredLecture(basicLecture);
			}

			/*
			 * Parse 3rd file - time information
			 * group number;lecture number;start time
			 */
			BufferedReader timeReader = new BufferedReader(new FileReader(timeFileName));
			// Skip the header line
			timeReader.readLine();

			CSVParser timeParser = new CSVParser(timeReader);

			for (String[] elems : timeParser.parse()) {
				Lecture lecture = lectures.get(new Integer(elems[1]));
				Group group = groups.get(new Integer(elems[0]));


				if (group != lecture.getGroup()) {
					logError("The group number for lecture " + elems[1] +
							"(" + lecture.getName() + ") as supplied  in file " + timeFileName + " does not match the group " +
							"number from file " + lectureFileName);

					return null;
				}

				Date startTime = null;
				try {
					startTime = lectureTimeFormat.parse(elems[2]);
				} catch (ParseException exc) {
					logError("Invalid time format " + elems[2] +
							" in file " + timeFileName + ". Expect hh:mm notation.");
					return null;
				}

				Calendar cal = Calendar.getInstance();
				cal.setTime(startTime);
				lecture.setStartTime(cal);
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return seminarplanung;
	}

	private static void logError(String errMsg) {
		System.err.println(errMsg);
	}
}

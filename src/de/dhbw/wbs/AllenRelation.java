package de.dhbw.wbs;

public enum AllenRelation {
	BEFORE, AFTER, EQUAL, MEETS, MEETS_INV, OVERLAPS, OVERLAPS_INV, DURING, DURING_INV, STARTS, STARTS_INV, FINISHES, FINISHES_INV;

	public static AllenRelation getAllenRelation(TimeSpan lhs, TimeSpan rhs) {
		if (lhs.getEndTime().before(rhs.getStartTime())) {
			return AllenRelation.BEFORE;
		}
		else if (lhs.getStartTime().after(rhs.getEndTime())) {
			return AllenRelation.AFTER;
		}
		else if (lhs.getStartTime().equals(rhs.getStartTime()) && lhs.getEndTime().equals(rhs.getEndTime())) {
			return AllenRelation.EQUAL;
		}
		else if (lhs.getEndTime().equals(rhs.getStartTime())) {
			return AllenRelation.MEETS;
		}
		else if (lhs.getStartTime().equals(rhs.getEndTime())) {
			return AllenRelation.MEETS_INV;
		}
		else if (lhs.getStartTime().before(rhs.getStartTime()) && lhs.getEndTime().after(rhs.getStartTime()) && lhs.getEndTime().before(rhs.getEndTime())) {
			return AllenRelation.OVERLAPS;
		}
		else if (lhs.getStartTime().after(rhs.getStartTime()) && lhs.getStartTime().before(rhs.getEndTime()) && lhs.getEndTime().after(rhs.getEndTime())) {
			return AllenRelation.OVERLAPS_INV;
		}
		else if (lhs.getStartTime().after(rhs.getStartTime()) && lhs.getEndTime().before(rhs.getEndTime())) {
			return AllenRelation.DURING;
		}
		else if (lhs.getStartTime().before(rhs.getStartTime()) && lhs.getEndTime().after(rhs.getEndTime())) {
			return AllenRelation.DURING_INV;
		}
		else if (lhs.getStartTime().equals(rhs.getStartTime()) && lhs.getEndTime().before(rhs.getEndTime())) {
			return AllenRelation.STARTS;
		}
		else if (lhs.getStartTime().equals(rhs.getStartTime()) && lhs.getEndTime().after(rhs.getEndTime())) {
			return AllenRelation.STARTS_INV;
		}
		else if (lhs.getEndTime().equals(rhs.getEndTime()) && lhs.getStartTime().after(rhs.getStartTime())) {
			return AllenRelation.FINISHES;
		}
		else if (lhs.getEndTime().equals(rhs.getEndTime()) && lhs.getStartTime().before(rhs.getStartTime())) {
			return AllenRelation.FINISHES_INV;
		}
		else {
			throw new RuntimeException("No fitting allen relation found for \n" + lhs.toString() + "\n" + rhs.toString());
		}
	}
}

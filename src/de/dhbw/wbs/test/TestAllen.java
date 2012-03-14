package de.dhbw.wbs.test;

import static org.junit.Assert.fail;

import java.util.GregorianCalendar;

import org.junit.Test;

import de.dhbw.wbs.AllenRelation;
import de.dhbw.wbs.TimeSpan;

public class TestAllen {
	/*
	 * <---->
	 * Date 1
	 * <------------------------------>
	 *              Date 2
	 *       <-------------------->
	 *               Date 3
	 *                           <---->
	 *                           Date 4
	 */
	private static final int HOURS_PER_DAY = 24;
	private final TimeSpan span1 = new TimeSpan(new GregorianCalendar(1989, GregorianCalendar.JANUARY, 1), 1 * HOURS_PER_DAY);
	private final TimeSpan span2 = new TimeSpan(new GregorianCalendar(1989, GregorianCalendar.JANUARY, 1), 8 * HOURS_PER_DAY);
	private final TimeSpan span3 = new TimeSpan(new GregorianCalendar(1989, GregorianCalendar.JANUARY, 2), 6 * HOURS_PER_DAY);
	private final TimeSpan span4 = new TimeSpan(new GregorianCalendar(1989, GregorianCalendar.JANUARY, 7), 2 * HOURS_PER_DAY);

	@Test
	public void testEquals() {
		AllenRelation equalRelation = AllenRelation.getAllenRelation(this.span1, this.span1);
		assertRelationEquals(AllenRelation.EQUAL, equalRelation);
	}

	@Test
	public void testBefore() {
		AllenRelation beforeRelation = AllenRelation.getAllenRelation(this.span1, this.span4);
		assertRelationEquals(AllenRelation.BEFORE, beforeRelation);

		AllenRelation afterRelation = AllenRelation.getAllenRelation(this.span4, this.span1);
		assertRelationEquals(AllenRelation.AFTER, afterRelation);
	}

	@Test
	public void testStarts() {
		AllenRelation startsRelation = AllenRelation.getAllenRelation(this.span1, this.span2);
		assertRelationEquals(AllenRelation.STARTS, startsRelation);

		AllenRelation startsInvRelation = AllenRelation.getAllenRelation(this.span2, this.span1);
		assertRelationEquals(AllenRelation.STARTS_INV, startsInvRelation);
	}

	@Test
	public void testFinishes() {
		AllenRelation finishesRelation = AllenRelation.getAllenRelation(this.span4, this.span2);
		assertRelationEquals(AllenRelation.FINISHES, finishesRelation);

		AllenRelation finishesInvRelation = AllenRelation.getAllenRelation(this.span2, this.span4);
		assertRelationEquals(AllenRelation.FINISHES_INV, finishesInvRelation);
	}

	@Test
	public void testDuring() {
		AllenRelation duringRelation = AllenRelation.getAllenRelation(this.span3, this.span2);
		assertRelationEquals(AllenRelation.DURING, duringRelation);

		AllenRelation duringInvRelation = AllenRelation.getAllenRelation(this.span2, this.span3);
		assertRelationEquals(AllenRelation.DURING_INV, duringInvRelation);
	}

	@Test
	public void testOverlaps() {
		AllenRelation overlapsRelation = AllenRelation.getAllenRelation(this.span3, this.span4);
		assertRelationEquals(AllenRelation.OVERLAPS, overlapsRelation);

		AllenRelation overlapsInvRelation = AllenRelation.getAllenRelation(this.span4, this.span3);
		assertRelationEquals(AllenRelation.OVERLAPS_INV, overlapsInvRelation);
	}

	@Test
	public void testMeets() {
		AllenRelation meetsRelation = AllenRelation.getAllenRelation(this.span1, this.span3);
		assertRelationEquals(AllenRelation.MEETS, meetsRelation);

		AllenRelation meetsInvRelation = AllenRelation.getAllenRelation(this.span3, this.span1);
		assertRelationEquals(AllenRelation.MEETS_INV, meetsInvRelation);
	}

	public void assertRelationEquals(AllenRelation expectedRel, AllenRelation actualRel) {
		if (!actualRel.equals(expectedRel))
			fail("Expected Allen relation " + expectedRel.name() + ", but found " + actualRel.name());
	}
}

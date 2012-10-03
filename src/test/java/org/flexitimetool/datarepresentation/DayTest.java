package org.flexitimetool.datarepresentation;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class DayTest {

	private static final LocalDate DATE = new LocalDate(2013, 5, 27);
	private static final long LUNCH_DURATION = TimeUnit.MINUTES.toMillis(60);

	private static final long DURATION1 = TimeUnit.MINUTES.toMillis(24);
	private static final DateTime BEGIN_TIME1 = new DateTime(2013, 5, 27, 8, 23);
	private static final DateTime END_TIME1 = BEGIN_TIME1.plus(DURATION1);
	private static final WorkPeriod WORK_PERIOD1 = new WorkPeriod(BEGIN_TIME1,
			END_TIME1);

	private static final long DURATION2 = TimeUnit.MINUTES.toMillis(67);
	private static final DateTime BEGIN_TIME2 = new DateTime(2013, 5, 27, 14,
			18);
	private static final DateTime END_TIME2 = BEGIN_TIME2.plus(DURATION2);
	private static final WorkPeriod WORK_PERIOD2 = new WorkPeriod(BEGIN_TIME2,
			END_TIME2);

	private Day day;

	@Before
	public void setUp() {
		day = new Day(DATE);
	}

	@Test
	public void dayHasDate() {
		assertEquals(DATE, day.getDate());
	}

	@Test
	public void dayHasLunchTime() {
		assertEquals(0, day.getLunchBreakDuration());
		day.setLunchBreakDuration(LUNCH_DURATION);
		assertEquals(LUNCH_DURATION, day.getLunchBreakDuration());
	}

	@Test
	public void dayContainsWorkPeriods() throws Exception {
		assertEquals(0, day.getWorkPeriods().size());

		day.getWorkPeriods().add(WORK_PERIOD1);
		assertEquals(1, day.getWorkPeriods().size());

		day.getWorkPeriods().add(WORK_PERIOD2);
		assertEquals(2, day.getWorkPeriods().size());
	}

	@Test
	public void calculatesTotalWorkTime() throws Exception {
		assertEquals(0, day.getTotalWorkTime());

		day.getWorkPeriods().add(WORK_PERIOD1);
		assertEquals(DURATION1, day.getTotalWorkTime());

		day.getWorkPeriods().add(WORK_PERIOD2);
		assertEquals(DURATION1 + DURATION2, day.getTotalWorkTime());

		day.setLunchBreakDuration(LUNCH_DURATION);
		assertEquals(DURATION1 + DURATION2 - LUNCH_DURATION,
				day.getTotalWorkTime());
	}
}

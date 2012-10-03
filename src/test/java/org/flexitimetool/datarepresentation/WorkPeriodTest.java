package org.flexitimetool.datarepresentation;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class WorkPeriodTest {

	private static final DateTime BEGIN_DATE_TIME = new DateTime(2008, 1, 2,
			20, 16, 33);
	private static final LocalDate BEGIN_DATE = new LocalDate(BEGIN_DATE_TIME);
	private static final LocalTime BEGIN_TIME = new LocalTime(BEGIN_DATE_TIME);

	@Test
	public void totalTimeCorrectForDateTime() {
		long duration = TimeUnit.MINUTES.toMillis(0);
		while (duration < TimeUnit.HOURS.toMillis(24)) {
			DateTime endTime = BEGIN_DATE_TIME.plusMillis((int) duration);
			WorkPeriod period = new WorkPeriod(BEGIN_DATE_TIME, endTime);
			assertEquals(duration, period.getDuration());
			duration += TimeUnit.MINUTES.toMillis(1);
		}
	}

	@Test
	public void totalTimeCorrectForDatePlusTime() {
		long duration = TimeUnit.MINUTES.toMillis(0);
		while (duration < TimeUnit.HOURS.toMillis(24)) {
			LocalTime endTime = new LocalTime(
					BEGIN_DATE_TIME.plusMillis((int) duration));
			WorkPeriod period = new WorkPeriod(BEGIN_DATE, BEGIN_TIME, endTime);
			assertEquals(duration, period.getDuration());
			duration += TimeUnit.MINUTES.toMillis(1);
		}
	}

}

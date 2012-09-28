package org.flexitimetool.engine.timecalc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.flexitimetool.engine.idletime.IdleTimeUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class WorkTimeCalculatorTest {

	private static final DateTime TIME1 = new DateTime(2012, 01, 13, 7, 45, 23);
	private static final long IDLE1 = 20L;

	private WorkTimeCalculator wtc;
	private IdleTimeUtils idleTimeUtils;
	private Clock clock;

	@Before
	public void setUp() throws Exception {
		idleTimeUtils = mock(IdleTimeUtils.class);
		clock = mock(Clock.class);
		when(clock.getTime()).thenReturn(TIME1);
		wtc = new WorkTimeCalculator(idleTimeUtils, clock);
	}

	@Test
	public void getBeginTimeReturnsNullIfIdleTimeNotDecreased()
			throws Exception {
		wtc.getBeginTime();
		assertNull(wtc.getBeginTime());
		assertNull(wtc.getBeginTime());
	}

	@Test
	public void beginTimeReturnedAfterIdleTimeDecreased() throws Exception {
		mockClocks(TIME1.minus(1), IDLE1 + 1);
		wtc.getBeginTime();

		mockClocks(TIME1.plus(IDLE1), IDLE1);
		assertEquals(TIME1, wtc.getBeginTime());
		assertEquals(TIME1, wtc.getBeginTime());

		long timeElapsed = 10L;
		mockClocks(TIME1.plus(timeElapsed), IDLE1 + timeElapsed);
		assertEquals(TIME1, wtc.getBeginTime());

		timeElapsed += 10L;
		mockClocks(TIME1.plus(timeElapsed), IDLE1 + timeElapsed);
		assertEquals(TIME1, wtc.getBeginTime());
	}

	@Test
	public void endTimeNullUntilBeginTimeTriggered() {
		assertNull(wtc.getBeginTime());
		assertNull(wtc.getEndTime());
		mockActivityAt(TIME1, IDLE1);
		assertNotNull(wtc.getBeginTime());
		assertNotNull(wtc.getEndTime());
	}

	@Test
	public void endTimeUpdatedUntilActivityEnds() throws Exception {
		mockActivityAt(TIME1, IDLE1);
		assertNotNull(wtc.getBeginTime());

		assertEquals(TIME1, wtc.getEndTime());

		long timeElapsed = 0;
		DateTime lastActivityTime = TIME1.plus(++timeElapsed);
		mockActivityAt(lastActivityTime, IDLE1);
		assertEquals(lastActivityTime, wtc.getEndTime());

		lastActivityTime = TIME1.plus(++timeElapsed);
		mockActivityAt(lastActivityTime, IDLE1);
		assertEquals(lastActivityTime, wtc.getEndTime());

		timeElapsed = IDLE1;
		mockClocks(lastActivityTime.plus(timeElapsed), timeElapsed);
		assertEquals(lastActivityTime, wtc.getEndTime());

		++timeElapsed;
		mockClocks(lastActivityTime.plus(timeElapsed), timeElapsed);
		assertEquals(lastActivityTime, wtc.getEndTime());

		++timeElapsed;
		mockClocks(lastActivityTime.plus(timeElapsed), timeElapsed);
		assertEquals(lastActivityTime, wtc.getEndTime());
	}

	private void mockActivityAt(DateTime time, final long idleTime) {
		mockClocks(time.minus(1), idleTime + 1);
		wtc.getBeginTime();
		mockClocks(time.plus(idleTime), idleTime);
		wtc.getBeginTime();
	}

	private void mockClocks(DateTime time, long idleTime) {
		when(idleTimeUtils.getIdleTimeMillis()).thenReturn(idleTime);
		when(clock.getTime()).thenReturn(time);
	}
}

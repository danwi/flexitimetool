package org.flexitimetool.engine.loops;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.flexitimetool.engine.message.BeginTimeMessage;
import org.flexitimetool.engine.message.EndTimeMessage;
import org.flexitimetool.engine.message.Message;
import org.flexitimetool.engine.message.MessageReceiver;
import org.flexitimetool.engine.timecalc.WorkTimeCalculator;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class DayLoopTest {

	private static final DateTime BEGIN_TIME = new DateTime(2012, 12, 20, 8,
			22, 33);
	private Loopable<?> loopable;
	private WorkTimeCalculator wtc;
	private DateTime beginTime = null;
	private MessageBox box;

	private class MessageBox implements MessageReceiver {
		public int beginCalls;
		public int endCalls;
		public BeginTimeMessage lastBeginTime = null;
		public EndTimeMessage lastEndTime = null;

		@Override
		public void putMessage(Message message) {
			try {
				lastBeginTime = (BeginTimeMessage) message;
				++beginCalls;
				return;
			} catch (ClassCastException e) {
			}
			try {
				lastEndTime = (EndTimeMessage) message;
				++endCalls;
				return;
			} catch (ClassCastException e) {
			}
			throw new RuntimeException("Unsupported message class "
					+ message.getClass());

		}
	}

	@Before
	public void setUp() throws Exception {
		wtc = mock(WorkTimeCalculator.class);
		box = spy(new MessageBox());
		loopable = new DayLoop(wtc, box);
	}

	@Test
	public void shouldCallBeginTimeUntilBegunAndThenEndTime() {
		int loopStepsUntilBegun = 0;
		int loopStepsAfterBegun = 0;
		int endTimeUpdates = 0;
		DateTime lastActivityTime = null;

		assertStep(++loopStepsUntilBegun, loopStepsAfterBegun, endTimeUpdates,
				lastActivityTime);

		assertStep(++loopStepsUntilBegun, loopStepsAfterBegun, endTimeUpdates,
				lastActivityTime);

		lastActivityTime = triggerActivity(BEGIN_TIME);

		assertStep(++loopStepsUntilBegun, ++loopStepsAfterBegun,
				++endTimeUpdates, lastActivityTime);

		assertStep(loopStepsUntilBegun, ++loopStepsAfterBegun, endTimeUpdates,
				lastActivityTime);

		assertStep(loopStepsUntilBegun, ++loopStepsAfterBegun, endTimeUpdates,
				lastActivityTime);

		lastActivityTime = triggerActivity(lastActivityTime.plus(10));

		assertStep(loopStepsUntilBegun, ++loopStepsAfterBegun,
				++endTimeUpdates, lastActivityTime);

		assertStep(loopStepsUntilBegun, ++loopStepsAfterBegun, endTimeUpdates,
				lastActivityTime);

		lastActivityTime = triggerActivity(lastActivityTime.plus(11));

		assertStep(loopStepsUntilBegun, ++loopStepsAfterBegun,
				++endTimeUpdates, lastActivityTime);

		assertStep(loopStepsUntilBegun, ++loopStepsAfterBegun, endTimeUpdates,
				lastActivityTime);

		// endTime not updated
		triggerActivity(lastActivityTime);

		assertStep(loopStepsUntilBegun, ++loopStepsAfterBegun, endTimeUpdates,
				lastActivityTime);

		// endTime earlier than last endTime
		triggerActivity(lastActivityTime.minus(1));

		assertStep(loopStepsUntilBegun, ++loopStepsAfterBegun, endTimeUpdates,
				lastActivityTime);

		lastActivityTime = triggerActivity(lastActivityTime.plus(1));

		assertStep(loopStepsUntilBegun, ++loopStepsAfterBegun,
				++endTimeUpdates, lastActivityTime);

	}

	private void assertStep(int loopStepsUntilBegun, int loopStepsAfterBegun,
			int endTimeUpdates, DateTime lastActivityTime) {
		assertEquals(DayLoop.POLL_INTERVAL, loopable.loopStep());
		verify(wtc, times(loopStepsUntilBegun)).getBeginTime();
		verify(wtc, times(loopStepsAfterBegun)).getEndTime();
		assertEquals(beginTime == null ? 0 : 1, box.beginCalls);
		assertEquals(endTimeUpdates, box.endCalls);
		if (box.beginCalls > 0)
			assertEquals(beginTime, box.lastBeginTime.getTime());
		if (box.endCalls > 0)
			assertEquals(lastActivityTime, box.lastEndTime.getTime());
	}

	public void testShouldSendMessageWhenBeginTimeSet() throws Exception {

	}

	private DateTime triggerActivity(DateTime time) {
		if (beginTime == null)
			beginTime = time;
		when(wtc.getBeginTime()).thenReturn(beginTime);
		when(wtc.getEndTime()).thenReturn(time);
		return time;
	}
}

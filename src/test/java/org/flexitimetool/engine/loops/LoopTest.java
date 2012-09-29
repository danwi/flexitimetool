package org.flexitimetool.engine.loops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class LoopTest {

	private static final Object RESULT = new Object();
	private Loopable<Object> loopable;
	private Loop<?> loop;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() throws Exception {
		loopable = mock(Loopable.class);
		when(loopable.isLoopFinished()).thenReturn(true);
		when(loopable.loopStep()).thenReturn(0L);
		loop = new Loop<Object>(loopable);
	}

	@Test(timeout = 5000)
	public void loopIsRunnable() {
		loop.run();
	}

	@Test(timeout = 5000)
	public void callsLoopStepUntilFinished() throws Exception {
		when(loopable.isLoopFinished()).thenReturn(false, false, true);
		loop.run();
		verify(loopable, times(2)).loopStep();
	}

	@Test(timeout = 5000)
	public void sleepsBetweenLoops() throws Exception {
		when(loopable.isLoopFinished()).thenReturn(false, false, true);
		long sleepTime = 100L;
		when(loopable.loopStep()).thenReturn(sleepTime);
		long startTime = System.nanoTime();
		loop.run();
		long endTime = System.nanoTime();
		long timeDiffMillis = TimeUnit.NANOSECONDS
				.toMillis(endTime - startTime);
		int fuzzFactor = 2;
		assertTrue("Timediff should be greater than sleeptime*2: "
				+ timeDiffMillis, timeDiffMillis > sleepTime * 2 - fuzzFactor);
	}
	
	@Test
	public void returnsResultWhenDone() throws Exception {
		when(loopable.getLoopResult()).thenReturn(RESULT);
		
		when(loopable.isLoopFinished()).thenReturn(true);
		assertEquals(RESULT, loop.run());

		when(loopable.isLoopFinished()).thenReturn(false, false, true);
		assertEquals(RESULT, loop.run());
	}
}

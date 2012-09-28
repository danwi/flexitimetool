package org.flexitimetool.engine.timecalc;

import org.flexitimetool.engine.idletime.IdleTimeUtils;
import org.flexitimetool.engine.timecalc.Clock;
import org.flexitimetool.engine.timecalc.WorkTimeCalculator;

public class WorkTimeDemo {

	private static final int POLL_INTERVALL = 500;
	private static final long THREASHOLD = POLL_INTERVALL*2;
	private static boolean working = false;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		IdleTimeUtils idleTimeUtils = new IdleTimeUtils();
		Clock clock = new Clock();
		WorkTimeCalculator work = new WorkTimeCalculatorFactory(idleTimeUtils,
				clock).getWorkTimeCalculator();
		while (null == work.getBeginTime()) {
			Thread.sleep(POLL_INTERVALL);
		}
		working = true;
		System.out.println("Begun work at " + work.getBeginTime());
		while (true) {
			Thread.sleep(POLL_INTERVALL);
			if (working && idleTimeUtils.getIdleTimeMillis() > THREASHOLD) {
				System.out.println("Ended work at " + work.getEndTime());
				working = false;
			}
			if (idleTimeUtils.getIdleTimeMillis() < THREASHOLD) {
				working = true;
			}
		}
	}
}

package org.flexitimetool.engine.loops;

import java.util.concurrent.TimeUnit;

import org.flexitimetool.engine.message.BeginTimeMessage;
import org.flexitimetool.engine.message.EndTimeMessage;
import org.flexitimetool.engine.message.MessageReceiver;
import org.flexitimetool.engine.timecalc.WorkTimeCalculator;
import org.joda.time.DateTime;

public class DayLoop implements Loopable<Object> {

	public static final long POLL_INTERVAL = TimeUnit.MINUTES.toMillis(1);
	private final WorkTimeCalculator wtc;
	private final MessageReceiver msgReceiver;
	private DateTime beginTime = null;
	private DateTime lastEndTime = null;

	public DayLoop(WorkTimeCalculator wtc, MessageReceiver msgReceiver) {
		this.wtc = wtc;
		this.msgReceiver = msgReceiver;
	}

	@Override
	public long loopStep() {
		if (beginTime == null) {
			beginTime = wtc.getBeginTime();
			if (beginTime != null) {
				msgReceiver.putMessage(new BeginTimeMessage(beginTime));
			}
		}
		if (beginTime != null) {
			DateTime endTime = wtc.getEndTime();
			if ( lastEndTime == null || endTime.getMillis() > lastEndTime.getMillis()) {
				msgReceiver.putMessage(new EndTimeMessage(endTime));
				lastEndTime = endTime;
			}
		}
		return POLL_INTERVAL;
	}

	@Override
	public boolean isLoopFinished() {
		return false;
	}

	@Override
	public Object getLoopResult() {
		// TODO Auto-generated method stub
		return null;
	}

}

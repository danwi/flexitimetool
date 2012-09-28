package org.flexitimetool.engine.timecalc;

import org.flexitimetool.engine.idletime.IdleTimeUtils;
import org.joda.time.DateTime;

public class WorkTimeCalculator {

	private final IdleTimeUtils idleTimeUtils;
	private final Clock clock;
	private long lastIdleTime;
	private DateTime beginTime = null;
	private DateTime endTime = null;

	public WorkTimeCalculator(final IdleTimeUtils idleTimeUtils, final Clock clock) {
		this.idleTimeUtils = idleTimeUtils;
		this.clock = clock;
	}

	public DateTime getBeginTime() {
		long idleTime = idleTimeUtils.getIdleTimeMillis();
		if (beginTime == null && idleTime < lastIdleTime)
			beginTime = clock.getTime().minus(idleTime);
		lastIdleTime = idleTime;
		return beginTime;
	}

	public DateTime getEndTime() {
		long idleTime = idleTimeUtils.getIdleTimeMillis();
		if (beginTime != null)
			endTime = clock.getTime().minus(idleTime);
		lastIdleTime = idleTime;
		return endTime;
	}

}

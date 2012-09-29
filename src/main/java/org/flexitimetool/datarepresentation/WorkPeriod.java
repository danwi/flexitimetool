package org.flexitimetool.datarepresentation;

import org.joda.time.DateTime;

public class WorkPeriod {

	private final DateTime beginTime;
	private final DateTime endTime;

	public WorkPeriod(DateTime beginTime, DateTime endTime) {
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	public DateTime getBeginTime() {
		return beginTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public long getDuration() {
		return endTime.getMillis() - beginTime.getMillis();
	}

}

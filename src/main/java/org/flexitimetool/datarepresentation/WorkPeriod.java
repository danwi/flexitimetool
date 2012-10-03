package org.flexitimetool.datarepresentation;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class WorkPeriod {

	private final DateTime beginTime;
	private final DateTime endTime;

	public WorkPeriod(DateTime beginTime, DateTime endTime) {
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	public WorkPeriod(LocalDate date, LocalTime beginTime, LocalTime endTime) {
		this.beginTime = date.toDateTime(beginTime);
		if (endTime.getMillisOfDay() >= beginTime.getMillisOfDay()) {
			this.endTime = date.toDateTime(endTime);
		} else {
			this.endTime = date.plusDays(1).toDateTime(endTime);
		}
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

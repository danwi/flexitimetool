package org.flexitimetool.datarepresentation;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

public class Day {

	private final DateTime date;
	private final List<WorkPeriod> workPeriods = new LinkedList<WorkPeriod>();
	private long lunchBreakDuration;

	public Day(DateTime date) {
		this.date = date;
	}
	
	public DateTime getDate() {
		return date;
	}
	
	public List<WorkPeriod> getWorkPeriods() {
		return workPeriods;
	}

	public void setLunchBreakDuration(long lunchBreakDuration) {
		this.lunchBreakDuration = lunchBreakDuration;
	}

	public long getLunchBreakDuration() {
		return lunchBreakDuration;
	}

	public long getTotalWorkTime() {
		long workDuration = 0;
		for (WorkPeriod workPeriod : workPeriods) {
			workDuration += workPeriod.getDuration();
		}
		return workDuration - lunchBreakDuration;
	}
}

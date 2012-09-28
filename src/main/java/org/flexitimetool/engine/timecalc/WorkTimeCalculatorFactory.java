package org.flexitimetool.engine.timecalc;

import org.flexitimetool.engine.idletime.IdleTimeUtils;

public class WorkTimeCalculatorFactory {
	private IdleTimeUtils idleTimeUtils;
	private Clock clock;
	
	public WorkTimeCalculatorFactory() {
		this(new IdleTimeUtils(), new Clock());
	}
	
	public WorkTimeCalculatorFactory(IdleTimeUtils idleTimeUtils, Clock clock) {
		this.idleTimeUtils = idleTimeUtils;
		this.clock = clock;
	}

	public WorkTimeCalculator getWorkTimeCalculator() {
		return new WorkTimeCalculator(idleTimeUtils, clock);
	}
}

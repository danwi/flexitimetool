package org.flexitimetool.engine.message;

import org.joda.time.DateTime;

public abstract class TimeMessage implements Message {
	private final DateTime dateTime;

	TimeMessage(final DateTime dateTime) {
		this.dateTime = dateTime;
	}

	public DateTime getTime() {
		return dateTime;
	}
}

package org.flexitimetool.engine.message;

import org.joda.time.DateTime;

public class EndTimeMessage extends TimeMessage {
	public EndTimeMessage(final DateTime dateTime) {
		super(dateTime);
	}
}

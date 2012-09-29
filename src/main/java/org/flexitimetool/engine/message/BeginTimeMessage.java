package org.flexitimetool.engine.message;

import org.joda.time.DateTime;

public class BeginTimeMessage extends TimeMessage {
	public BeginTimeMessage(final DateTime dateTime) {
		super(dateTime);
	}
}

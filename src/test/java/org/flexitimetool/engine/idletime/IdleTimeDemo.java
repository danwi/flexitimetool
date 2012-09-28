package org.flexitimetool.engine.idletime;

import java.text.MessageFormat;

import org.flexitimetool.engine.idletime.IdleTimeUtils;



public class IdleTimeDemo {

	public static void main(String arg[]) throws InterruptedException {
		idleDisplayLoop();
	}

	private static void idleDisplayLoop() throws InterruptedException {
		IdleTimeUtils idleTimeUtils = new IdleTimeUtils();
		while(true) {
			Thread.sleep(1000);
			long idleTime = idleTimeUtils.getIdleTimeMillis();
			System.out.println(MessageFormat.format("Idle for {0} ms", idleTime));
		}
		
	}

}

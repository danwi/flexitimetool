package org.flexitimetool.idletime;

import java.text.MessageFormat;



public class W32IdleTimeDemo {

	public static void main(String arg[]) throws InterruptedException {
		idleDisplayLoop();
	}

	private static void idleDisplayLoop() throws InterruptedException {
		W32IdleTimeUtils idleTimeUtils = new W32IdleTimeUtils();
		while(true) {
			Thread.sleep(1000);
			long idleTime = idleTimeUtils.getIdleTimeMillis();
			System.out.println(MessageFormat.format("Idle for {0} ms", idleTime));
		}
		
	}

}

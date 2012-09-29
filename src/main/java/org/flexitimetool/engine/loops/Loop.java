package org.flexitimetool.engine.loops;

public class Loop<T> {

	private final Loopable<T> loopable;

	public Loop(Loopable<T> loopable) {
		this.loopable = loopable;
	}

	public T run() {
		while(!loopable.isLoopFinished()) {
			try {
				Thread.sleep(loopable.loopStep());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		return loopable.getLoopResult();
	}
}

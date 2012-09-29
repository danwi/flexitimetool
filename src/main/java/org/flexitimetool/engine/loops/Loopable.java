package org.flexitimetool.engine.loops;

public interface Loopable<T> {
	/**
	 * Run a loop step.
	 * 
	 * @return the number of milliseconds to sleep
	 */
	long loopStep();

	/**
	 * Get loop finished status.
	 * 
	 * @return true if loop is finished and {@link #getLoopResult()} can be called.
	 *         Return false if loop is not done and {@link #loopStep()} should
	 *         be called after sleeping.
	 */
	boolean isLoopFinished();

	/**
	 * Get the result of the loop.
	 * 
	 * @return the loop result if loop {@link #isLoopFinished()} is true,
	 *         undefined otherwise.
	 */
	T getLoopResult();
}

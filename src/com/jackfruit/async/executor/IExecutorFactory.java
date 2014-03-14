package com.jackfruit.async.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * Find a appropriate ExecutorService for specific message.
 * @author yaguang.xiao
 *
 */
public interface IExecutorFactory {

	/**
	 * Find a appropriate ExecutorService for the input message.
	 * @return
	 */
	ExecutorService getExecutor();

	/**
	 * Shutdown the executors after all queued tasks
	 * have been completed.
	 */
	void shutdown();
	
	/**
	 * Wait the executors to terminate.
	 * @throws InterruptedException 
	 */
	void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;
	
	/**
	 * Shutdown the executors immediately,
	 * cancel all queued tasks that haven't started yet,
	 * and attempt to interrupt the running tasks.
	 */
	void shutdownNow();
}

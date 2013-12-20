package com.jackfruit.async.executor;

import java.util.concurrent.ExecutorService;
/**
 * Find a appropriate ExecutorService for specific message.
 * @author yaguang.xiao
 *
 */
public interface IExecutorFactory {
	/**
	 * Find a appropriate ExecutorService for the input message.
	 * @param message
	 * @return
	 */
	ExecutorService getExecutor(Object message);
	
	/**
	 * Shutdown the executors.
	 */
	void shutdown();
}

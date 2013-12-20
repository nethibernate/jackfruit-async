package com.jackfruit.async.msg.executor;

import java.util.concurrent.ExecutorService;
/**
 * Find a appropriate ExecutorService for specific message.
 * @author yaguang.xiao
 *
 */
public interface IExecutorFactory {
	
	/**
	 * Wrap the message to be sent.
	 * @param message message to be sent.
	 * @return
	 */
	Object wrapMessage(Object message);
	
	/**
	 * Find a appropriate ExecutorService for the input message.
	 * @param message
	 * @return
	 */
	ExecutorService getExecutor(Object message);
	
	/**
	 * Unwrap the message received.
	 * @param message
	 * @return
	 */
	Object unwrapMessage(Object message);
	
	/**
	 * Shutdown the executors.
	 */
	void shutdown();
}

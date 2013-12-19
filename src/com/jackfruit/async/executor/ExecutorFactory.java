package com.jackfruit.async.executor;

import java.util.concurrent.ExecutorService;
/**
 * Find a appropriate ExecutorService.
 * @author yaguang.xiao
 *
 */
public interface ExecutorFactory {
	/**
	 * Find a appropriate ExecutorService for the input message.
	 * @param message
	 * @return
	 */
	ExecutorService getExecutor(Object message);
}

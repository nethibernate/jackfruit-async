package com.jackfruit.async.executor.impl;

import com.jackfruit.async.executor.IExecutorFactory;

/**
 * Messages come from the same server executes sequentially.
 * @author yaguang.xiao
 *
 */
public interface IBindServerIdExecutorFactory extends IExecutorFactory {
	
	/**
	 * Set the serverId of the server which sends the current message.
	 * @param serverId
	 */
	void setServerId(int serverId);
	
}

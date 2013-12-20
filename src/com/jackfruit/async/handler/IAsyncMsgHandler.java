package com.jackfruit.async.handler;
/**
 * Receive message from other servers.
 * @author yaguang.xiao
 *
 */
public interface IAsyncMsgHandler {
	
	/**
	 * Invoked when receive a message from other servers.
	 * <p><strong>This method is invoked in communication thread!</strong>
	 * @param message received message
	 * @return true if handled correctly.
	 */
	void onRecived(Object message);
	
}

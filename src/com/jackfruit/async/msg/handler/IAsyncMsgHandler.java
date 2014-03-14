package com.jackfruit.async.msg.handler;

import com.jackfruit.async.akka.session.ServerSession;

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
	void onRecived(Object message, ServerSession session);
	
}

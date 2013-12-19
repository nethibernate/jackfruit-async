package com.jackfruit.async.msghandle;
/**
 * Receive message from other servers.
 * @author yaguang.xiao
 *
 */
public interface MessageHandler {
	
	/**
	 * Invoked when receive a message from other servers.
	 * <p><strong>This method is invoked in communication thread!</strong>
	 * @param message received message
	 * @return true if handled correctly.
	 */
	void handle(Object message);
	
}

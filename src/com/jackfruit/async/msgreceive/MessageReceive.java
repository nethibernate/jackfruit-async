package com.jackfruit.async.msgreceive;
/**
 * Receive message from other servers.
 * @author yaguang.xiao
 *
 */
public interface MessageReceive {
	
	/**
	 * Invoked when receive a message from other servers.
	 * <p><strong>This method is invoked in communication thread!</strong>
	 * @param message received message
	 * @return true if handled correctly.
	 */
	boolean onRecevie(Object message);
	
}

package com.jackfruit.async;

import com.jackfruit.async.akka.MessageManager;
import com.jackfruit.async.executor.ExecutorFactory;
import com.jackfruit.async.msghandle.MessageHandler;
import com.jackfruit.async.session.ServerSession;

/**
 * Handle Message Receive and Message Send.
 * @author yaguang.xiao
 *
 */
public class MessageService {
	
	/**
	 * Set the ExecutorService's factory which
	 *  will produce ExecutorService to process messages.
	 * @param executorFactory
	 */
	public static void setExecutorFactory(ExecutorFactory factory) {
		MessageManager.Instance.setExecutorFactory(factory);
	}
	
	/**
	 * Set the message receive object.
	 * @param msgReceiveObject
	 */
	public static void setMessageRecieve(MessageHandler msgReceiveObject) {
		MessageManager.Instance.setMessageRecieve(msgReceiveObject);
	}
	
	/**
	 * Send message to the specific server actor.
	 * @param session server communicate actor system session. 
	 * @param message message to be sent.
	 */
	public static void sendMessage(ServerSession session, Object message) {
		MessageManager.Instance.sendMessage(session, message);
	}
}
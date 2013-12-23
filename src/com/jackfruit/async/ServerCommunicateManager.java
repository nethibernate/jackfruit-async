package com.jackfruit.async;

import com.jackfruit.async.akka.AkkaManager;
import com.jackfruit.async.akka.session.ServerSession;
import com.jackfruit.async.msg.MessageManager;
import com.jackfruit.async.msg.ServerMsg;
/**
 * This manage the whole communication system.
 * @author yaguang.xiao
 *
 */
public class ServerCommunicateManager {
	
	/** The unique Id of the current server. */
	private static int serverId;
	
	/**
	 * Start the communication system with specified configuration.
	 * @param config
	 */
	public static void start(ServerConfig config) {
		AkkaManager.buildAkkaManager(config);
		MessageManager.buildMessageManager(config.getDefaultExecutorThreadNum());
		serverId = config.getServerId();
	}
	
	/**
	 * Send message to remote server.
	 * @param message
	 * @param session
	 */
	public static void sendMessage(Object message, ServerSession session) {
		if(AkkaManager.Instance == null || MessageManager.Instance == null)
			return;
		
		AkkaManager.Instance.tell(wrapMessage(message), session);
	}
	
	/**
	 * Wrap the message to be sent.
	 * @param message
	 * @return
	 */
	private static ServerMsg wrapMessage(Object message) {
		return new ServerMsg(serverId, message);
	}
	
	/**
	 * Shutdown the communication system.
	 */
	public static void shutDown() {
		if(AkkaManager.Instance == null || MessageManager.Instance == null)
			return;
		
		AkkaManager.Instance.close();
		MessageManager.Instance.shutdown();
	}
	
}

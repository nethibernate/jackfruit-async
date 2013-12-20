package com.jackfruit.async;

import com.jackfruit.async.akka.AkkaManager;
import com.jackfruit.async.akka.session.ServerSession;
import com.jackfruit.async.msg.MessageManager;
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
		serverId = config.getServerId();
	}
	
	/**
	 * Send message to remote server.
	 * @param message
	 * @param session
	 */
	public static void sendMessage(Object message, ServerSession session) {
		if(AkkaManager.Instance == null)
			return;
		
		Object wrappedMsg = MessageManager.Instance.wrapMessage(message);
		AkkaManager.Instance.tell(wrappedMsg, session);
	}
	
	/**
	 * Shutdown the communication system.
	 */
	public static void shutDown() {
		if(AkkaManager.Instance == null)
			return;
		
		AkkaManager.Instance.close();
		MessageManager.Instance.shutdown();
	}
	
	/**
	 * Get the unique Server Id.
	 * @return
	 */
	public static int getServerId() {
		return serverId;
	}
	
}

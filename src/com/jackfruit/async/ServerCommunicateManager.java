package com.jackfruit.async;

import com.jackfruit.async.akka.AkkaManager;
import com.jackfruit.async.akka.MessageManager;
import com.jackfruit.async.config.ServerConfig;
/**
 * This manage the whole communication system.
 * @author yaguang.xiao
 *
 */
public class ServerCommunicateManager {
	
	/**
	 * Start the communication system with specified configuration.
	 * @param config
	 */
	public static void start(ServerConfig config) {
		AkkaManager.buildAkkaManager(config);
	}
	
	/**
	 * Shutdown the communication system.
	 */
	public static void shutDown() {
		AkkaManager.Instance.close();
		MessageManager.Instance.shutdown();
	}
	
}

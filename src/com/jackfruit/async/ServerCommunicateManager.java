package com.jackfruit.async;

import java.util.concurrent.TimeUnit;

import com.jackfruit.async.akka.AkkaManager;
import com.jackfruit.async.akka.session.ServerSession;
import com.jackfruit.async.executor.AsyncExecutor;
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
	/** The identifier of the current server. */
	private static ServerSession curServerSession;
	
	/**
	 * Start the communication system with specified configuration.
	 * @param config
	 */
	public static void start(final ServerConfig config) {
		AsyncExecutor.Instance.submit(new Runnable() {

			@Override
			public void run() {
				AkkaManager.buildAkkaManager(config);
				MessageManager.buildMessageManager(config.getDefaultExecutorThreadNum());
				serverId = config.getServerId();
				curServerSession = new ServerSession(config.getIp(), config.getPort());
			}
			
		});
	}
	
	/**
	 * Send message to remote server.
	 * @param message
	 * @param session
	 */
	public static void sendMessage(final Object message, final ServerSession session) {
		AsyncExecutor.Instance.submit(new Runnable() {

			@Override
			public void run() {
				if(AkkaManager.Instance == null || MessageManager.Instance == null)
					return;
				
				AkkaManager.Instance.tell(wrapMessage(message), session);
			}

		});
	}
	
	/**
	 * Wrap the message to be sent.
	 * @param message
	 * @return
	 */
	private static ServerMsg wrapMessage(Object message) {
		return new ServerMsg(serverId, curServerSession, message);
	}
	
	/**
	 * Shutdown the communication system.
	 */
	public static void shutDown() {
		AsyncExecutor.Instance.submit(new Runnable() {

			@Override
			public void run() {
				if(AkkaManager.Instance == null || MessageManager.Instance == null)
					return;
				
				AkkaManager.Instance.shutdown();
				MessageManager.Instance.shutdown();
			}
			
		});
		AsyncExecutor.Instance.shutdown();
	}
	
	/**
	 * Wait the communication system to terminate.
	 * @throws InterruptedException 
	 */
	public static void shutdownAndawaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
		AsyncExecutor.Instance.submit(new Runnable() {

			@Override
			public void run() {
				if(AkkaManager.Instance == null || MessageManager.Instance == null)
					return;
				
				AkkaManager.Instance.shutdown();
				MessageManager.Instance.shutdown();
				
				AkkaManager.Instance.awaitTermination();
				try {
					MessageManager.Instance.awaitTermination(timeout, unit);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			
		});
		AsyncExecutor.Instance.shutdown();
		AsyncExecutor.Instance.awaitTermination(timeout, unit);
	}
	
}

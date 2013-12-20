package com.jackfruit.async.akka;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;

import com.jackfruit.async.executor.ExecutorFactory;
import com.jackfruit.async.executor.impl.GetExecutorByServerId;
import com.jackfruit.async.msg.ServerMsg;
import com.jackfruit.async.msghandle.MessageHandler;
import com.jackfruit.async.session.ServerSession;
/**
 * 
 * @author yaguang.xiao
 *
 */
public class MessageManager {
	/** The only object of MessageManager. */
	public static MessageManager Instance = new MessageManager();
	/** Cached server actors' path. */
	private Map<String, ActorSelection> servers = new HashMap<String, ActorSelection>();
	/** Factory of executors which will execute the process of messages. */
	private ExecutorFactory executorFactory = new GetExecutorByServerId();
	/** It's onReceive method will be invoked when receive a message. */
	private MessageHandler msgHandler;
	
	/**
	 * Protect the instance of MessageManager
	 * from constructing outside this class.
	 */
	private MessageManager() {
		
	}
	
	/**
	 * Send message to the specific server actor.
	 * @param session server communicate actor system session. 
	 * @param message message to be sent.
	 */
	public void sendMessage(ServerSession session, Object message) {
		String identifier = PathBuilder.getIdentifier(session);
		ActorSelection path = servers.get(identifier);
		if(path == null) {
			path = PathBuilder.buildServerActorPath(session);
			servers.put(identifier, path);
		}
		
		path.tell(this.wrapMessage(message), AkkaManager.Instance.getServerAcotr());
	}
	
	/**
	 * Wrap the input message into a ServerMsg.
	 * @param msg Input message.
	 * @return
	 */
	private ServerMsg wrapMessage(Object msg) {
		return new ServerMsg(AkkaManager.Instance.serverId, msg);
	}
	
	/**
	 * Set the ExecutorService's factory which
	 *  will produce ExecutorService to process messages.
	 * @param executorFactory
	 */
	public synchronized void setExecutorFactory(ExecutorFactory executorFactory) {
		this.executorFactory = executorFactory;
	}
	
	/**
	 * Set the message handle object.
	 * @param msgReceiveObject
	 */
	public synchronized void setMessageRecieve(MessageHandler msgReceiveObject) {
		msgHandler = msgReceiveObject;
	}
	
	/**
	 * Handle the message that has been received.
	 * @param message received message
	 * @param sender message sender
	 * @return
	 */
	public synchronized boolean receiveMessage(final Object message, ActorRef sender) {
		if(executorFactory == null)
			return false;
		ExecutorService executor = executorFactory.getExecutor(message);
		if(executor == null)
			return false;
		executor.submit(new Runnable() {

			@Override
			public void run() {
				if(msgHandler != null) {
					msgHandler.handle(message);
				}
				
			}
			
		});
		return true;
	}
	
	/**
	 * Shutdown the MessageManager thread.
	 */
	public synchronized void shutdown() {
		this.executorFactory.shutdown();
	}
}

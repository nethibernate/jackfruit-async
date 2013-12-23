package com.jackfruit.async.msg;

import java.util.concurrent.ExecutorService;

import com.jackfruit.async.executor.IExecutorFactory;
import com.jackfruit.async.executor.impl.ExecutorFactoryImpl;
import com.jackfruit.async.executor.impl.IBindServerIdExecutorFactory;
import com.jackfruit.async.msg.handler.IAsyncMsgHandler;
import com.jackfruit.async.msg.handler.impl.AsyncMsgHandlerImpl;
/**
 * 
 * @author yaguang.xiao
 *
 */
public class MessageManager {
	/** The only object of MessageManager. */
	public static MessageManager Instance;
	
	/** Factory of executors which will execute the process of messages. */
	private IExecutorFactory executorFactory;
	/** It's onReceive method will be invoked when receive a message. */
	private IAsyncMsgHandler asyncMsgHandler = new AsyncMsgHandlerImpl();
	
	private MessageManager(int threadNum) {
		executorFactory = new ExecutorFactoryImpl(threadNum);
	}
	
	/**
	 * Build the only object of MessageManager.
	 * @param threadNum
	 */
	public static void buildMessageManager(int threadNum) {
		if(Instance == null) {
			Instance = new MessageManager(threadNum);
		}
	}
	
	/**
	 * Handle the message that has been received.
	 * @param message received message
	 * @param sender message sender
	 * @return
	 */
	public boolean receiveMessage(final Object message) {
		if(executorFactory == null)
			return false;
		
		if(!(message instanceof ServerMsg))
			return false;
		final ServerMsg serverMsg = (ServerMsg)message;
		if(executorFactory instanceof IBindServerIdExecutorFactory) {
			((IBindServerIdExecutorFactory)executorFactory).setServerId(serverMsg.serverId);
		}
		
		ExecutorService executor = executorFactory.getExecutor();
		if(executor == null)
			return false;
		
		executor.submit(new Runnable() {

			@Override
			public void run() {
				if(asyncMsgHandler != null) {
					asyncMsgHandler.onRecived(serverMsg.msg);
				}
				
			}
			
		});
		return true;
	}
	
	/**
	 * Set the ExecutorService's factory which
	 *  will produce ExecutorService to process messages.
	 * @param executorFactory
	 */
	public void setExecutorFactory(IExecutorFactory executorFactory) {
		this.executorFactory = executorFactory;
	}
	
	/**
	 * Set the message handler object.
	 * @param asyncMsgHandler
	 */
	public void setAsyncMsgHandler(IAsyncMsgHandler asyncMsgHandler) {
		this.asyncMsgHandler = asyncMsgHandler;
	}
	
	/**
	 * Shutdown the MessageManager thread.
	 */
	public void shutdown() {
		this.executorFactory.shutdown();
	}
}

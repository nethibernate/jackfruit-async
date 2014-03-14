package com.jackfruit.async.msg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.jackfruit.async.executor.AsyncExecutor;
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
	public void receiveMessage(final Object message) {
		AsyncExecutor.Instance.submit(new Runnable() {

			@Override
			public void run() {
				handleMessageInAsyncExecutor(message);
			}
			
		});
	}
	
	/**
	 * Handle the message that has been received in AsyncExecutor.
	 * @param message
	 */
	private void handleMessageInAsyncExecutor(final Object message) {
		if(executorFactory == null)
			return;
		
		if(!(message instanceof ServerMsg))
			return;
		final ServerMsg serverMsg = (ServerMsg)message;
		if(executorFactory instanceof IBindServerIdExecutorFactory) {
			((IBindServerIdExecutorFactory)executorFactory).setServerId(serverMsg.serverId);
		}
		
		ExecutorService executor = executorFactory.getExecutor();
		if(executor == null)
			return;
		
		executor.submit(new Runnable() {

			@Override
			public void run() {
				if(asyncMsgHandler != null) {
					asyncMsgHandler.onRecived(serverMsg.msg, serverMsg.serverSession);
				}
				
			}
			
		});
	}
	
	/**
	 * Set the ExecutorService's factory which
	 *  will produce ExecutorService to process messages.
	 * @param executorFactory
	 */
	public void setExecutorFactory(final IExecutorFactory executorFactory) {
		AsyncExecutor.Instance.submit(new Runnable() {

			@Override
			public void run() {
				MessageManager.Instance.executorFactory = executorFactory;
			}
			
		});
	}
	
	/**
	 * Set the message handler object.
	 * @param asyncMsgHandler
	 */
	public static void setAsyncMsgHandler(final IAsyncMsgHandler asyncMsgHandler) {
		AsyncExecutor.Instance.submit(new Runnable() {

			@Override
			public void run() {
				MessageManager.Instance.asyncMsgHandler = asyncMsgHandler;
			}
			
		});
	}
	
	/**
	 * Shutdown the MessageManager thread.
	 */
	public void shutdown() {
		this.executorFactory.shutdown();
	}
	
	/**
	 * Wait the MessageManager threads to terminate.
	 * @param timeout
	 * @param unit
	 * @throws InterruptedException 
	 */
	public void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		this.executorFactory.awaitTermination(timeout, unit);
	}
}

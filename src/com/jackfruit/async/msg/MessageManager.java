package com.jackfruit.async.msg;

import java.util.concurrent.ExecutorService;

import com.jackfruit.async.msg.executor.IExecutorFactory;
import com.jackfruit.async.msg.executor.impl.ExecutorFactoryImpl;
import com.jackfruit.async.msg.handler.IAsyncMsgHandler;
import com.jackfruit.async.msg.handler.impl.AsyncMsgHandlerImpl;
/**
 * 
 * @author yaguang.xiao
 *
 */
public enum MessageManager {
	/** The only object of MessageManager. */
	Instance;
	
	/** Factory of executors which will execute the process of messages. */
	private IExecutorFactory executorFactory = new ExecutorFactoryImpl();
	/** It's onReceive method will be invoked when receive a message. */
	private IAsyncMsgHandler asyncMsgHandler = new AsyncMsgHandlerImpl();
	
	/**
	 * Wrap the message to be sent.
	 * @param message
	 * @return
	 */
	public Object wrapMessage(Object message) {
		if(this.executorFactory == null)
			return message;
		return this.executorFactory.wrapMessage(message);
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
		ExecutorService executor = executorFactory.getExecutor(message);
		if(executor == null)
			return false;
		
		final Object originalMsg = this.executorFactory.unwrapMessage(message);
		executor.submit(new Runnable() {

			@Override
			public void run() {
				if(asyncMsgHandler != null) {
					asyncMsgHandler.onRecived(originalMsg);
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

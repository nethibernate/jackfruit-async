package com.jackfruit.async.msg.executor.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jackfruit.async.ServerCommunicateManager;
import com.jackfruit.async.msg.executor.IExecutorFactory;

public class ExecutorFactoryImpl implements IExecutorFactory {

	private final ExecutorService[] serverIdBindExecutors;
	private int count = 0;
	private Map<Integer, Integer> serverMap = new HashMap<Integer, Integer>();
	
	public ExecutorFactoryImpl() {
		serverIdBindExecutors = new ExecutorService[10];
		for(int i = 0;i < serverIdBindExecutors.length;i ++) {
			serverIdBindExecutors[i] = Executors.newSingleThreadExecutor();
		}
	}
	
	@Override
	public Object wrapMessage(Object message) {
		int serverId = ServerCommunicateManager.getServerId();
		return new ServerMsg(serverId, message);
	}
	
	@Override
	public ExecutorService getExecutor(Object message) {
		if(!(message instanceof ServerMsg))
			return null;
		
		ServerMsg serverMsg = (ServerMsg)message;
		int serverId = serverMsg.serverId;
		Integer arriveId = serverMap.get(serverId);
		if(arriveId == null) {
			this.count ++;
			arriveId = this.count;
			this.serverMap.put(serverId, arriveId);
		}
		
		return this.getExecutorById(arriveId);
	}
	
	/**
	 * Find the corresponding ExecutorService.
	 * @param id
	 * @return
	 */
	private ExecutorService getExecutorById(int id) {
		int executorIndex = id % this.serverIdBindExecutors.length;
		executorIndex = executorIndex < 0? 0 : executorIndex;
		return this.serverIdBindExecutors[executorIndex];
	}
	
	@Override
	public Object unwrapMessage(Object message) {
		if(!(message instanceof ServerMsg))
			return message;
		return ((ServerMsg)message).msg;
	}

	@Override
	public void shutdown() {
		for(int i = 0;i < serverIdBindExecutors.length;i ++) {
			this.serverIdBindExecutors[i].shutdown();
		}
	}

}

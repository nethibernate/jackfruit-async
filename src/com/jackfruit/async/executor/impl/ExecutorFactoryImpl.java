package com.jackfruit.async.executor.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorFactoryImpl implements IBindServerIdExecutorFactory {

	private final ExecutorService[] serverIdBindExecutors;
	private int count = 0;
	private Map<Integer, Integer> serverMap = new HashMap<Integer, Integer>();
	private int curServerId;
	
	public ExecutorFactoryImpl(int threadNum) {
		serverIdBindExecutors = new ExecutorService[threadNum];
		for(int i = 0;i < serverIdBindExecutors.length;i ++) {
			serverIdBindExecutors[i] = Executors.newSingleThreadExecutor();
		}
	}
	
	@Override
	public ExecutorService getExecutor() {
		Integer arriveId = serverMap.get(curServerId);
		if(arriveId == null) {
			this.count ++;
			arriveId = this.count;
			this.serverMap.put(curServerId, arriveId);
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
	public void shutdown() {
		for(int i = 0;i < serverIdBindExecutors.length;i ++) {
			this.serverIdBindExecutors[i].shutdown();
		}
	}
	
	@Override
	public void shutdownNow() {
		for(int i = 0;i < serverIdBindExecutors.length;i ++) {
			this.serverIdBindExecutors[i].shutdownNow();
		}
	}

	@Override
	public void setServerId(int serverId) {
		this.curServerId = serverId;
	}

}

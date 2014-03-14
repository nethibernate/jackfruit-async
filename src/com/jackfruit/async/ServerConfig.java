package com.jackfruit.async;

/**
 * A common use for server configuration.
 * 
 * @author yaguang.xiao
 * 
 */
public class ServerConfig {
	/** 服务器的唯一标示Id */
	private int serverId;
	/** 服务器的Ip */
	private String ip;
	/** 服务器的端口 */
	private int port;
	/** 默认线程池的大小 */
	private int defaultExecutorThreadNum;

	public int getServerId() {
		return serverId;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public int getDefaultExecutorThreadNum() {
		return defaultExecutorThreadNum;
	}
	
	public void setDefaultExecutorThreadNum(int defaultExecutorThreadNum) {
		this.defaultExecutorThreadNum = defaultExecutorThreadNum;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}
}

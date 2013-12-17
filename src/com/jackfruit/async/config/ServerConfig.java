package com.jackfruit.async.config;

/**
 * A common use for server configuration.
 * 
 * @author yaguang.xiao
 * 
 */
public class ServerConfig {
	/** 服务器类型 */
	private int serverType;
	/** 服务器的唯一标示Id */
	private int serverId;
	/** 服务器的名字 */
	private String serverName = "Server";
	/** 服务器的Ip */
	private String ip;
	/** 服务器的端口 */
	private int port;
	/** 服务器之间通信模块构建成功之后的汇报路径 */
	private String reportPath;

	public int getServerType() {
		return serverType;
	}

	public int getServerId() {
		return serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setServerType(int serverType) {
		this.serverType = serverType;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

}

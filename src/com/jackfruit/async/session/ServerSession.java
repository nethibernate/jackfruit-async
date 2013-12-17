package com.jackfruit.async.session;

/**
 * A session used to communication between servers.
 * 
 * @author yaguang.xiao
 * 
 */
public class ServerSession {
	private String host;
	private int port;

	public ServerSession(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

}

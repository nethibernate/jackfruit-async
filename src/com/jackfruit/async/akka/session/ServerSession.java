package com.jackfruit.async.akka.session;

import java.io.Serializable;

/**
 * A session used to communication between servers.
 * 
 * @author yaguang.xiao
 * 
 */
public class ServerSession implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

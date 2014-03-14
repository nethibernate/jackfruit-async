package com.jackfruit.async.msg;

import java.io.Serializable;

import com.jackfruit.async.akka.session.ServerSession;

/**
 * Message sent and received by the communication framework.
 * @author yaguang.xiao
 *
 */
public class ServerMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The unique id of the server. */
	public final int serverId;
	/** The sender of the message */
	public final ServerSession serverSession;
	/** The message passed by the server */
	public final Object msg;
	
	public ServerMsg(int serverId, ServerSession serverSession, Object msg) {
		this.serverId = serverId;
		this.serverSession = serverSession;
		this.msg = msg;
	}
}

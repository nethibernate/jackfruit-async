package com.jackfruit.async.msg.executor.impl;
/**
 * Message sent and received by the communication framework.
 * @author yaguang.xiao
 *
 */
public class ServerMsg {
	/** The unique id of the server. */
	public final int serverId;
	/** The message passed by the server */
	public final Object msg;
	
	public ServerMsg(int serverId, Object msg) {
		this.serverId = serverId;
		this.msg = msg;
	}
}

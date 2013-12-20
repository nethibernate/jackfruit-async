package com.jackfruit.async.akka.actor;

import com.jackfruit.async.msg.MessageManager;

import akka.actor.UntypedActor;

/**
 * The actor to receive and send message on a server.
 * @author yaguang.xiao
 *
 */
public class ServerActor extends UntypedActor {
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if(!MessageManager.Instance.receiveMessage(msg)) {
			unhandled(msg);
		}
	}

}

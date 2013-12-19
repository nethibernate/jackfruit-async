package com.jackfruit.async.akka;

import akka.actor.UntypedActor;

/**
 * The actor to receive and send message on a server.
 * @author yaguang.xiao
 *
 */
public class ServerActor extends UntypedActor {
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if(!MessageManager.Instance.receiveMessage(msg, getSender())) {
			unhandled(msg);
		}
	}

}

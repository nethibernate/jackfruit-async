package com.jackfruit.async.akka;

import akka.actor.UntypedActor;

/**
 * The actor to receive and send message on a server.
 * @author yaguang.xiao
 *
 */
class DServerActior extends UntypedActor {
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if(!MessageHandler.receiveMessage(msg, getSender())) {
			unhandled(msg);
		}
	}

}

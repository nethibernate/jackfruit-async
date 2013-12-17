package com.jackfruit.async.akka;

import akka.actor.ActorSelection;

import com.jackfruit.async.session.ServerSession;

/**
 * ActorPath related method.
 * @author yaguang.xiao
 *
 */
class DPathBuilder {
	/**
	 * Build the server actor's path from specific session.
	 * @param session server session
	 * @return the server actor's path
	 */
	static ActorSelection buildServerActorPath(ServerSession session) {
		StringBuilder path = new StringBuilder();
		path.append("akka.tcp://");
		path.append(ServerCommunicateManager.ACTOR_SYSTEM_NAME);
		path.append("@");
		path.append(session.getHost());
		path.append(":");
		path.append(session.getPort());
		path.append("/");
		path.append(ServerCommunicateManager.SERVER_ACTOR_NAME);
		
		return ServerCommunicateManager.getActorSystem().actorSelection(path.toString());
	}
	
	/**
	 * Get the server actor's identifier from specific session.
	 * @param session session
	 * @return the server actor's identifier
	 */
	static String getIdentifier(ServerSession session) {
		StringBuilder identifier = new StringBuilder();
		identifier.append(session.getHost());
		identifier.append(":");
		identifier.append(session.getPort());
		return identifier.toString();
	}
}

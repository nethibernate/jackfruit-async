package com.jackfruit.async.akka;

import akka.actor.ActorSelection;

import com.jackfruit.async.session.ServerSession;

/**
 * ActorPath related method.
 * @author yaguang.xiao
 *
 */
public class PathBuilder {
	/**
	 * Build the server actor's path from specific session.
	 * @param session server session
	 * @return the server actor's path
	 */
	public static ActorSelection buildServerActorPath(ServerSession session) {
		StringBuilder path = new StringBuilder();
		path.append("akka.tcp://");
		path.append(AkkaManager.ACTOR_SYSTEM_NAME);
		path.append("@");
		path.append(session.getHost());
		path.append(":");
		path.append(session.getPort());
		path.append("/");
		path.append(AkkaManager.SERVER_ACTOR_NAME);
		
		return AkkaManager.Instance.getActorSystem().actorSelection(path.toString());
	}
	
	/**
	 * Get the server actor's identifier from specific session.
	 * @param session session
	 * @return the server actor's identifier
	 */
	public static String getIdentifier(ServerSession session) {
		StringBuilder identifier = new StringBuilder();
		identifier.append(session.getHost());
		identifier.append(":");
		identifier.append(session.getPort());
		return identifier.toString();
	}
}

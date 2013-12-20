package com.jackfruit.async.akka.path;

import com.jackfruit.async.akka.session.ServerSession;

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
	public static String buildServerActorPath(ServerSession session, String systemName, String actorName) {
		StringBuilder path = new StringBuilder();
		path.append("akka.tcp://");
		path.append(systemName);
		path.append("@");
		path.append(session.getHost());
		path.append(":");
		path.append(session.getPort());
		path.append("/");
		path.append(actorName);
		
		return path.toString();
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

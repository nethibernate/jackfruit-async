package com.jackfruit.async.akka;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.jackfruit.async.ServerConfig;
import com.jackfruit.async.akka.actor.ServerActor;
import com.jackfruit.async.akka.config.AkkaConfigBuilder;
import com.jackfruit.async.akka.path.PathBuilder;
import com.jackfruit.async.akka.session.ServerSession;
import com.typesafe.config.Config;
/**
 * Responsible for starting and managing the communication actor system.
 * @author yaguang.xiao
 *
 */
public class AkkaManager {
	/** The only instance of AkkaManager. */
	public static AkkaManager Instance;
	
	/** An ActorSystem reside on the server. */
	private ActorSystem actorSystem;
	/** The name of the ActorSystem. */
	public static final String ACTOR_SYSTEM_NAME = "serverActorSystem";
	
	/** The Actor that responsible for receive and send messages. */
	private ActorRef serverActor;
	/** The name of the Actor that responsible for receive and send messages.*/
	public static final String SERVER_ACTOR_NAME = "serverActor";
	
	/** Cached server actors' path. */
	private Map<String, ActorSelection> servers = new HashMap<String, ActorSelection>();
	
	private AkkaManager(ServerConfig config) {
		Config akkaSysConfig = AkkaConfigBuilder.build(
				config.getIp(), config.getPort());
		actorSystem = ActorSystem.create(ACTOR_SYSTEM_NAME, akkaSysConfig);
		serverActor = actorSystem.actorOf(Props.create(ServerActor.class), SERVER_ACTOR_NAME);
	}
	
	/**
	 * Get the only one akka manager.
	 * @param config
	 * @return
	 */
	public static void buildAkkaManager(ServerConfig config) {
		if(Instance == null) {
			Instance = new AkkaManager(config);
		}
	}
	
	/**
	 * Send the message to remote server.
	 * @param message
	 * @param session
	 */
	public void tell(Object message, ServerSession session) {
		String identifier = PathBuilder.getIdentifier(session);
		ActorSelection path = servers.get(identifier);
		if(path == null) {
			path = PathBuilder.buildServerActorPath(session);
			servers.put(identifier, path);
		}
		
		path.tell(message, AkkaManager.Instance.getServerAcotr());
	}
	
	/**
	 * Shutdown the actor system.
	 */
	public void close() {
		actorSystem.shutdown();
	}
	
	/**
	 * Get ActorSystem reside on the server.
	 * @return
	 */
	public ActorSystem getActorSystem() {
		return actorSystem;
	}
	
	/**
	 * Get the actor that responsible for receive and send messages.
	 * @return
	 */
	public ActorRef getServerAcotr() {
		return serverActor;
	}
	
}

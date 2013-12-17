package com.jackfruit.async.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.jackfruit.async.config.ServerConfig;
import com.typesafe.config.Config;
/**
 * This manage the whole ActorSystem which
 * used to communicate between server.
 * @author yaguang.xiao
 *
 */
public class ServerCommunicateManager {
	/**
	 * An ActorSystem reside on the server.
	 */
	private static ActorSystem actorSystem;
	/**
	 * The name of the ActorSystem.
	 */
	static final String ACTOR_SYSTEM_NAME = "serverActorSystem";
	
	/**
	 * The Actor that responsible for receive and send messages.
	 */
	private static ActorRef serverActor;
	/**
	 * The name of the Actor that responsible for receive and send messages.
	 */
	static final String SERVER_ACTOR_NAME = "serverActor";
	
	/**
	 * Start the akka system with specified configuration.
	 * @param config
	 */
	public static void start(ServerConfig config) {
		Config akkaSysConfig = DAkkaConfigBuilder.build(
				config.getIp(), config.getPort());
		actorSystem = ActorSystem.create(ACTOR_SYSTEM_NAME, akkaSysConfig);
		serverActor = actorSystem.actorOf(Props.create(DServerActior.class), SERVER_ACTOR_NAME);
	}
	
	/**
	 * Get ActorSystem reside on the server.
	 * @return
	 */
	static ActorSystem getActorSystem() {
		return actorSystem;
	}
	
	/**
	 * Get the actor that responsible for receive and send messages.
	 * @return
	 */
	static ActorRef getServerAcotr() {
		return serverActor;
	}
}

package com.jackfruit.async.akka;

import com.jackfruit.async.akka.config.AkkaConfigBuilder;
import com.jackfruit.async.config.ServerConfig;
import com.typesafe.config.Config;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
/**
 * Responsible for starting and managing the communication actor system.
 * @author yaguang.xiao
 *
 */
public class AkkaManager {
	/** The only instance of AkkaManager. */
	public static AkkaManager Instance;
	
	/** Current server's unique id. */
	public final int serverId;
	
	/** An ActorSystem reside on the server. */
	private ActorSystem actorSystem;
	/** The name of the ActorSystem. */
	public static final String ACTOR_SYSTEM_NAME = "serverActorSystem";
	
	/** The Actor that responsible for receive and send messages. */
	private ActorRef serverActor;
	/** The name of the Actor that responsible for receive and send messages.*/
	public static final String SERVER_ACTOR_NAME = "serverActor";
	
	private AkkaManager(ServerConfig config) {
		this.serverId = config.getServerId();
		Config akkaSysConfig = AkkaConfigBuilder.build(
				config.getIp(), config.getPort());
		actorSystem = ActorSystem.create(ACTOR_SYSTEM_NAME, akkaSysConfig);
		serverActor = actorSystem.actorOf(Props.create(ServerActor.class), SERVER_ACTOR_NAME);
		
		reportAfterStart(config.getReportPath(), config.getServerName());
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
	 * Shutdown the actor system.
	 */
	public void close() {
		actorSystem.shutdown();
	}
	
	/**
	 * Report to the target actor that current communication framework is started.
	 * @param reportPath actor path
	 * @param serverName current server name
	 */
	private void reportAfterStart(String reportPath, String serverName) {
		ActorSelection actorSelection = actorSystem.actorSelection(reportPath);
		actorSelection.tell(serverName + " is started.", serverActor);
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

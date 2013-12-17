package com.jackfruit.async.akka;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;

import com.jackfruit.async.msgreceive.MessageReceive;
import com.jackfruit.async.session.ServerSession;
/**
 * Message Send and Message Receive.
 * @author yaguang.xiao
 *
 */
public class MessageHandler {
	/** Cached server actors' path. */
	private static Map<String, ActorSelection> servers = new HashMap<String, ActorSelection>();
	/** It's onReceive method will be invoked when receive a message. */
	private static MessageReceive msgReceive;
	
	/**
	 * Send message to the specific server actor.
	 * @param session server communicate actor system session. 
	 * @param message message to be sent.
	 */
	public static void sendMessage(ServerSession session, Object message) {
		String identifier = DPathBuilder.getIdentifier(session);
		ActorSelection path = servers.get(identifier);
		if(path == null) {
			path = DPathBuilder.buildServerActorPath(session);
			servers.put(identifier, path);
		}
		
		path.tell(message, ServerCommunicateManager.getServerAcotr());
	}
	
	/**
	 * Handle the message that has been received.
	 * @param message received message
	 * @param sender message sender
	 * @return
	 */
	static boolean receiveMessage(Object message, ActorRef sender) {
		if(msgReceive == null)
			return false;
		return msgReceive.onRecevie(message);
	}
	
	/**
	 * Set the message receive object.
	 * @param msgReceiveObject
	 */
	static void setMessageRecieve(MessageReceive msgReceiveObject) {
		msgReceive = msgReceiveObject;
	}
}
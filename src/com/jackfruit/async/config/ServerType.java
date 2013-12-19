package com.jackfruit.async.config;

/**
 * All server type such as
 * GameServer, AgentServer, DBServer, CacheServer
 * will be define here.
 * @author yaguang.xiao
 *
 */
public enum ServerType {
	GameServer,
	AgentServer,
	DBServer,
	CacheServer
	;
	
	/**
	 * Check whether the type name is correct.
	 * @param typeName
	 * @return
	 */
	public static boolean check(String typeName) {
		return get(typeName) != null;
	}
	
	/**
	 * Get ServerType by name.
	 * @param typeName
	 * @return
	 */
	public static ServerType get(String typeName) {
		for(ServerType type : ServerType.values()) {
			if(type.toString().equals(typeName))
				return type;
		}
		return null;
	}
}

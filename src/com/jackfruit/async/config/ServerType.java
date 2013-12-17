package com.jackfruit.async.config;

import java.util.List;

import com.jackfruit.util.enums.EnumUtil;
import com.jackfruit.util.enums.IndexedEnum;

/**
 * All server type such as
 * GameServer, AgentServer, DBServer, CacheServer
 * will be define here.
 * @author yaguang.xiao
 *
 */
public enum ServerType implements IndexedEnum {
	GameServer(0),
	AgentServer(1),
	DBServer(2),
	CacheServer(3)
	;

	public final int index;
	
	ServerType(int index) {
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return this.index;
	}
	
	private static final List<ServerType> values = IndexedEnumUtil
			.toIndexes(ServerType.values());
	
	/**
	 * Get the specific ServerType by index.
	 * @param index
	 * @return
	 */
	public static ServerType valueOf(int index) {
		return EnumUtil.valueOf(values, index);
	}
	
	/**
	 * Check whether the index is valid.
	 * @param index
	 * @return
	 */
	public static boolean check(int index) {
		return EnumUtil.valueOf(values, index) != null;
	}
}

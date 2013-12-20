package com.jackfruit.async.msg.handler.impl;

import com.jackfruit.async.msg.handler.IAsyncMsgHandler;
/**
 * A simple implement of IAsyncMsgHandler.
 * @author yaguang.xiao
 *
 */
public class AsyncMsgHandlerImpl implements IAsyncMsgHandler {

	@Override
	public void onRecived(Object message) {
		System.out.println(message);
	}

}

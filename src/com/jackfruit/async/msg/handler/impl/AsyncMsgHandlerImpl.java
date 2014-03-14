package com.jackfruit.async.msg.handler.impl;

import com.jackfruit.async.akka.session.ServerSession;
import com.jackfruit.async.msg.handler.IAsyncMsgHandler;
/**
 * A simple implement of IAsyncMsgHandler.
 * @author yaguang.xiao
 *
 */
public class AsyncMsgHandlerImpl implements IAsyncMsgHandler {

	@Override
	public void onRecived(Object message, ServerSession session) {
		System.out.println(message);
	}
}

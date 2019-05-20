package com.gene.net;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gene.connect.ConnectMgr;
import com.gene.connect.UserConnect;
import com.gene.exec.CmdTask;
import com.gene.message.PBMessage;
import com.gene.net.commond.Command;
import com.gene.net.commond.CommandMgr;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class RequestHandler extends ChannelInboundHandlerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LOGGER.info("[client] recieved a connector from " + ctx.channel().remoteAddress());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		PBMessage packet = (PBMessage) msg;
		handle(ctx.channel(), packet);
	}
	
	public static void handle(Channel channel, PBMessage packet) {
		short code = packet.getCode();
		Command cmd = CommandMgr.getCommand(code);
		if (cmd == null) {
			LOGGER.error("not found cmd , code : " + code + " , userId : " + packet.getPlayerId());
			return;
		}
		UserConnect userConnect = ConnectMgr.get(channel);
		if(userConnect == null) {
			userConnect = ConnectMgr.add(channel);
		}
		userConnect.enqueue(new CmdTask(cmd, channel, packet, userConnect.getCmdTaskQueue()));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			offlineHandle(ctx.channel(), "exceptionCaught");
			cause.printStackTrace();
			super.exceptionCaught(ctx, cause);
	}
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		offlineHandle(ctx.channel(), "handlerRemoved");
		super.handlerRemoved(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			offlineHandle(ctx.channel(), "userEventTriggered");
		}
		super.userEventTriggered(ctx, evt);
	}

	private void offlineHandle(Channel channel, String typeName) {
		ConnectMgr.remove(channel);
		channel.close();
	}
}

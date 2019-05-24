package com.gene.net;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gene.cmd.Command;
import com.gene.connect.ConnectService;
import com.gene.connect.User;
import com.gene.exec.CmdTask;
import com.gene.message.PBMessage;
import com.gene.message.ReqCode;
import com.gene.net.commond.CommandService;
import com.gene.util.ErrorUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class RequestHandler extends ChannelInboundHandlerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		User user = ConnectService.getInst().get(ctx.channel());
		if(user == null) {
			ConnectService.getInst().add(ctx.channel());
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		PBMessage packet = (PBMessage) msg;
		handle(ctx.channel(), packet);
	}
	
	public static void handle(Channel channel, PBMessage packet) {
		short os = packet.getOs();
		short code = packet.getCode();
		Command cmd = CommandService.getInst().getCommand(os, code);
		if (cmd == null) {
			LOGGER.error("not found cmd , os:{}, code:{} ", os, code);
			return;
		}
		User user = ConnectService.getInst().get(channel);
		if(user == null) {
			ErrorUtil.error(channel, packet, "RequestHandler没有登录");
			return;
		}
		user.enqueue(new CmdTask(cmd, user, packet, user.getCmdTaskQueue()));
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
		ConnectService.getInst().remove(channel);
		channel.close();
	}
}

package com.gene.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gene.client.service.ClientService;
import com.gene.message.PBMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class ResponHandler extends ChannelInboundHandlerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponHandler.class);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LOGGER.info("[client] recieved a connector from " + ctx.channel().remoteAddress());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		PBMessage packet = (PBMessage) msg;
		ClientService.getInst().packHandel(packet);
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
		channel.close();
	}
}

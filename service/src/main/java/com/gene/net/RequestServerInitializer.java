package com.gene.net;

import java.util.concurrent.TimeUnit;

import com.gene.codec.MessageDecoder;
import com.gene.codec.MessageEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class RequestServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// 2分钟客户端没有读写操作就断开连接
		pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0, 3, TimeUnit.MINUTES));
		pipeline.addLast("decoder", new MessageDecoder());
		pipeline.addLast("encoder", new MessageEncoder());
		pipeline.addLast("handler", new RequestHandler());
	}

}

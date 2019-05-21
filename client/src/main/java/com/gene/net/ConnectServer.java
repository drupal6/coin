package com.gene.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ConnectServer {

	private final Logger LOGGER = LoggerFactory.getLogger(ConnectServer.class);
	
	private EventLoopGroup group;
	private Bootstrap bootstrap;

	public ConnectServer() {
		group = new NioEventLoopGroup();

		bootstrap = new Bootstrap();
		bootstrap.group(group);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ResponInitializer());
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		// 连接超时(2秒)
		bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000);
	}

	public Channel connect(String ip, int port) {
		try {
			ChannelFuture future = bootstrap.connect(ip, port).sync();
			return future.channel();
		} catch (Exception e) {
			LOGGER.error("客户端连接Channel失败, ip : " + ip + ", port : " + port, e);
			return null;
		}
	}

	public boolean stop() {
		group.shutdownGracefully();
		return true;
	}

}

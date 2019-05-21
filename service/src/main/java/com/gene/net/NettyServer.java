package com.gene.net;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

	private final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);
	
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	public void start(int port) throws InterruptedException {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);

		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		//使用netty内存对象管理
		bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.childHandler(new RequestServerInitializer());
		bootstrap.bind(port).sync();
		LOGGER.info("启动服务器监听端口成功, port:{}",port);
	}

	public boolean stop() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		return true;
	}

}

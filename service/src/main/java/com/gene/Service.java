package com.gene;

import com.gene.net.NettyServer;
import com.gene.net.commond.CommandService;

public class Service {

	public static void main(String[] args) throws Exception {
		CommandService.getInst().init();
		NettyServer nettyServer = new NettyServer();
		nettyServer.start(7881);
	}
}

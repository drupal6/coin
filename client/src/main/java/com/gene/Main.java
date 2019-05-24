package com.gene;

import com.gene.client.service.ClientService;
import com.gene.net.ConnectServer;

import io.netty.channel.Channel;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		ConnectServer connectServer = new ConnectServer();
		Channel channel = connectServer.connect("192.168.2.55", 7881);
		ClientService.getInst().setClient(channel);
	}
}

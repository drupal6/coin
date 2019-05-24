package com.gene.client.service;

import com.gene.client.Client;
import com.gene.message.PBMessage;

import io.netty.channel.Channel;

public class ClientService {
	
	private static ClientService instance = new ClientService();
	
	public static ClientService getInst() {
		return instance;
	}
	
	private Client client;
	
	public void setClient(Channel channel) {
		client = new Client();
		client.setChannel(channel);
	}

	public Client getClient() {
		return client;
	}
	
	public void packHandel(PBMessage packet) {
		
	}
}

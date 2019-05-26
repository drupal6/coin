package com.gene.client.service;

import com.gene.ReqData;
import com.gene.client.Client;
import com.gene.message.PBMessage;
import com.gene.util.OS;

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
	
	public void packHandel(PBMessage packet) throws Exception {
		ReqData reqRata = client.removeReqData(packet.getSeqId());
		switch (packet.getOs()) {
		case OS.HUOBI:
			HbClientService.getInst().packHandel(client, packet, reqRata);
			break;
		default:
			break;
		}
	}
}

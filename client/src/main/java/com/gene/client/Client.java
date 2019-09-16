package com.gene.client;

import com.gene.client.service.ClientService;
import com.gene.message.MessageUtil;
import com.gene.message.PBMessage;
import com.gene.util.OS;
import com.google.protobuf.AbstractMessage.Builder;
import com.google.protobuf.Message;

import io.netty.channel.Channel;

public class Client {
	
	private final Channel channel;

	private HbClient hbClient;
	
	public Client(Channel channel) {
		this.channel = channel;
	}
	
	public Channel getChannel() {
		return channel;
	}

	public HbClient getHbClient() {
		return hbClient;
	}

	public void setHbClient(HbClient hbClient) {
		this.hbClient = hbClient;
	}
	
	public void req(short code, Builder<?> builder, short os, boolean waitBack, Object...param) {
		req(code, builder.build(), os, waitBack, param);
	}
	public void req(short code, Message message, short os, boolean waitBack, Object...param) {
		PBMessage pack = MessageUtil.buildMessage(code, message, ClientService.getInst().getSeqId(), OS.HUOBI);
		ClientService.getInst().req(channel, pack, waitBack, param);
	}
}

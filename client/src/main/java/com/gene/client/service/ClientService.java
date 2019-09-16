package com.gene.client.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gene.client.Client;
import com.gene.message.PBMessage;
import com.gene.net.ReqData;
import com.gene.util.OS;

import io.netty.channel.Channel;

public class ClientService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);
	
	private static ClientService instance = new ClientService();
	
	public static ClientService getInst() {
		return instance;
	}
	
	private Map<Integer, ReqData> reqDataMap = new ConcurrentHashMap<>();
	
	private Client client;
	
	private AtomicInteger reqId = new AtomicInteger(0);
	
	public void setClient(Channel channel) {
		client = new Client(channel);
	}

	public Client getClient() {
		return client;
	}
	
	public synchronized int getSeqId() {
		if(reqId.get() >= Integer.MAX_VALUE - 10) {
			reqId.set(0);
		}
		return reqId.incrementAndGet();
	}
	
	public void req(Channel channel, PBMessage pack, boolean waitBack, Object...param) {
		if(channel != null && channel.isActive()) {
			if(waitBack) {
				ReqData reqData = new ReqData(pack, param);
				reqDataMap.put(reqData.getSeqId(), reqData);
			}
			channel.writeAndFlush(pack);
		} else {
			//重连
		}
	}
	
	public synchronized ReqData removeReqData(int seqId) {
		return reqDataMap.remove(seqId);
	}
	
	public void packHandel(PBMessage packet) throws Exception {
		ReqData reqRata = removeReqData(packet.getSeqId());
		switch (packet.getOs()) {
		case OS.HUOBI:
			HbClientService.getInst().packHandel(client, packet, reqRata);
			break;
		default:
			LOGGER.error("OS is not support. OS:{}", packet.getOs());
			break;
		}
	}
}

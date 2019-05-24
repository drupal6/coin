package com.gene.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.gene.ReqData;
import com.gene.message.PBMessage;
import com.gene.proto.HBApiProto.AccountMsg;

import io.netty.channel.Channel;

public class Client {

	private AtomicInteger reqId = new AtomicInteger(0);
	
	private Map<Integer, ReqData> reqDataMap = new ConcurrentHashMap<>();
	
	private Channel channel;
	
	private HbClient hbClient;
	
	public void req(PBMessage pack) {
		req(pack, false, null);
	}
	
	public int getSeqId() {
		return reqId.incrementAndGet();
	}
	
	public void req(PBMessage pack, boolean waitBack, Object...param) {
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
	
	public void resParse(PBMessage pack, Channel channel) {
		
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}

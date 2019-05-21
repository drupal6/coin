package com.gene;

import com.gene.message.MessageUtil;
import com.gene.message.PBMessage;
import com.gene.message.ReqCode;
import com.gene.net.ConnectServer;
import com.gene.proto.HBApiProto.ReqApiMsg;

import io.netty.channel.Channel;

public class Client {

	public static void main(String[] args) throws InterruptedException {
		ConnectServer connectServer = new ConnectServer();
		Channel channel = connectServer.connect("192.168.2.55", 7881);
		
		ReqApiMsg.Builder builder = ReqApiMsg.newBuilder();
		builder.setType(1);
		PBMessage pack = MessageUtil.buildMessage(ReqCode.HB_API, builder);
		pack.setPlayerId(1);
		channel.writeAndFlush(pack);
		
		Thread.sleep(10000);
	}

}

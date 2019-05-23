package com.gene;

import com.gene.message.MessageUtil;
import com.gene.message.PBMessage;
import com.gene.message.ReqCode;
import com.gene.net.ConnectServer;
import com.gene.proto.HBApiProto.ReqAccountMsg;
import com.huobi.client.model.enums.AccountType;

import io.netty.channel.Channel;

public class Client {

	public static void main(String[] args) throws InterruptedException {
		ConnectServer connectServer = new ConnectServer();
		Channel channel = connectServer.connect("192.168.2.55", 7881);
		System.out.println(channel.id().toString());
		ReqAccountMsg.Builder builder = ReqAccountMsg.newBuilder();
		builder.setApiKey("testasdfas");
		builder.setSecretKey("testasdfasasdfas");
		builder.setAccountType(AccountType.SPOT.name());
		PBMessage pack = MessageUtil.buildMessage(ReqCode.HB_ACCOUNT, builder, 1);
		channel.writeAndFlush(pack);
		
		Thread.sleep(10000);
	}

}

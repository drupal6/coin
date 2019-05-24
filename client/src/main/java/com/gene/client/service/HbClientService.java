package com.gene.client.service;

import com.gene.client.Client;
import com.gene.message.MessageUtil;
import com.gene.message.PBMessage;
import com.gene.message.ReqCode;
import com.gene.proto.HBApiProto.ReqAccountMsg;
import com.gene.util.OS;
import com.huobi.client.model.enums.AccountType;

import io.netty.channel.Channel;

public class HbClientService {
	
	private static HbClientService instance = new HbClientService();
	
	public static HbClientService getInst() {
		return instance;
	}
	
	/**
	 * 登录
	 */
	public void login(Client client) {
		ReqAccountMsg.Builder builder = ReqAccountMsg.newBuilder();
		builder.setApiKey("testasdfas");
		builder.setSecretKey("testasdfasasdfas");
		builder.setAccountType(AccountType.SPOT.name());
		PBMessage pack = MessageUtil.buildMessage(ReqCode.HB_ACCOUNT, builder, client.getSeqId(), OS.HUOBI);
		client.req(pack, true, null);
	}
	
	public void packHandel(Channel channel, PBMessage message) {
		System.out.println("packHandel:" + channel.id().toString());
	}
}

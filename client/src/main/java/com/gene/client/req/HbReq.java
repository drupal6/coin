package com.gene.client.req;

import com.gene.client.Client;
import com.gene.message.ReqCode;
import com.gene.proto.HBApiProto.ReqAccountMsg;
import com.gene.util.OS;
import com.huobi.client.model.enums.AccountType;

public class HbReq {

	/**
	 * 登录
	 */
	public static void login(Client client) {
		ReqAccountMsg.Builder builder = ReqAccountMsg.newBuilder();
		builder.setApiKey("xxx-xxx-xxx-xxx");
		builder.setSecretKey("xxx-xxx-xxx-xxx");
		builder.setAccountType(AccountType.SPOT.name());
		client.req(ReqCode.HB_ACCOUNT, builder, OS.HUOBI, false);
	}
	
}

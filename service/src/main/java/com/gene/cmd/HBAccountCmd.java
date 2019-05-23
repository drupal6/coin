package com.gene.cmd;


import com.gene.connect.ConnectService;
import com.gene.connect.UserConnect;
import com.gene.message.MessageUtil;
import com.gene.message.PBMessage;
import com.gene.message.ReqCode;
import com.gene.message.ResCode;
import com.gene.net.commond.Cmd;
import com.gene.proto.HBApiProto.ReqAccountMsg;
import com.gene.proto.HBApiProto.ResApiMsg;
import com.gene.service.HbMsgBuilder;
import com.huobi.client.AsyncRequestClient;
import com.huobi.client.SubscriptionClient;
import com.huobi.client.model.Account;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceMode;

import io.netty.channel.Channel;

@Cmd(code=ReqCode.HB_ACCOUNT, desc="登录")
public class HBAccountCmd extends Command {

	@Override
	public void execute(Channel channel, PBMessage packet) throws Exception {
		ReqAccountMsg param = ReqAccountMsg.parseFrom(packet.getBytes());
		System.out.println("channelId:" + channel.id().toString() + " param:" + param.toString());
		AsyncRequestClient client = AsyncRequestClient.create(param.getApiKey(), param.getSecretKey());
		client.getAccountBalance(AccountType.lookup(param.getAccountType()), result -> {
			ResApiMsg.Builder builder = ResApiMsg.newBuilder();
			if(result.succeeded()) {
				//获取账号资产
				Account account = result.getData();
				builder.setAccount(HbMsgBuilder.buildAccountMsg(account));
				UserConnect userConnect = ConnectService.getInst().add(channel);
				userConnect.setAuthAsyncClient(client);
				send(channel, builder, packet.getSeqId());
				//监听
				SubscriptionClient authSubscriptionClient = SubscriptionClient.create(param.getApiKey(), param.getSecretKey());
				userConnect.setAuthSubscriptionClient(authSubscriptionClient);
				authSubscriptionClient.subscribeAccountEvent(BalanceMode.AVAILABLE, accountEvent -> {
					ResApiMsg.Builder syncChangeBuilder = ResApiMsg.newBuilder();
					syncChangeBuilder.setAccountChangeEvent(HbMsgBuilder.buildAccountChangeMsg(accountEvent));
					send(channel, syncChangeBuilder, 0);
				});
			} else {
				builder.setResult(1);
				builder.setMsg(result.getException().getMessage());
				send(channel, builder, packet.getSeqId());
			}
		});
	}
	
	private void send(Channel channel, ResApiMsg.Builder builder, int seqId) {
		PBMessage pack = MessageUtil.buildMessage(ResCode.HB_API, builder, seqId);
		if(channel != null && channel.isActive()) {
			channel.writeAndFlush(pack);
		}
	}
}

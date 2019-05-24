package com.gene.cmd;


import com.gene.connect.HbUser;
import com.gene.connect.User;
import com.gene.message.MessageUtil;
import com.gene.message.PBMessage;
import com.gene.message.ReqCode;
import com.gene.message.ResCode;
import com.gene.net.commond.Cmd;
import com.gene.proto.HBApiProto.ReqAccountMsg;
import com.gene.proto.HBApiProto.ResApiMsg;
import com.gene.service.HbMsgBuilder;
import com.gene.util.ErrorUtil;
import com.gene.util.OS;
import com.huobi.client.AsyncRequestClient;
import com.huobi.client.SubscriptionClient;
import com.huobi.client.model.Account;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceMode;

@Cmd(code=ReqCode.HB_ACCOUNT, os=OS.HUOBI, desc="登录")
public class HBAccountCmd extends Command {

	@Override
	public void execute(User user, PBMessage packet) throws Exception {
		ReqAccountMsg param = ReqAccountMsg.parseFrom(packet.getBytes());
//		ResApiMsg.Builder builder = ResApiMsg.newBuilder();
//		builder.setResult(1);
//		builder.setMsg("test");
//		send(channel, builder, packet.getSeqId());
		AsyncRequestClient client = AsyncRequestClient.create(param.getApiKey(), param.getSecretKey());
		client.getAccountBalance(AccountType.lookup(param.getAccountType()), result -> {
			ResApiMsg.Builder builder = ResApiMsg.newBuilder();
			if(result.succeeded()) {
				//获取账号资产
				Account account = result.getData();
				builder.setAccount(HbMsgBuilder.buildAccountMsg(account));
				if(user == null) {
					ErrorUtil.error(user, packet, "user in not connect.");
					return;
				}
				HbUser hbUser = new HbUser();
				user.setHbUser(hbUser);
				hbUser.setAuthAsyncClient(client);
				send(user, builder, packet.getSeqId(), packet.getOs());
				//监听
				SubscriptionClient authSubscriptionClient = SubscriptionClient.create(param.getApiKey(), param.getSecretKey());
				hbUser.setAuthSubscriptionClient(authSubscriptionClient);
				authSubscriptionClient.subscribeAccountEvent(BalanceMode.AVAILABLE, accountEvent -> {
					ResApiMsg.Builder syncChangeBuilder = ResApiMsg.newBuilder();
					syncChangeBuilder.setAccountChangeEvent(HbMsgBuilder.buildAccountChangeMsg(accountEvent));
					send(user, syncChangeBuilder, 0, packet.getOs());
				});
			} else {
				builder.setResult(1);
				builder.setMsg(result.getException().getMessage());
				send(user, builder, packet.getSeqId(), packet.getOs());
			}
		});
	}
	
	private void send(User user, ResApiMsg.Builder builder, int seqId, short os) {
		PBMessage pack = MessageUtil.buildMessage(ResCode.HB_API, builder, seqId, os);
		user.send(pack);
	}
}

package com.gene.cmd;


import com.gene.HbClientBuilder;
import com.gene.connect.UserConnect;
import com.gene.message.MessageUtil;
import com.gene.message.PBMessage;
import com.gene.message.ReqCode;
import com.gene.message.ResCode;
import com.gene.net.commond.Cmd;
import com.gene.proto.HBApiProto.ReqApiMsg;
import com.gene.proto.HBApiProto.ReqType;
import com.gene.proto.HBApiProto.ResApiMsg;
import com.huobi.client.model.enums.AccountType;

@Cmd(code=ReqCode.HB_API, desc="火币api")
public class HBApiCmd implements Command {

	@Override
	public void execute(UserConnect connect, PBMessage packet) throws Exception {
		ReqApiMsg param = ReqApiMsg.parseFrom(packet.getBytes());
		ReqType type = param.getType();
		ResApiMsg.Builder builder = ResApiMsg.newBuilder();
		PBMessage pack = MessageUtil.buildMessage(ResCode.HB_API, builder);
		connect.send(pack);
	}
	
	private void account(ReqApiMsg param) {
//		HbClientBuilder.asyncClient(param.getApiKey(), param.getSecretKey())
//		.getAccountBalance(AccountType.lookup(param.getAccountType()), callback);
	}
	
}

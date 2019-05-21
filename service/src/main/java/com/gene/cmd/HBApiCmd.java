package com.gene.cmd;

import com.gene.connect.UserConnect;
import com.gene.message.MessageUtil;
import com.gene.message.PBMessage;
import com.gene.message.ReqCode;
import com.gene.message.ResCode;
import com.gene.net.commond.Cmd;
import com.gene.proto.HBApiProto.ReqApiMsg;
import com.gene.proto.HBApiProto.ResApiMsg;

@Cmd(code=ReqCode.HB_API, desc="火币api")
public class HBApiCmd implements Command {

	@Override
	public void execute(UserConnect connect, PBMessage packet) throws Exception {
		ReqApiMsg param = ReqApiMsg.parseFrom(packet.getBytes());
		ResApiMsg.Builder builder = ResApiMsg.newBuilder();
		builder.setType(param.getType());
		PBMessage pack = MessageUtil.buildMessage(ResCode.HB_API, builder);
		connect.send(pack);
	}
}

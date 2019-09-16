package com.gene.util;

import com.gene.connect.ConnectService;
import com.gene.connect.User;
import com.gene.message.MessageUtil;
import com.gene.message.PBMessage;
import com.gene.message.ResCode;
import com.gene.proto.ErrorCodeProto.ResErrorCodeMsg;

import io.netty.channel.Channel;

public class ErrorUtil {
	
	public static void error(User user, PBMessage packet, String msg) {
		ResErrorCodeMsg.Builder build = ResErrorCodeMsg.newBuilder();
		build.setMsg(msg);
		ConnectService.getInst().sendToUser(user, ResCode.ERROR, build, packet.getSeqId(), packet.getOs());
	}
	
	public static void error(Channel channel, PBMessage packet, String msg) {
		ResErrorCodeMsg.Builder build = ResErrorCodeMsg.newBuilder();
		build.setMsg(msg);
		PBMessage message = MessageUtil.buildMessage(ResCode.ERROR, build, packet.getSeqId(), packet.getOs());
		if(channel != null && channel.isActive()) {
			channel.writeAndFlush(message);
		}
	}
}

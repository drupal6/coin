package com.gene.util;

import com.gene.connect.UserConnect;
import com.gene.message.MessageUtil;
import com.gene.message.PBMessage;
import com.gene.message.ResCode;
import com.gene.proto.ErrorCodeProto.ResErrorCodeMsg;

import io.netty.channel.Channel;

public class ErrorUtil {
	
	public static void error(UserConnect user, int seqId, String msg) {
		ResErrorCodeMsg.Builder build = ResErrorCodeMsg.newBuilder();
		build.setMsg(msg);
		PBMessage message = MessageUtil.buildMessage(ResCode.ERROR, build, seqId);
		user.send(message);
	}
	
	public static void error(Channel channel, int seqId, String msg) {
		ResErrorCodeMsg.Builder build = ResErrorCodeMsg.newBuilder();
		build.setMsg(msg);
		PBMessage message = MessageUtil.buildMessage(ResCode.ERROR, build, seqId);
		if(channel != null && channel.isActive()) {
			channel.writeAndFlush(message);
		}
	}
}

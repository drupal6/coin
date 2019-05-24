package com.gene.message;

import com.google.protobuf.AbstractMessage.Builder;
import com.google.protobuf.Message;

public class MessageUtil {

	/**
	 * 消息创建
	 */
	public static PBMessage buildMessage(short code, Builder<?> builder, int seqId, short os) {
		return buildMessage(code, builder.build(), seqId, os);
	}

	/**
	 * 消息创建
	 */
	public static PBMessage buildMessage(short code, Message message, int seqId, short os) {
		PBMessage response = new PBMessage(code, seqId, os);
		response.setMessage(message);
		return response;
	}

}

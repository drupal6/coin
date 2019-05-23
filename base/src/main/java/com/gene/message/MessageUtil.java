package com.gene.message;

import com.google.protobuf.AbstractMessage.Builder;
import com.google.protobuf.Message;

public class MessageUtil {

	/**
	 * 消息创建
	 */
	public static PBMessage buildMessage(short code, Builder<?> builder, int seqId) {
		return buildMessage(code, builder.build(), seqId);
	}

	/**
	 * 消息创建
	 */
	public static PBMessage buildMessage(short code, Message message, int seqId) {
		PBMessage response = new PBMessage(code);
		response.setSeqId(seqId);
		response.setMessage(message);
		return response;
	}

}

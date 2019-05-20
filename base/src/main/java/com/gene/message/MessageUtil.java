package com.gene.message;

import com.google.protobuf.AbstractMessage.Builder;
import com.google.protobuf.Message;

public class MessageUtil {

	/**
	 * 消息创建
	 */
	public static PBMessage buildMessage(short code, Builder<?> builder) {
		return buildMessage(code, builder.build());
	}

	/**
	 * 消息创建
	 */
	public static PBMessage buildMessage(short code, Message message) {
		PBMessage response = new PBMessage(code, -1);
		response.setMessage(message);
		return response;
	}

}

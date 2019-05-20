package com.gene.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gene.message.PBMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class MessageDecoder extends LengthFieldBasedFrameDecoder{

	private final Logger LOGGER = LoggerFactory.getLogger(MessageDecoder.class);
	
	public MessageDecoder() {
		super(PBMessage.DECODER_MAX_FRAME_LENGTH, PBMessage.DECODER_LENGTH_FIELD_OFFSET, 
				PBMessage.DECODER_LENGTH_FIELD_LENGTH, PBMessage.DECODER_LENGTH_ADJUSTMEN, 0);
	}

	@Override
	protected PBMessage decode(ChannelHandlerContext ctx, ByteBuf in2) throws Exception {
		ByteBuf in = (ByteBuf) super.decode(ctx, in2);
		if (in == null) {
		    return null;
		}
		if (in.readableBytes() < PBMessage.HDR_SIZE) {
			return null;
		}
		in.markReaderIndex();
		int headerFlag = in.readShort();
		if (PBMessage.HEADER != headerFlag) { // 验证header标记
			LOGGER.warn("Illegal client request, can not match header flag. drop a packet, close connection. headerFlag=" + headerFlag);
			return null;
		}
		int len = in.readShort(); // 包长
		if (len <= 0 || len >= PBMessage.PACKET_MAX_LEN) {
			// 非法的数据长度
			LOGGER.warn("Receive Message Length Invalid Length = " + len + ", drop this Message.");
			return null;
		}
		if (in.readableBytes() < len - 4) {
			// 数据还不够读取,等待下一次读取o
			in.resetReaderIndex();
			return null;
		}
		PBMessage message = new PBMessage();
		message.readHeader(in);
		byte[] data = new byte[len - PBMessage.HDR_SIZE];
		in.readBytes(data);
		message.setBytes(data);
		in.release();
		return message;
	}
}

package com.gene.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gene.message.PBMessage;
import com.google.protobuf.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<PBMessage> {
	
	private final Logger LOGGER = LoggerFactory.getLogger(MessageEncoder.class);
	
	@Override
    protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, PBMessage msg,
               boolean preferDirect) throws Exception {
		int len = msg.getLen();
    	if (preferDirect) {
    		//TODO
    		if(len <= 32) {
    			return ctx.alloc().ioBuffer(32);
    		} else if(len <= 64) {
    			return ctx.alloc().ioBuffer(64);
    		} else if(len <= 128) {
    			return ctx.alloc().ioBuffer(128);
    		} else if(len <= 256) {
    			return ctx.alloc().ioBuffer(256);
    		}else if(len <= 512) {
    			return ctx.alloc().ioBuffer(512);
    		}else if(len <= 1024) {
    			return ctx.alloc().ioBuffer(1024);
    		}else if(len <= 2048) {
    			return ctx.alloc().ioBuffer(2048);
    		} else if(len > PBMessage.PACKET_MAX_LEN) {
    			return ctx.alloc().ioBuffer(32);
    		}
    		return ctx.alloc().ioBuffer(512);
		} else {
			return ctx.alloc().heapBuffer();
		}
	}
	
	@Override
	protected void encode(ChannelHandlerContext arg0, PBMessage pbMessage, ByteBuf out) throws Exception {
		Message msg = pbMessage.getMessage();
		byte[] bytes = pbMessage.getBytes();
		short size = pbMessage.getLen();

		if (size > PBMessage.PACKET_MAX_LEN) { // 包过长
			LOGGER.warn("Send Message Length Invalid Length = " + size + ", drop this Message. code=" + pbMessage.getCode());
			return;
		}
		pbMessage.writeHeader(out); // 头部
		if (msg != null && msg.getSerializedSize() > 0) { // 消息体
			out.writeBytes(msg.toByteArray());
		} else if (bytes != null && bytes.length > 0) {
			out.writeBytes(bytes);
		}
	}
}

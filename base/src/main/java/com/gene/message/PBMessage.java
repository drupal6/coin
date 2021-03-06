package com.gene.message;

import java.io.Serializable;

import com.google.protobuf.Message;

import io.netty.buffer.ByteBuf;

/**
 * 服务器和客户端,服务器和服务器直接数据传输的对象
 **/
public class PBMessage implements Serializable {

	public static final int DECODER_MAX_FRAME_LENGTH = 1024 * 1024;  //解码的帧的最大长度
	public static final int DECODER_LENGTH_FIELD_OFFSET = 2;  		 //解码长度属性的起始位
	public static final int DECODER_LENGTH_FIELD_LENGTH = 2;		 //解码长度属性的长度
	public static final int DECODER_LENGTH_ADJUSTMEN = -4;			  //解码长度属性调正(长度之前包括长度占的位数)
	/** 最大包长 */
	public static final int PACKET_MAX_LEN = Short.MAX_VALUE ;

	private static final long serialVersionUID = 1L;
	public static final short HDR_SIZE = 14;
	public static final short HEADER = 29099;

	private boolean forward = false; //是否是转发
	
	private short header = HEADER; // 包头
	private short len = HDR_SIZE; // 数据包长度
	private short checksum; // 校验和
	private short code; // 协议号
	private int seqId; 	// 客服端标识
	private short os; // 平台

	private byte[] bytes; // 数据体
	private Message message; // Proto

	public PBMessage() {
	}

	public PBMessage(short code, int seqId, short os) {
		this.code = code;
		this.seqId = seqId;
		this.os = os;
	}

	public short getHeader() {
		return header;
	}

	public short getLen() {
		return len;
	}

	public void setLen(short len) {
		this.len = len;
	}

	public short getChecksum() {
		return checksum;
	}

	public void setChecksum(short checksum) {
		this.checksum = checksum;
	}

	public short getCode() {
		return code;
	}

	public void setCode(short code) {
		this.code = code;
	}

	public int getSeqId() {
		return seqId;
	}

	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}

	public short getOs() {
		return os;
	}

	public void setOs(short os) {
		this.os = os;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
		if(message != null) {
			len += message.getSerializedSize();
		}
	}

	public boolean isForward() {
		return forward;
	}

	public void setForward(boolean forward) {
		this.forward = forward;
	}

	public void readHeader(ByteBuf in) {
		in.resetReaderIndex();
		in.readShort();
		len = in.readShort();
		checksum = in.readShort();
		code = in.readShort();
		seqId = in.readInt();
		os = in.readShort();
	}

	public void writeHeader(ByteBuf out) {
		out.writeShort(PBMessage.HEADER);// 包头固定长度
		out.writeShort(len); // 总长度
		out.writeShort(checksum); // 校验
		out.writeShort(code); // 命令
		out.writeInt(seqId);
		out.writeShort(os);
	}

	public short calcChecksum(byte[] data) {
		int val = 0x77;
		int i = 6;
		int size = data.length;
		while (i < size) {
			val += (data[i++] & 0xFF);
		}
		return (short) (val & 0x7F7F);
	}

	public void clearCheckSum() {
		checksum = 0;
	}

	public String headerToStr() {
		StringBuilder sb = new StringBuilder();
		sb.append("header : ").append(header);
		sb.append(", code : ").append(code);
		sb.append(", len : ").append(len);
		sb.append(", seqId : ").append(seqId);
		sb.append(", os : ").append(os);
		sb.append(", checksum : ").append(checksum);
		return sb.toString();
	}

}

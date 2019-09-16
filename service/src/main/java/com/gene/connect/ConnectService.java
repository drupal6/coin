package com.gene.connect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.gene.exec.CmdExecutor;
import com.gene.message.MessageUtil;
import com.gene.message.PBMessage;
import com.google.protobuf.Message;
import com.google.protobuf.AbstractMessage.Builder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

public class ConnectService {

	private static ConnectService instance = new ConnectService();
	
	public static ConnectService getInst() {
		return instance;
	}
	
	private Map<ChannelId, User> channelMap = new ConcurrentHashMap<ChannelId, User>();
	public CmdExecutor executor;
	
	public ConnectService() {
		int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
		int maxPoolSize = corePoolSize * 2;
		int keepAliveTime = 5;
		int cacheSize = maxPoolSize;
		executor = new CmdExecutor(corePoolSize, maxPoolSize, keepAliveTime, cacheSize, "executor");
	}
	
	public User get(Channel channel) {
		return channelMap.get(channel.id());
	}
	
	public User add(Channel channel) {
		User user = new User(channel, executor);
		synchronized (channelMap) {
			channelMap.put(channel.id(), user);
			System.out.println("addUser user.");
		}
		return user;
	}
	
	public void remove(Channel channel) {
		synchronized (channelMap) {
			User user = channelMap.remove(channel.id());
			if(user.getHbUser() != null) {
				user.getHbUser().unSub();
				System.out.println("remove user.");
			}
		}
	}
	
	private void sendToUser(User user, PBMessage pbMessage) {
		user.send(pbMessage);
	}
	
	public void sendToUser(User user, short code,  Builder<?> builder, int seqId, short os) {
		PBMessage pack = MessageUtil.buildMessage(code, builder, seqId, os);
		sendToUser(user, pack);
	}
	
	public void sendToUser(User user, short code, Message message, int seqId, short os) {
		PBMessage pack = MessageUtil.buildMessage(code, message, seqId, os);
		sendToUser(user, pack);
	}
	
	public void setToAll(short code, Builder<?> builder, int seqId, short os) {
		PBMessage pack = MessageUtil.buildMessage(code, builder, seqId, os);
		setToAll(pack);
	}
	
	public void setToAll(short code, Message message, int seqId, short os) {
		PBMessage pack = MessageUtil.buildMessage(code, message, seqId, os);
		setToAll(pack);
	}
	
	private void setToAll(PBMessage pbMessage) {
		channelMap.forEach((cid, user) -> {
			user.send(pbMessage);
		});
	}
}

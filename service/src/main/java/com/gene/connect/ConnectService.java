package com.gene.connect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.gene.exec.CmdExecutor;

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
		}
		return user;
	}
	
	public void remove(Channel channel) {
		synchronized (channelMap) {
			User user = channelMap.remove(channel.id());
			if(user.getHbUser() != null) {
				user.getHbUser().unSub();
			}
		}
	}
}

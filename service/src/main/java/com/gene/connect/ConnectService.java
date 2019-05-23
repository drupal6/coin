package com.gene.connect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.gene.exec.AbstractCmdTaskQueue;
import com.gene.exec.CmdExecutor;
import com.gene.exec.CmdTask;
import com.gene.exec.CmdTaskQueue;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

public class ConnectService {

	private static ConnectService instance = new ConnectService();
	
	public static ConnectService getInst() {
		return instance;
	}
	
	private Map<ChannelId, UserConnect> channelMap = new ConcurrentHashMap<ChannelId, UserConnect>();
	public CmdExecutor executor;
	private CmdTaskQueue cmdTaskQueue;
	
	public ConnectService() {
		int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
		int maxPoolSize = corePoolSize * 2;
		int keepAliveTime = 5;
		int cacheSize = maxPoolSize;
		executor = new CmdExecutor(corePoolSize, maxPoolSize, keepAliveTime, cacheSize, "executor");
		this.cmdTaskQueue = new AbstractCmdTaskQueue(executor);
	}
	
	public UserConnect get(Channel channel) {
		return channelMap.get(channel.id());
	}
	
	public UserConnect add(Channel channel) {
		UserConnect userConnect = new UserConnect(channel, executor);
		synchronized (channelMap) {
			channelMap.put(channel.id(), userConnect);
		}
		return userConnect;
	}
	
	public void remove(Channel channel) {
		synchronized (channelMap) {
			UserConnect userConnect = channelMap.remove(channel.id());
			if(userConnect != null) {
				if(userConnect.getSubscriptionClient() != null) {
					userConnect.getSubscriptionClient().unsubscribeAll();
				}
				if(userConnect.getAuthSubscriptionClient() != null) {
					userConnect.getAuthSubscriptionClient().unsubscribeAll();
				}
			}
		}
	}
	
	public CmdTaskQueue getCmdTaskQueue() {
		return cmdTaskQueue;
	}

	public void enqueue(CmdTask cmdTask) {
		cmdTaskQueue.enqueue(cmdTask);
	}
}

package com.gene.connect;

import com.gene.exec.AbstractCmdTaskQueue;
import com.gene.exec.CmdExecutor;
import com.gene.exec.CmdTask;
import com.gene.exec.CmdTaskQueue;
import com.gene.message.PBMessage;
import com.huobi.client.AsyncRequestClient;
import com.huobi.client.SubscriptionClient;

import io.netty.channel.Channel;

public class UserConnect {
	
	private final Channel channel;
	
	private CmdTaskQueue cmdTaskQueue;
	
	private AsyncRequestClient authAsyncClient;
	
	private AsyncRequestClient asyncClient;
	
	private SubscriptionClient subscriptionClient;
	
	private SubscriptionClient authSubscriptionClient;

	public UserConnect(Channel channel, CmdExecutor executor) {
		this.channel = channel;
		this.cmdTaskQueue = new AbstractCmdTaskQueue(executor);
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public CmdTaskQueue getCmdTaskQueue() {
		return cmdTaskQueue;
	}

	public void enqueue(CmdTask cmdTask) {
		cmdTaskQueue.enqueue(cmdTask);
	}
	
	public void send(PBMessage pbmessage) {
		if(channel != null && channel.isActive()) {
			channel.writeAndFlush(pbmessage);
		} else {
			ConnectService.getInst().remove(channel);
		}
	}
	
	public void setCmdTaskQueue(CmdTaskQueue cmdTaskQueue) {
		this.cmdTaskQueue = cmdTaskQueue;
	}

	public AsyncRequestClient getAuthAsyncClient() {
		return authAsyncClient;
	}

	public void setAuthAsyncClient(AsyncRequestClient authAsyncClient) {
		this.authAsyncClient = authAsyncClient;
	}

	public AsyncRequestClient getAsyncClient() {
		if(authAsyncClient == null) {
			authAsyncClient = AsyncRequestClient.create();
		}
		return asyncClient;
	}

	public void setAsyncClient(AsyncRequestClient asyncClient) {
		this.asyncClient = asyncClient;
	}

	public SubscriptionClient getSubscriptionClient() {
		if(subscriptionClient == null) {
			subscriptionClient = SubscriptionClient.create();
		}
		return subscriptionClient;
	}

	public void setSubscriptionClient(SubscriptionClient subscriptionClient) {
		this.subscriptionClient = subscriptionClient;
	}

	public SubscriptionClient getAuthSubscriptionClient() {
		return authSubscriptionClient;
	}

	public void setAuthSubscriptionClient(SubscriptionClient authSubscriptionClient) {
		this.authSubscriptionClient = authSubscriptionClient;
	}
}

package com.gene.connect;

import com.gene.exec.AbstractCmdTaskQueue;
import com.gene.exec.CmdExecutor;
import com.gene.exec.CmdTask;
import com.gene.exec.CmdTaskQueue;

import io.netty.channel.Channel;

public class UserConnect {
	
	private final Channel channel;
	
	private CmdTaskQueue cmdTaskQueue;

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
}

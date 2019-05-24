package com.gene.connect;

import com.gene.exec.AbstractCmdTaskQueue;
import com.gene.exec.CmdExecutor;
import com.gene.exec.CmdTask;
import com.gene.exec.CmdTaskQueue;
import com.gene.message.PBMessage;

import io.netty.channel.Channel;

public class User {

	private final Channel channel;
	
	private CmdTaskQueue cmdTaskQueue;
	
	private HbUser hbUser;
	
	public User(Channel channel, CmdExecutor executor) {
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

	public HbUser getHbUser() {
		return hbUser;
	}

	public void setHbUser(HbUser hbUser) {
		this.hbUser = hbUser;
	}
}

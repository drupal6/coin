package com.gene.net.commond;

import com.gene.message.PBMessage;

import io.netty.channel.Channel;

public interface Command {

	public abstract void execute(Channel channel, PBMessage packet) throws Exception;

}

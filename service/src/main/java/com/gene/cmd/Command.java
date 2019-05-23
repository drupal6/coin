package com.gene.cmd;

import com.gene.message.PBMessage;

import io.netty.channel.Channel;

public abstract class Command {

	public abstract void execute(Channel channel, PBMessage packet) throws Exception;

}

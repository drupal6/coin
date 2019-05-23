package com.gene.cmd;

import com.gene.connect.ConnectService;
import com.gene.connect.UserConnect;
import com.gene.message.PBMessage;
import com.gene.util.ErrorUtil;

import io.netty.channel.Channel;

public abstract class UserCommand extends Command{

	public void execute(Channel channel, PBMessage packet) throws Exception {
		UserConnect userConnect = ConnectService.getInst().get(channel);
		if(userConnect == null) {
			ErrorUtil.error(channel, packet.getSeqId(), "UserCommand没有登录");
			return;
		}
		execute(userConnect, packet);
	}
	
	public abstract void execute(UserConnect connect, PBMessage packet) throws Exception;

}

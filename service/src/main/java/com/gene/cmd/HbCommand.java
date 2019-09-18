package com.gene.cmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gene.connect.HbUser;
import com.gene.connect.User;
import com.gene.message.PBMessage;

public abstract class HbCommand extends Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(HbCommand.class);
	
	public void execute(User user, PBMessage packet) throws Exception {
		HbUser hbuser = user.getHbUser();
		if(hbuser == null) {
			LOGGER.error("HbUser is null. code:{}", packet.getCode());
			return;
		}
		execute(hbuser, packet);
	}
	
	public abstract void execute(HbUser hbuser, PBMessage packet) throws Exception;

}

package com.gene.cmd;

import com.gene.connect.User;
import com.gene.message.PBMessage;

public abstract class Command {

	public abstract void execute(User user, PBMessage packet) throws Exception;

}

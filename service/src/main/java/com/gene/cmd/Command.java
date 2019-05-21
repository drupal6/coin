package com.gene.cmd;

import com.gene.connect.UserConnect;
import com.gene.message.PBMessage;

public interface Command {

	public abstract void execute(UserConnect connect, PBMessage packet) throws Exception;

}

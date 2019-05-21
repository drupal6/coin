package com.gene.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gene.cmd.Command;
import com.gene.connect.UserConnect;
import com.gene.message.PBMessage;

/**
 * 执行cmd
 */
public class CmdTask implements Runnable {
	
	private final Logger LOGGER = LoggerFactory.getLogger(CmdTask.class);

	protected CmdTaskQueue queue;
	private UserConnect connect;
	private PBMessage packet;
	protected Command cmd;
	protected ICallBack callBack;
	

	public CmdTask(Command cmd, UserConnect connect, PBMessage packet, CmdTaskQueue queue) {
		this.connect = connect;
		this.packet = packet;
		this.cmd = cmd;
		this.queue = queue;
	}
	
	public CmdTask(CmdTaskQueue queue, ICallBack callBack) {
		this.callBack = callBack;
		this.queue = queue;
	}

	@Override
	public void run() {
		if (queue != null) {
			long start = System.currentTimeMillis();
			try {
				if(cmd != null) {
					cmd.execute(connect, packet);
					long end = System.currentTimeMillis();
					long interval = end - start;
					if (interval >= 1000) {
						LOGGER.warn("execute action : " + this.toString() + ", interval : " + interval + ", size : " + queue.getQueue().size());
					}
				}
				if(callBack != null) {
					callBack.callBack();
				}
			} catch (Exception e) {
				if(cmd != null) {
					LOGGER.error("执行 command 异常, command : " + cmd.toString() + ", packet : " + packet.toString(), e);
				}
				if(callBack != null) {
					LOGGER.error("执行 callBack 异常", e);
				}
			} finally {
				queue.dequeue(this);
			}
		}
	}

	@Override
	public String toString() {
		return cmd.toString() + ", packet : " + packet.headerToStr();
	}
}

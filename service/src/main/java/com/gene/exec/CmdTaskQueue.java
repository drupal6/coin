package com.gene.exec;

import java.util.Queue;

/**
 * CmdTask命令处理队列
 */
public interface CmdTaskQueue {
	
	public CmdTaskQueue getCmdTaskQueue();
	
	public void enqueue(CmdTask cmdTask);
	
	public void dequeue(CmdTask cmdTask);
	
	public Queue<CmdTask> getQueue();
	
	public void clear();
}

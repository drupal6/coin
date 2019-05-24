package com.gene;

import com.gene.message.PBMessage;

public class ReqData {

	private final int seqId;
	
	private final long createTime;
	
	private final PBMessage pack;
	
	private final Object[] param;
	
	public ReqData(PBMessage pack, Object[] param) {
		this.seqId = pack.getSeqId();
		this.pack = pack;
		this.param = param;
		this.createTime = System.currentTimeMillis();
	}

	public int getSeqId() {
		return seqId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public PBMessage getPack() {
		return pack;
	}

	public Object[] getParam() {
		return param;
	}
}

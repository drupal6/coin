package com.gene.client.receive.hb.impl;

import com.gene.client.HbClient;
import com.gene.client.receive.hb.HBReceiveHandle;
import com.gene.net.ReqData;
import com.gene.proto.HBApiProto.ResApiMsg;
import com.google.protobuf.InvalidProtocolBufferException;

public class HBApiReceive implements HBReceiveHandle {

	@Override
	public void receiveHandel(HbClient hbClient, ResApiMsg result, ReqData reqRata) throws InvalidProtocolBufferException {
		
	}
}

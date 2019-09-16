package com.gene.client.receive.hb;

import com.gene.client.HbClient;
import com.gene.net.ReqData;
import com.gene.proto.HBApiProto.ResApiMsg;
import com.google.protobuf.InvalidProtocolBufferException;

public interface HBReceiveHandle {
	
	public void receiveHandel(HbClient client, ResApiMsg result, ReqData reqRata) throws InvalidProtocolBufferException;
}

package com.gene.client.receive;

import com.gene.client.Client;
import com.gene.net.ReqData;
import com.gene.proto.HBApiProto.ResApiMsg;
import com.google.protobuf.InvalidProtocolBufferException;

public interface ReceiveHandle {

	public void receiveHandel(Client client, ResApiMsg result, ReqData reqRata) throws InvalidProtocolBufferException;
}

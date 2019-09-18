package com.gene.client.receive.hb.impl;

import com.gene.client.Client;
import com.gene.client.HbClient;
import com.gene.client.receive.ReceiveHandle;
import com.gene.net.ReqData;
import com.gene.proto.HBApiProto.ResApiMsg;
import com.gene.util.HbBeanBuilder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.huobi.client.model.Account;

public class HBAccountReceive implements ReceiveHandle {

	@Override
	public void receiveHandel(Client client, ResApiMsg result, ReqData reqRata) throws InvalidProtocolBufferException {
		HbClient hbClient = client.getHbClient();
		if(result.hasAccount()) {
			Account account = HbBeanBuilder.parseAccount(result.getAccount());
			if(hbClient == null) {
				hbClient = new HbClient(client);
				hbClient.setAccount(account);
				client.setHbClient(hbClient);
			}
		}
	}
}

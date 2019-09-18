package com.gene.client.receive.hb.impl;

import com.gene.client.HbClient;
import com.gene.client.receive.hb.HBReceiveHandle;
import com.gene.net.ReqData;
import com.gene.proto.HBApiProto.ResApiMsg;
import com.gene.util.HbBeanBuilder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.huobi.client.model.Account;
import com.huobi.client.model.event.AccountEvent;

public class HBAccountEventReceive implements HBReceiveHandle {

	@Override
	public void receiveHandel(HbClient hbClient, ResApiMsg result, ReqData reqRata) throws InvalidProtocolBufferException {
		if(result.hasAccountChangeEvent()) {
			AccountEvent accountEvent = HbBeanBuilder.parseAccountEvent(result.getAccountChangeEvent());
			accountEvent.getData().forEach(accountChange -> {
				Account account = hbClient.getAccount(accountChange.getAccountType());
				if(account != null) {
					account.getBalances().forEach(balance -> {
						if(balance.getCurrency().equals(accountChange.getCurrency()) && balance.getType() == accountChange.getBalanceType()) {
							balance.setBalance(accountChange.getBalance());
						}
					});
				}
			});
		}
	}
}

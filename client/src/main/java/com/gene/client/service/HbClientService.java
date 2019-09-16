package com.gene.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gene.client.Client;
import com.gene.client.HbClient;
import com.gene.client.receive.ReceiveHandle;
import com.gene.client.receive.hb.HBReceiveHandle;
import com.gene.message.PBMessage;
import com.gene.net.ReqData;
import com.gene.proto.HBApiProto.ResApiMsg;
import com.gene.util.ClassUtil;
import com.google.protobuf.InvalidProtocolBufferException;

public class HbClientService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(HbClientService.class);
	
	private static HbClientService instance = new HbClientService();
	
	private static AtomicBoolean loadReceive = new AtomicBoolean(false);
	
	private List<Object> receives = new ArrayList<Object>();
	
	
	public static HbClientService getInst() {
		if(false == loadReceive.get()) {
			try {
				instance.initReceiveHandle();
				loadReceive.set(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	public boolean initReceiveHandle() throws Exception {
		Package pack = HBReceiveHandle.class.getPackage();
		Set<Class<?>> allClasses = ClassUtil.getClasses(pack);
		for (Class<?> clazz : allClasses) {
			if(false == ReceiveHandle.class.isAssignableFrom(clazz) && false == HBReceiveHandle.class.isAssignableFrom(clazz)) {
				continue;
			}
			if(clazz.isInterface()) {
				continue;
			}
			receives.add(clazz.newInstance());
		}
		return true;
	}
	
	public void packHandel(Client client, PBMessage message, ReqData reqRata) throws InvalidProtocolBufferException {
		ResApiMsg result = ResApiMsg.parseFrom(message.getBytes()); 
		if(result.getResult() != 0) {
			int reqCpde = 0;
			if(reqRata != null) {
				reqCpde = reqRata.getPack().getCode();
			}
			LOGGER.error("code:{} request is fail.msg:{}", reqCpde, message.getMessage());
			return;
		}
		receives.forEach(receiveHandle -> {
			try {
				if(receiveHandle instanceof ReceiveHandle) {
					((ReceiveHandle) receiveHandle).receiveHandel(client, result, reqRata);
				} else if(receiveHandle instanceof HBReceiveHandle) {
					HbClient hbClient = client.getHbClient();
					if(hbClient == null) {
						LOGGER.error("HbClient is null. msg:{}", result.toString());
						return;
					}
					((HBReceiveHandle) receiveHandle).receiveHandel(hbClient, result, reqRata);
				} else {
					
				}
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}
		});
	}
}

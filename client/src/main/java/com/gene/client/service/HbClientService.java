package com.gene.client.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gene.ReqData;
import com.gene.client.Client;
import com.gene.client.HbClient;
import com.gene.message.MessageUtil;
import com.gene.message.PBMessage;
import com.gene.message.ReqCode;
import com.gene.proto.HBApiProto.ReqAccountMsg;
import com.gene.proto.HBApiProto.ResApiMsg;
import com.gene.util.OS;
import com.google.protobuf.InvalidProtocolBufferException;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Order;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.event.AccountEvent;
import com.huobi.client.model.event.OrderUpdateEvent;

public class HbClientService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(HbClientService.class);
	
	private static HbClientService instance = new HbClientService();
	
	public static HbClientService getInst() {
		return instance;
	}
	
	/**
	 * 登录
	 */
	public void login(Client client) {
		ReqAccountMsg.Builder builder = ReqAccountMsg.newBuilder();
		builder.setApiKey("testasdfas");
		builder.setSecretKey("testasdfasasdfas");
		builder.setAccountType(AccountType.SPOT.name());
		PBMessage pack = MessageUtil.buildMessage(ReqCode.HB_ACCOUNT, builder, client.getSeqId(), OS.HUOBI);
		client.req(pack, true, null);
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
		HbClient hbClient = client.getHbClient();
		if(result.hasAccount()) {
			if(hbClient == null) {
				hbClient = new HbClient();
				client.setHbClient(hbClient);
			}
			hbClient.setAccount(HbMsgParse.parseAccount(result.getAccount()));
		} else {
			if(hbClient == null || hbClient.getAccount() == null) {
				LOGGER.error("HBClient not login.");
				return;
			}
			
			if(result.hasAccountChangeEvent()) {
				AccountEvent accountEvent = HbMsgParse.parseAccountEvent(result.getAccountChangeEvent());
				updateAccount(hbClient, accountEvent);
			}
			
			if(result.getOpenOrdersCount() > 0) {
				List<Order> openOrders = new ArrayList<>();
				result.getOpenOrdersList().forEach(o -> {
					openOrders.add(HbMsgParse.parseOrder(o));
				});
				hbClient.setOpenOrders(openOrders);
			}
			
			if(result.getOrderHistoryCount() > 0) {
				List<Order> orderHistory = new ArrayList<>();
				result.getOrderHistoryList().forEach(o -> {
					orderHistory.add(HbMsgParse.parseOrder(o));
				});
				hbClient.setOrderHistory(orderHistory);
			}
			if(result.hasOrderUdateEvent()) {
				OrderUpdateEvent orderUpdateEvent = HbMsgParse.parseOrderUpdateEvent(result.getOrderUdateEvent());
				updateOrder(hbClient, orderUpdateEvent);
			}
			if(result.hasBestQuote()) {
				BestQuote bestQuote = HbMsgParse.parseBestQuote(result.getBestQuote());
			}
		}
	}
	
	private void updateAccount(HbClient hbClient, AccountEvent accountEvent) {
		
	}
	
	private void updateOrder(HbClient hbClient, OrderUpdateEvent orderUpdateEvent) {
		
	}
}

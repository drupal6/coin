package com.gene.cmd;


import java.math.BigDecimal;
import java.util.List;

import com.gene.connect.UserConnect;
import com.gene.message.MessageUtil;
import com.gene.message.PBMessage;
import com.gene.message.ReqCode;
import com.gene.message.ResCode;
import com.gene.net.commond.Cmd;
import com.gene.proto.HBApiProto.OrderUpdateEventMsg;
import com.gene.proto.HBApiProto.ReqApiMsg;
import com.gene.proto.HBApiProto.ReqType;
import com.gene.proto.HBApiProto.ResApiMsg;
import com.gene.service.HbMsgBuilder;
import com.gene.util.ErrorUtil;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Order;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.HistoricalOrdersRequest;
import com.huobi.client.model.request.NewOrderRequest;
import com.huobi.client.model.request.OpenOrderRequest;

@Cmd(code=ReqCode.HB_API, desc="火币api")
public class HBApiCmd extends UserCommand {

	@Override
	public void execute(UserConnect connect, PBMessage packet) throws Exception {
		ReqApiMsg param = ReqApiMsg.parseFrom(packet.getBytes());
		ReqType type = param.getType();
		switch (type.getNumber()) {
		case ReqType.OPENORDERS_VALUE:
			openOrders(connect, param, packet.getSeqId());
			break;
		case ReqType.ORDERHISTORY_VALUE:
			orderHistory(connect, param, packet.getSeqId());
			break;
		case ReqType.CREATEORDER_VALUE:
			createOrder(connect, param, packet.getSeqId());
			break;
		case ReqType.CANCELORDER_VALUE:
			cancelOrder(connect, param, packet.getSeqId());
			break;
		case ReqType.BESTQUOTE_VALUE:
			bestQuote(connect, param, packet.getSeqId());
			break;
		case ReqType.MONITORORDER_VALUE:
			monitorOrder(connect, param.getSymbols());
			break;
		default:
			ErrorUtil.error(connect.getChannel(), packet.getSeqId(), "type not suport! type:" + type.name());
			break;
		}
	}
	
	private void openOrders(UserConnect connect, ReqApiMsg param, int seqId) {
		OpenOrderRequest openOrderRequest = new OpenOrderRequest(param.getSymbol(), AccountType.lookup(param.getAccountType()));
		connect.getAuthAsyncClient().getOpenOrders(openOrderRequest, asyncResult -> {
			if(asyncResult.succeeded()) {
				List<Order> orders = asyncResult.getData();
				ResApiMsg.Builder builder = ResApiMsg.newBuilder();
				orders.forEach(order -> {
					builder.addOpenOrders(HbMsgBuilder.buildOrdermsg(order));
				});
				send(connect, builder, seqId);
			} else {
				
			}
		});
	}
	
	private void orderHistory(UserConnect connect, ReqApiMsg param, int seqId) {
		HistoricalOrdersRequest historicalOrdersRequest = new HistoricalOrdersRequest(param.getSymbol(), OrderState.lookup(param.getOrderState()));
		connect.getAuthAsyncClient().getHistoricalOrders(historicalOrdersRequest, asyncResult -> {
			if(asyncResult.succeeded()) {
				List<Order> orders = asyncResult.getData();
				ResApiMsg.Builder builder = ResApiMsg.newBuilder();
				orders.forEach(order -> {
					builder.addOrderHistory(HbMsgBuilder.buildOrdermsg(order));
				});
				send(connect, builder, seqId);
			} else {
				
			}
		});
	}
	
	private void createOrder(UserConnect connect, ReqApiMsg param, int seqId) {
		NewOrderRequest newOrderRequest = new NewOrderRequest(param.getSymbol(), 
				AccountType.lookup(param.getAccountType()), 
				OrderType.lookup(param.getOrderType()), new BigDecimal(param.getAmount()), new BigDecimal(param.getPrice()));
		connect.getAuthAsyncClient().createOrder(newOrderRequest, asyncResult -> {
			if(asyncResult.succeeded()) {
				monitorOrder(connect, param.getSymbol());
			} else {
				
			}
		});
	}
	private void cancelOrder(UserConnect connect, ReqApiMsg param, int seqId) {
		connect.getAuthAsyncClient().cancelOrders(param.getSymbol(), param.getIdsList(), asyncResult -> {
			if(asyncResult.succeeded()) {
				
			} else {
				
			}
		});
	}
	
	private void bestQuote(UserConnect connect, ReqApiMsg param, int seqId) {
		connect.getAsyncClient().getBestQuote(param.getSymbol(), asyncResult -> {
			if(asyncResult.succeeded()) {
				BestQuote bestQuote = asyncResult.getData();
				ResApiMsg.Builder builder = ResApiMsg.newBuilder();
				builder.setBestQuote(HbMsgBuilder.buildBestQuoteMsg(bestQuote));
				send(connect, builder, seqId);
			} else {
				
			}
		});
	}
	
	private void monitorOrder(UserConnect connect, String symbols) {
		//监听订单变化
		connect.getAuthSubscriptionClient().subscribeOrderUpdateEvent(symbols, orderUpdateEvent -> {
			OrderUpdateEventMsg.Builder orderUpdateEventBuilder = OrderUpdateEventMsg.newBuilder();
			orderUpdateEventBuilder.setSymbol(orderUpdateEvent.getSymbol());
			orderUpdateEventBuilder.setTimestamp(orderUpdateEvent.getTimestamp());
			if(orderUpdateEvent.getData() != null) {
				orderUpdateEventBuilder.setOrder(HbMsgBuilder.buildOrdermsg(orderUpdateEvent.getData()));
			}
			ResApiMsg.Builder builder = ResApiMsg.newBuilder();
			builder.setOrderUdateEvent(orderUpdateEventBuilder);
			send(connect, builder, 0);
		});
	}
	
	
	
	private void send(UserConnect connect, ResApiMsg.Builder builder, int seqId) {
		PBMessage pack = MessageUtil.buildMessage(ResCode.HB_API, builder, seqId);
		connect.send(pack);
	}
	
}

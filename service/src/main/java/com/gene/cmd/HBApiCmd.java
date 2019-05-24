package com.gene.cmd;


import java.math.BigDecimal;
import java.util.List;

import com.gene.connect.User;
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
import com.gene.util.OS;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Order;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.HistoricalOrdersRequest;
import com.huobi.client.model.request.NewOrderRequest;
import com.huobi.client.model.request.OpenOrderRequest;

@Cmd(code=ReqCode.HB_API, os=OS.HUOBI, desc="火币api")
public class HBApiCmd extends Command {

	@Override
	public void execute(User user, PBMessage packet) throws Exception {
		if(user.getHbUser() == null) {
			ErrorUtil.error(user.getChannel(), packet, "Hb not login.");
			return;
		}
		ReqApiMsg param = ReqApiMsg.parseFrom(packet.getBytes());
		ReqType type = param.getType();
		switch (type.getNumber()) {
		case ReqType.OPENORDERS_VALUE:
			openOrders(user, param, packet);
			break;
		case ReqType.ORDERHISTORY_VALUE:
			orderHistory(user, param, packet);
			break;
		case ReqType.CREATEORDER_VALUE:
			createOrder(user, param, packet);
			break;
		case ReqType.CANCELORDER_VALUE:
			cancelOrder(user, param, packet);
			break;
		case ReqType.BESTQUOTE_VALUE:
			bestQuote(user, param, packet);
			break;
		case ReqType.MONITORORDER_VALUE:
			monitorOrder(user, param.getSymbols(), packet);
			break;
		default:
			ErrorUtil.error(user.getChannel(), packet, "type not suport! type:" + type.name());
			break;
		}
	}
	
	private void openOrders(User user, ReqApiMsg param, PBMessage packet) {
		OpenOrderRequest openOrderRequest = new OpenOrderRequest(param.getSymbol(), AccountType.lookup(param.getAccountType()));
		user.getHbUser().getAuthAsyncClient().getOpenOrders(openOrderRequest, asyncResult -> {
			if(asyncResult.succeeded()) {
				List<Order> orders = asyncResult.getData();
				ResApiMsg.Builder builder = ResApiMsg.newBuilder();
				orders.forEach(order -> {
					builder.addOpenOrders(HbMsgBuilder.buildOrdermsg(order));
				});
				send(user, builder, packet.getSeqId(), packet.getOs());
			} else {
				
			}
		});
	}
	
	private void orderHistory(User user, ReqApiMsg param, PBMessage packet) {
		HistoricalOrdersRequest historicalOrdersRequest = new HistoricalOrdersRequest(param.getSymbol(), OrderState.lookup(param.getOrderState()));
		user.getHbUser().getAuthAsyncClient().getHistoricalOrders(historicalOrdersRequest, asyncResult -> {
			if(asyncResult.succeeded()) {
				List<Order> orders = asyncResult.getData();
				ResApiMsg.Builder builder = ResApiMsg.newBuilder();
				orders.forEach(order -> {
					builder.addOrderHistory(HbMsgBuilder.buildOrdermsg(order));
				});
				send(user, builder, packet.getSeqId(), packet.getOs());
			} else {
				
			}
		});
	}
	
	private void createOrder(User user, ReqApiMsg param, PBMessage packet) {
		NewOrderRequest newOrderRequest = new NewOrderRequest(param.getSymbol(), 
				AccountType.lookup(param.getAccountType()), 
				OrderType.lookup(param.getOrderType()), new BigDecimal(param.getAmount()), new BigDecimal(param.getPrice()));
		user.getHbUser().getAuthAsyncClient().createOrder(newOrderRequest, asyncResult -> {
			if(asyncResult.succeeded()) {
				monitorOrder(user, param.getSymbol(), packet);
			} else {
				
			}
		});
	}
	private void cancelOrder(User user, ReqApiMsg param, PBMessage packet) {
		user.getHbUser().getAuthAsyncClient().cancelOrders(param.getSymbol(), param.getIdsList(), asyncResult -> {
			if(asyncResult.succeeded()) {
				
			} else {
				
			}
		});
	}
	
	private void bestQuote(User user, ReqApiMsg param, PBMessage packet) {
		user.getHbUser().getAsyncClient().getBestQuote(param.getSymbol(), asyncResult -> {
			if(asyncResult.succeeded()) {
				BestQuote bestQuote = asyncResult.getData();
				ResApiMsg.Builder builder = ResApiMsg.newBuilder();
				builder.setBestQuote(HbMsgBuilder.buildBestQuoteMsg(bestQuote));
				send(user, builder, packet.getSeqId(), packet.getOs());
			} else {
				
			}
		});
	}
	
	private void monitorOrder(User user, String symbols, PBMessage packet) {
		//监听订单变化
		user.getHbUser().getAuthSubscriptionClient().subscribeOrderUpdateEvent(symbols, orderUpdateEvent -> {
			OrderUpdateEventMsg.Builder orderUpdateEventBuilder = OrderUpdateEventMsg.newBuilder();
			orderUpdateEventBuilder.setSymbol(orderUpdateEvent.getSymbol());
			orderUpdateEventBuilder.setTimestamp(orderUpdateEvent.getTimestamp());
			if(orderUpdateEvent.getData() != null) {
				orderUpdateEventBuilder.setOrder(HbMsgBuilder.buildOrdermsg(orderUpdateEvent.getData()));
			}
			ResApiMsg.Builder builder = ResApiMsg.newBuilder();
			builder.setOrderUdateEvent(orderUpdateEventBuilder);
			send(user, builder, 0, packet.getOs());
		});
	}
	
	
	
	private void send(User user, ResApiMsg.Builder builder, int seqId, short os) {
		PBMessage pack = MessageUtil.buildMessage(ResCode.HB_API, builder, seqId, os);
		user.send(pack);
	}
	
}

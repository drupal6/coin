package com.gene.service;

import com.gene.proto.HBApiProto.AccountChangeMsg;
import com.gene.proto.HBApiProto.AccountEventMsg;
import com.gene.proto.HBApiProto.AccountMsg;
import com.gene.proto.HBApiProto.BalanceMsg;
import com.gene.proto.HBApiProto.BestQuoteMsg;
import com.gene.proto.HBApiProto.OrderMsg;
import com.huobi.client.model.Account;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Order;
import com.huobi.client.model.event.AccountEvent;

public class HbMsgBuilder {

	public static AccountMsg buildAccountMsg(Account account) {
		AccountMsg.Builder builder = AccountMsg.newBuilder();
		builder.setId(account.getId());
		builder.setAccountType(account.getType().name());
		builder.setAccountState(account.getState().name());
		account.getBalances().forEach(balance -> {
			BalanceMsg.Builder blanceBuilder = BalanceMsg.newBuilder();
			blanceBuilder.setCurrency(balance.getCurrency());
			blanceBuilder.setBalanceType(balance.getType().name());
			blanceBuilder.setBalance(balance.getBalance().toString());
			builder.addBlances(blanceBuilder);
		});
		return builder.build();
	}
	
	public static AccountEventMsg buildAccountChangeMsg(AccountEvent accountEvent) {
		AccountEventMsg.Builder builder = AccountEventMsg.newBuilder();
		builder.setTimestamp(accountEvent.getTimestamp());
		builder.setChangeType(accountEvent.getChangeType().name());
		accountEvent.getData().forEach(accountChange -> {
			AccountChangeMsg.Builder accountChangeBuilder = AccountChangeMsg.newBuilder();
			accountChangeBuilder.setCurrency(accountChange.getCurrency());
			accountChangeBuilder.setAccountType(accountChange.getAccountType().name());
			accountChangeBuilder.setBalance(accountChange.getBalance().toString());
			accountChangeBuilder.setBalanceType(accountChange.getBalanceType().name());
			builder.addChanges(accountChangeBuilder);
		});
		return builder.build();
	}
	
	public static OrderMsg buildOrdermsg(Order order) {
		OrderMsg.Builder builder = OrderMsg.newBuilder();
		builder.setAccountType(order.getAccountType().name());
		builder.setAmount(order.getAmount().toString());
		builder.setPrice(order.getPrice().toString());
		builder.setCreatedTimestamp(order.getCreatedTimestamp());
		builder.setCanceledTimestamp(order.getCanceledTimestamp());
		builder.setFinishedTimestamp(order.getFinishedTimestamp());
		builder.setOrderId(order.getOrderId());
		builder.setSymbol(order.getSymbol());
		if(order.getType() != null) {
			builder.setOrderType(order.getType().name());
		}
		builder.setFilledAmount(order.getFilledAmount().toString());
		builder.setFilledCashAmount(order.getFilledCashAmount().toString());
		builder.setFilledFees(order.getFilledFees().toString());
		if(order.getSource() != null) {
			builder.setOrderSource(order.getSource().name());
		}
		if(order.getState() != null) {
			builder.setOrderState(order.getState().name());
		}
		return builder.build();
	}
	
	public static BestQuoteMsg buildBestQuoteMsg(BestQuote bestQuote) {
		BestQuoteMsg.Builder builder = BestQuoteMsg.newBuilder();
		builder.setTimestamp(bestQuote.getTimestamp());
		builder.setAskAmount(bestQuote.getAskAmount().toString());
		builder.setAskPrice(bestQuote.getAskPrice().toString());
		builder.setBidAmount(bestQuote.getBidAmount().toString());
		builder.setBidPrice(bestQuote.getBidPrice().toString());
		return builder.build();
	}
}

package com.gene.util;

import com.gene.proto.BeanProto.AccountChangeMsg;
import com.gene.proto.BeanProto.AccountEventMsg;
import com.gene.proto.BeanProto.AccountMsg;
import com.gene.proto.BeanProto.BalanceMsg;
import com.gene.proto.BeanProto.BestQuoteMsg;
import com.gene.proto.BeanProto.OrderMsg;
import com.huobi.client.model.Account;
import com.huobi.client.model.AccountChange;
import com.huobi.client.model.Balance;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Order;
import com.huobi.client.model.event.AccountEvent;

public class HbMsgBuilder {

	public static AccountMsg buildAccountMsg(Account account) {
		AccountMsg.Builder builder = AccountMsg.newBuilder();
		builder.setId(account.getId());
		builder.setType(account.getType().name());
		builder.setState(account.getState().name());
		account.getBalances().forEach(balance -> {
			builder.addBalances(buildBanlace(balance));
		});
		return builder.build();
	}
	
	public static BalanceMsg buildBanlace(Balance balance) {
		BalanceMsg.Builder blanceBuilder = BalanceMsg.newBuilder();
		blanceBuilder.setCurrency(balance.getCurrency());
		blanceBuilder.setType(balance.getType().name());
		blanceBuilder.setBalance(balance.getBalance().toString());
		return blanceBuilder.build();
	}
	
	public static AccountEventMsg buildAccountChangeMsg(AccountEvent accountEvent) {
		AccountEventMsg.Builder builder = AccountEventMsg.newBuilder();
		builder.setTimestamp(accountEvent.getTimestamp());
		builder.setChangeType(accountEvent.getChangeType().name());
		accountEvent.getData().forEach(accountChange -> {
			builder.addChanges(buildAccountChange(accountChange));
		});
		return builder.build();
	}
	
	public static AccountChangeMsg buildAccountChange(AccountChange accountChange) {
		AccountChangeMsg.Builder accountChangeBuilder = AccountChangeMsg.newBuilder();
		accountChangeBuilder.setCurrency(accountChange.getCurrency());
		accountChangeBuilder.setAccountType(accountChange.getAccountType().name());
		accountChangeBuilder.setBalance(accountChange.getBalance().toString());
		accountChangeBuilder.setBalanceType(accountChange.getBalanceType().name());
		return accountChangeBuilder.build();
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
			builder.setType(order.getType().name());
		}
		builder.setFilledAmount(order.getFilledAmount().toString());
		builder.setFilledCashAmount(order.getFilledCashAmount().toString());
		builder.setFilledFees(order.getFilledFees().toString());
		if(order.getSource() != null) {
			builder.setSource(order.getSource().name());
		}
		if(order.getState() != null) {
			builder.setState(order.getState().name());
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

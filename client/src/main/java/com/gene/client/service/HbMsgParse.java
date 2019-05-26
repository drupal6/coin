package com.gene.client.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.gene.proto.HBApiProto.AccountEventMsg;
import com.gene.proto.HBApiProto.AccountMsg;
import com.gene.proto.HBApiProto.BestQuoteMsg;
import com.gene.proto.HBApiProto.OrderMsg;
import com.gene.proto.HBApiProto.OrderUpdateEventMsg;
import com.huobi.client.model.Account;
import com.huobi.client.model.AccountChange;
import com.huobi.client.model.Balance;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Order;
import com.huobi.client.model.enums.AccountChangeType;
import com.huobi.client.model.enums.AccountState;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceType;
import com.huobi.client.model.enums.OrderSource;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.event.AccountEvent;
import com.huobi.client.model.event.OrderUpdateEvent;

public class HbMsgParse {

	public static Account parseAccount(AccountMsg accountMsg) {
		Account account = new Account();
		account.setId(accountMsg.getId());
		account.setType(AccountType.lookup(accountMsg.getAccountType()));
		account.setState(AccountState.lookup(accountMsg.getAccountState()));
		List<Balance> balances = new ArrayList<>();
		accountMsg.getBlancesList().forEach(balanceMsg -> {
			Balance b = new Balance();
			b.setCurrency(balanceMsg.getCurrency());
			b.setType(BalanceType.lookup(balanceMsg.getBalanceType()));
			b.setBalance(new BigDecimal(balanceMsg.getBalance()));
			balances.add(b);
		});
		account.setBalances(balances);
		return account;
	}
	
	public static AccountEvent parseAccountEvent(AccountEventMsg accountEventMsg) {
		AccountEvent event = new AccountEvent();
		event.setTimestamp(accountEventMsg.getTimestamp());
		event.setChangeType(AccountChangeType.lookup(accountEventMsg.getChangeType()));
		accountEventMsg.getChangesList().forEach(changes -> {
			AccountChange ac = new AccountChange();
			ac.setCurrency(changes.getCurrency());
			ac.setAccountType(AccountType.lookup(changes.getAccountType()));
			ac.setBalance(new BigDecimal(changes.getBalance()));
			ac.setBalanceType(BalanceType.lookup(changes.getBalanceType()));
			event.getData().add(ac);
		});
		return event;
	}
	
	public static Order parseOrder(OrderMsg orderMsg) {
		Order o = new Order();
		o.setAccountType(AccountType.lookup(orderMsg.getOrderType()));
		o.setAmount(new BigDecimal(orderMsg.getAmount()));
		o.setPrice(new BigDecimal(orderMsg.getPrice()));
		o.setCreatedTimestamp(orderMsg.getCreatedTimestamp());
		o.setCanceledTimestamp(orderMsg.getCanceledTimestamp());
		o.setFinishedTimestamp(orderMsg.getFinishedTimestamp());
		o.setOrderId(orderMsg.getOrderId());
		o.setSymbol(orderMsg.getSymbol());
		o.setType(OrderType.lookup(orderMsg.getOrderType()));
		o.setFilledAmount(new BigDecimal(orderMsg.getFilledAmount()));
		o.setFilledCashAmount(new BigDecimal(orderMsg.getFilledCashAmount()));
		o.setFilledFees(new BigDecimal(orderMsg.getFilledFees()));
		o.setSource(OrderSource.lookup(orderMsg.getOrderSource()));
		o.setState(OrderState.lookup(orderMsg.getOrderState()));
		return o;
	}
	
	public static OrderUpdateEvent parseOrderUpdateEvent(OrderUpdateEventMsg orderUpdateEventMsg) {
		OrderUpdateEvent orderUpdateEvent = new OrderUpdateEvent();
		orderUpdateEvent.setSymbol(orderUpdateEventMsg.getSymbol());
		orderUpdateEvent.setTimestamp(orderUpdateEventMsg.getTimestamp());
		orderUpdateEvent.setData(parseOrder(orderUpdateEventMsg.getOrder()));
		return orderUpdateEvent;
	}
	
	public static BestQuote parseBestQuote(BestQuoteMsg bestQuoteMsg) {
		BestQuote bestQuote = new BestQuote();
		bestQuote.setTimestamp(bestQuoteMsg.getTimestamp());
		bestQuote.setAskPrice(new BigDecimal(bestQuoteMsg.getAskPrice()));
		bestQuote.setAskAmount(new BigDecimal(bestQuoteMsg.getAskAmount()));
		bestQuote.setBidPrice(new BigDecimal(bestQuoteMsg.getBidPrice()));
		bestQuote.setBidAmount(new BigDecimal(bestQuoteMsg.getBidAmount()));
		return bestQuote;
	}
}

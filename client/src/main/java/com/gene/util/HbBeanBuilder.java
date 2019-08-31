package com.gene.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import com.huobi.client.model.enums.AccountChangeType;
import com.huobi.client.model.enums.AccountState;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceType;
import com.huobi.client.model.enums.OrderSource;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.event.AccountEvent;

public class HbBeanBuilder {
	
	public static Account parseAccount(AccountMsg msg) {
		Account account = new Account();
		account.setId(msg.getId());
		account.setType(AccountType.valueOf(msg.getType()));
		account.setState(AccountState.valueOf(msg.getState()));
		List<Balance> balances = new ArrayList<>();
		msg.getBalancesList().forEach(bmsg -> {
			balances.add(parseBalance(bmsg));
		});
		account.setBalances(balances);
		return account;
	}
	
	public static Balance parseBalance(BalanceMsg balanceMsg) {
		Balance balance = new Balance();
		balance.setCurrency(balanceMsg.getCurrency());
		balance.setType(BalanceType.valueOf(balanceMsg.getType()));
		balance.setBalance(new BigDecimal(balanceMsg.getBalance()));
		return balance;
	}
	
	public static AccountEvent parseAccountEvent(AccountEventMsg msg) {
		AccountEvent accountEvent = new AccountEvent();
		accountEvent.setTimestamp(msg.getTimestamp());
		accountEvent.setChangeType(AccountChangeType.valueOf(msg.getChangeType()));
		msg.getChangesList().forEach(changeMsg -> {
			accountEvent.getData().add(parseAccountChange(changeMsg));
		});
		return accountEvent;
	}
	
	public static AccountChange parseAccountChange(AccountChangeMsg msg) {
		AccountChange accountChange = new AccountChange();
		accountChange.setCurrency(msg.getCurrency());
		accountChange.setAccountType(AccountType.valueOf(msg.getAccountType()));
		accountChange.setBalance(new BigDecimal(msg.getBalance()));
		accountChange.setBalanceType(BalanceType.valueOf(msg.getBalanceType()));
		return accountChange;
	}

	public static Order parseOrder(OrderMsg msg) {
		Order order = new Order();
		order.setAccountType(AccountType.valueOf(msg.getAccountType()));
		order.setAmount(new BigDecimal(msg.getAmount()));
		order.setPrice(new BigDecimal(msg.getPrice())); 
		order.setCreatedTimestamp(msg.getCreatedTimestamp());
		order.setCanceledTimestamp(msg.getCanceledTimestamp());
		order.setFinishedTimestamp(msg.getFinishedTimestamp());
		order.setOrderId(msg.getOrderId());
		order.setSymbol(msg.getSymbol());
		order.setType(OrderType.valueOf(msg.getType()));
		order.setFilledAmount(new BigDecimal(msg.getFilledAmount()));
		order.setFilledCashAmount(new BigDecimal(msg.getFilledCashAmount()));
		order.setFilledFees(new BigDecimal(msg.getFilledFees()));
		order.setSource(OrderSource.valueOf(msg.getSource()));
		order.setState(OrderState.valueOf(msg.getState()));
		return order;
	}
	
	public static BestQuote parseBestQuote(BestQuoteMsg msg) {
		BestQuote bestQuote = new BestQuote();
		bestQuote.setTimestamp(msg.getTimestamp());
		bestQuote.setAskPrice(new BigDecimal(msg.getAskPrice()));
		bestQuote.setAskAmount(new BigDecimal(msg.getAskAmount()));
		bestQuote.setBidPrice(new BigDecimal(msg.getBidPrice()));
		bestQuote.setBidAmount(new BigDecimal(msg.getBidAmount()));
		return bestQuote;
	}
}

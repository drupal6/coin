package com.gene.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huobi.client.model.Account;
import com.huobi.client.model.Order;
import com.huobi.client.model.enums.AccountType;

public class HbClient {
	
	private final Client client;
	
	private Map<AccountType, Account> accountMap = new HashMap<AccountType, Account>();

	private List<Order> openOrders;
	
	private List<Order> orderHistory;
	
	public HbClient(Client client) {
		this.client = client;
	}
	
	public Client getClient() {
		return client;
	}

	public Account getAccount(AccountType accountType) {
		return accountMap.get(accountType);
	}

	public void setAccount(Account account) {
		this.accountMap.put(account.getType(), account);
	}

	public List<Order> getOpenOrders() {
		return openOrders;
	}

	public void setOpenOrders(List<Order> openOrders) {
		this.openOrders = openOrders;
	}

	public List<Order> getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(List<Order> orderHistory) {
		this.orderHistory = orderHistory;
	}
}

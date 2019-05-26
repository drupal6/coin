package com.gene.client;

import java.util.List;

import com.huobi.client.model.Account;
import com.huobi.client.model.Order;

public class HbClient {
	
	private Account account;

	private List<Order> openOrders;
	
	private List<Order> orderHistory;
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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

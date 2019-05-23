package com.gene.service;

import java.math.BigDecimal;
import java.util.List;

import com.huobi.client.model.Account;
import com.huobi.client.model.BatchCancelResult;
import com.huobi.client.model.Deposit;
import com.huobi.client.model.Order;
import com.huobi.client.model.Withdraw;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.CancelOpenOrderRequest;
import com.huobi.client.model.request.HistoricalOrdersRequest;
import com.huobi.client.model.request.NewOrderRequest;
import com.huobi.client.model.request.WithdrawRequest;

public class HbAuthService {
	
	/**
	 * 获取账号
	 * @param apiKey
	 * @param secretKey
	 * @param type
	 * @return
	 */
	public Account getAcountBalance(String apiKey, String secretKey, AccountType type) {
		return HbClientBuilder.syncClient(apiKey, secretKey).getAccountBalance(type);
	}
	
	/**
	 * 提币
	 * @param apiKey
	 * @param secretKey
	 * @param address
	 * @param amount
	 * @param currency
	 * @return
	 */
	public long withdraw(String apiKey, String secretKey, String address, BigDecimal amount, String currency) {
		WithdrawRequest request = new WithdrawRequest(address, amount, currency);
		return HbClientBuilder.syncClient(apiKey, secretKey).withdraw(request);
	}
	
	/**
	 * 取消提币
	 * @param apiKey
	 * @param secretKey
	 * @param address
	 * @param amount
	 * @param currency
	 * @return
	 */
	public void chanceWithdraw(String apiKey, String secretKey, String currency, long id) {
		HbClientBuilder.syncClient(apiKey, secretKey).cancelWithdraw(currency, id);
	}
	
	/**
	 * 获取提币历史
	 * @param apiKey
	 * @param secretKey
	 * @param currency
	 * @param fromId
	 * @param size
	 * @return
	 */
	public List<Withdraw> getWithdrawHistory(String apiKey, String secretKey, String currency, long fromId, int size) {
		return HbClientBuilder.syncClient(apiKey, secretKey).getWithdrawHistory(currency, fromId, size);
	}
	
	/**
	 * 获取存币历史
	 * @param apiKey
	 * @param secretKey
	 * @param currency
	 * @param fromId
	 * @param size
	 * @return
	 */
	public List<Deposit> getDepositHistory(String apiKey, String secretKey, String currency, long fromId, int size) {
		return HbClientBuilder.syncClient(apiKey, secretKey).getDepositHistory(currency, fromId, size);
	}
	
	/**
	 * 创建订单
	 * @param apiKey
	 * @param secretKey
	 * @param currency
	 * @param accountType
	 * @param orderType
	 * @param amount
	 * @param price
	 * @return
	 */
	public long createOrder(String apiKey, String secretKey, String currency, AccountType accountType, OrderType orderType, BigDecimal amount, BigDecimal price) {
		NewOrderRequest newOrderRequest = new NewOrderRequest(currency, accountType, orderType, amount, price);
		return HbClientBuilder.syncClient(apiKey, secretKey).createOrder(newOrderRequest);
	}
	
	/**
	 * 取消订单
	 * @param apiKey
	 * @param secretKey
	 * @param currency
	 * @param id
	 */
	public void cancelOrder(String apiKey, String secretKey, String currency, long id) {
		HbClientBuilder.syncClient(apiKey, secretKey).cancelOrder(currency, id);
	}
	
	/**
	 * 取消还没有生效的订单
	 * @param apiKey
	 * @param secretKey
	 * @param currency
	 * @param accountType
	 * @return
	 */
	public BatchCancelResult cancelOpenOrder(String apiKey, String secretKey, String currency, AccountType accountType) {
		CancelOpenOrderRequest request = new CancelOpenOrderRequest(currency, accountType);
		return HbClientBuilder.syncClient(apiKey, secretKey).cancelOpenOrders(request);
	}
	
	/**
	 * 获取订单信息
	 * @param apiKey
	 * @param secretKey
	 * @param currency
	 * @param id
	 * @return
	 */
	public Order getOrder(String apiKey, String secretKey, String currency, long id) {
		return HbClientBuilder.syncClient(apiKey, secretKey).getOrder(currency, id);
	}
	
	/**
	 * 获取订单历史
	 * @param apiKey
	 * @param secretKey
	 * @param currency
	 * @param orderState
	 * @return
	 */
	public List<Order> getHistoricalOrders(String apiKey, String secretKey, String currency, OrderState orderState) {
		HistoricalOrdersRequest request =  new HistoricalOrdersRequest(currency, orderState);
		return HbClientBuilder.syncClient(apiKey, secretKey).getHistoricalOrders(request);
	}
}

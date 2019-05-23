package com.gene.service;

import com.huobi.client.SubscriptionListener;
import com.huobi.client.model.enums.BalanceMode;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.event.AccountEvent;
import com.huobi.client.model.event.CandlestickEvent;
import com.huobi.client.model.event.OrderUpdateEvent;
import com.huobi.client.model.event.TradeEvent;

public class HbSubService {
	
	/**
	 * 监听交易信息更新
	 * @param symbols
	 * @param callback
	 */
	public void subTradeUpdateEvent(String symbols, SubscriptionListener<TradeEvent> callback) {
		HbClientBuilder.subClient().subscribeTradeEvent(symbols, callback);
	}
	
	/**
	 * k线监听
	 * @param symbols
	 * @param interval
	 * @param callback
	 */
	public void subCandlestickEvent(String symbols, CandlestickInterval interval,
		      SubscriptionListener<CandlestickEvent> callback) {
		HbClientBuilder.subClient().subscribeCandlestickEvent(symbols, interval, callback);
	}
	
	/**
	 * 监听订单更新
	 * @param symbols
	 * @param callback
	 */
	public void subOrderUpdateEvent(String symbols, SubscriptionListener<OrderUpdateEvent> callback) {
		HbClientBuilder.subClient().subscribeOrderUpdateEvent(symbols, callback);
	}
	
	/**
	 * 监听账号变动
	 * @param apiKey
	 * @param sceretKey
	 * @param mode
	 * @param callback
	 */
	public void subscribeAccountEvent(String apiKey, String sceretKey, BalanceMode mode, SubscriptionListener<AccountEvent> callback) {
		HbClientBuilder.subClient(apiKey, sceretKey).subscribeAccountEvent(mode, callback);
	}
}

package com.gene.service;

import java.util.List;

import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.PriceDepth;
import com.huobi.client.model.enums.CandlestickInterval;

public class HbService implements CoinService {

	/**
	 * 获取k线图
	 * @param symbol
	 * @param interval
	 * @param size
	 * @return
	 */
	public List<Candlestick> getCandlestick(String symbol, CandlestickInterval interval, int size) {
		return SyncRequestClient.create().getLatestCandlestick(symbol, interval, size);
	}
	
	/**
	 * 获得symbol最佳出价
	 * @param symbol
	 * @return
	 */
	public BestQuote getBesetQuote(String symbol) {
		return SyncRequestClient.create().getBestQuote(symbol);
	}
	
	/**
	 * 获取价格深度
	 * @param symbol
	 * @param size
	 * @return
	 */
	public PriceDepth getPriceDepth(String symbol, int size) {
		return SyncRequestClient.create().getPriceDepth(symbol, size);
	}
}

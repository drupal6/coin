package com.gene.service;

import java.util.List;

import com.gene.HbClientBuilder;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.PriceDepth;
import com.huobi.client.model.Trade;
import com.huobi.client.model.TradeStatistics;
import com.huobi.client.model.enums.CandlestickInterval;

public class HbMarkService {
	/**
	 * 获取服务器时间utc
	 * @return
	 */
	public long getExchangeTimestamp() {
		return HbClientBuilder.syncClient().getExchangeTimestamp();
	}
	
	/**
	 * 获取k线数据
	 * @param symbol
	 * @param interval
	 * @param size[1 - 2000]
	 * @return
	 */
	public List<Candlestick> getLatestCandlestick(String symbol, CandlestickInterval interval, int size) {
		return HbClientBuilder.syncClient().getLatestCandlestick(symbol, interval, size);
	}
	
	/**
	 * 获取交易深度
	 * @param symbol
	 * @param size[1 - 150]
	 * @return
	 */
	public PriceDepth getPriceDepth(String symbol, int size) {
		return HbClientBuilder.syncClient().getPriceDepth(symbol, size);
	}
	
	/**
	 * 获取最新的交易信息
	 * @param symbol
	 * @return
	 */
	public Trade getLastTrade(String symbol) {
		return HbClientBuilder.syncClient().getLastTrade(symbol);
	}
	
	/**
	 * 获取最好的出手价
	 * @param symbol
	 * @return
	 */
	public BestQuote getBestQuote(String symbol) {
		return HbClientBuilder.syncClient().getBestQuote(symbol);
	}
	
	/**
	 * 获取交易对
	 * @param symbol
	 * @param size[1 - 2000]
	 * @return
	 */
	public List<Trade> getHistoricalTrade(String symbol, int size) {
		return HbClientBuilder.syncClient().getHistoricalTrade(symbol, size);
	}
	
	/**
	 * 获取24小时统计数据
	 * @param symbol
	 * @return
	 */
	public TradeStatistics geTradeStatistics(String symbol) {
		return HbClientBuilder.syncClient().get24HTradeStatistics(symbol);
	}
}

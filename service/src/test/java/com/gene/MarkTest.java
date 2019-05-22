package com.gene;

import java.util.List;

import com.gene.service.HbMarkService;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.enums.CandlestickInterval;

public class MarkTest {

	public static void main(String[] args) {
		HbMarkService mark = new HbMarkService();
		System.out.println("ExchangeTimestamp:" + mark.getExchangeTimestamp());
		List<Candlestick> candlesticks = mark.getLatestCandlestick("btcusdt", CandlestickInterval.MIN15, 2000);
		candlesticks.forEach(candlestick -> {
			System.out.println("Timestamp:" + candlestick.getTimestamp() + " amount:" + candlestick.getAmount() + " count:" + candlestick.getCount()
					+ " open:" + candlestick.getOpen() + " close:" + candlestick.getClose() + " low:" + candlestick.getLow() + " high:" + candlestick.getHigh()
					+ " volume:" + candlestick.getVolume());
		});
	}

}

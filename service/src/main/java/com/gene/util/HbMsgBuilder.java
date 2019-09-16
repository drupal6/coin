package com.gene.util;

import com.gene.proto.BeanProto.AccountChangeMsg;
import com.gene.proto.BeanProto.AccountEventMsg;
import com.gene.proto.BeanProto.AccountMsg;
import com.gene.proto.BeanProto.BalanceMsg;
import com.gene.proto.BeanProto.BatchCancelResultMsg;
import com.gene.proto.BeanProto.BestQuoteMsg;
import com.gene.proto.BeanProto.CandlestickEventMsg;
import com.gene.proto.BeanProto.CandlestickMsg;
import com.gene.proto.BeanProto.CompleteSubAccountInfoMsg;
import com.gene.proto.BeanProto.DepositMsg;
import com.gene.proto.BeanProto.DepthEntryMsg;
import com.gene.proto.BeanProto.EtfSwapConfigMsg;
import com.gene.proto.BeanProto.EtfSwapHistoryMsg;
import com.gene.proto.BeanProto.ExchangeInfoMsg;
import com.gene.proto.BeanProto.LastTradeAndBestQuoteMsg;
import com.gene.proto.BeanProto.LoanMsg;
import com.gene.proto.BeanProto.MarginBalanceDetailMsg;
import com.gene.proto.BeanProto.MatchResultMsg;
import com.gene.proto.BeanProto.OrderMsg;
import com.gene.proto.BeanProto.OrderUpdateEventMsg;
import com.gene.proto.BeanProto.PriceDepthEventMsg;
import com.gene.proto.BeanProto.PriceDepthMsg;
import com.gene.proto.BeanProto.SymbolMsg;
import com.gene.proto.BeanProto.TradeEventMsg;
import com.gene.proto.BeanProto.TradeMsg;
import com.gene.proto.BeanProto.TradeStatisticsEventMsg;
import com.gene.proto.BeanProto.TradeStatisticsMsg;
import com.gene.proto.BeanProto.UnitPriceMsg;
import com.huobi.client.model.Account;
import com.huobi.client.model.AccountChange;
import com.huobi.client.model.Balance;
import com.huobi.client.model.BatchCancelResult;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.CompleteSubAccountInfo;
import com.huobi.client.model.Deposit;
import com.huobi.client.model.DepthEntry;
import com.huobi.client.model.EtfSwapConfig;
import com.huobi.client.model.EtfSwapHistory;
import com.huobi.client.model.ExchangeInfo;
import com.huobi.client.model.LastTradeAndBestQuote;
import com.huobi.client.model.Loan;
import com.huobi.client.model.MarginBalanceDetail;
import com.huobi.client.model.MatchResult;
import com.huobi.client.model.Order;
import com.huobi.client.model.PriceDepth;
import com.huobi.client.model.Symbol;
import com.huobi.client.model.Trade;
import com.huobi.client.model.TradeStatistics;
import com.huobi.client.model.UnitPrice;
import com.huobi.client.model.event.AccountEvent;
import com.huobi.client.model.event.CandlestickEvent;
import com.huobi.client.model.event.OrderUpdateEvent;
import com.huobi.client.model.event.PriceDepthEvent;
import com.huobi.client.model.event.TradeEvent;
import com.huobi.client.model.event.TradeStatisticsEvent;

public class HbMsgBuilder {

	public static AccountMsg buildAccountMsg(Account account) {
		AccountMsg.Builder accountBuilder = AccountMsg.newBuilder();
		accountBuilder.setId(account.getId());
		accountBuilder.setType(account.getType().name());
		accountBuilder.setState(account.getState().name());
		account.getBalances().forEach(balance -> {
			accountBuilder.addBalances(buildBanlaceMsg(balance));
		});
		return accountBuilder.build();
	}
	
	public static AccountChangeMsg buildAccountChangeMsg(AccountChange accountChange) {
		AccountChangeMsg.Builder accountChangeBuilder = AccountChangeMsg.newBuilder();
		accountChangeBuilder.setCurrency(accountChange.getCurrency());
		accountChangeBuilder.setAccountType(accountChange.getAccountType().name());
		accountChangeBuilder.setBalance(accountChange.getBalance().toString());
		accountChangeBuilder.setBalanceType(accountChange.getBalanceType().name());
		return accountChangeBuilder.build();
	}
	
	public static BalanceMsg buildBanlaceMsg(Balance balance) {
		BalanceMsg.Builder blanceBuilder = BalanceMsg.newBuilder();
		blanceBuilder.setCurrency(balance.getCurrency());
		blanceBuilder.setType(balance.getType().name());
		blanceBuilder.setBalance(balance.getBalance().toString());
		return blanceBuilder.build();
	}
	
	public static BatchCancelResultMsg buildBatchCancelResultMsg(BatchCancelResult batchCancelResult) {
		BatchCancelResultMsg.Builder batchCancelResultBuilder = BatchCancelResultMsg.newBuilder();
		batchCancelResultBuilder.setSuccessCount(batchCancelResult.getSuccessCount());
		batchCancelResultBuilder.setFailedCount(batchCancelResult.getFailedCount());
		return batchCancelResultBuilder.build();
	}
	
	public static BestQuoteMsg buildBestQuoteMsg(BestQuote bestQuote) {
		BestQuoteMsg.Builder bestQuoteMsgBuilder = BestQuoteMsg.newBuilder();
		bestQuoteMsgBuilder.setTimestamp(bestQuote.getTimestamp());
		bestQuoteMsgBuilder.setAskAmount(bestQuote.getAskAmount().toString());
		bestQuoteMsgBuilder.setAskPrice(bestQuote.getAskPrice().toString());
		bestQuoteMsgBuilder.setBidAmount(bestQuote.getBidAmount().toString());
		bestQuoteMsgBuilder.setBidPrice(bestQuote.getBidPrice().toString());
		return bestQuoteMsgBuilder.build();
	}
	
	public static CandlestickMsg buildCandlestickMsg(Candlestick candlestick) {
		CandlestickMsg.Builder candlestickBuilder = CandlestickMsg.newBuilder();
		candlestickBuilder.setTimestamp(candlestick.getTimestamp());
		candlestickBuilder.setAmount(candlestick.getAmount().toString());
		candlestickBuilder.setCount(candlestick.getCount());
		candlestickBuilder.setOpen(candlestick.getOpen().toString());
		candlestickBuilder.setClose(candlestick.getClose().toString());
		candlestickBuilder.setLow(candlestick.getLow().toString());
		candlestickBuilder.setHigh(candlestick.getHigh().toString());
		candlestickBuilder.setVolume(candlestick.getVolume().toString());
		return candlestickBuilder.build();
	}
	
	public static CompleteSubAccountInfoMsg buildCompleteSubAccountInfoMsg(CompleteSubAccountInfo completeSubAccountInfo) {
		CompleteSubAccountInfoMsg.Builder completeSubAccountInfoBuilder = CompleteSubAccountInfoMsg.newBuilder();
		completeSubAccountInfoBuilder.setId(completeSubAccountInfo.getId());
		completeSubAccountInfoBuilder.setType(completeSubAccountInfo.getType().name());
		completeSubAccountInfo.getBalanceList().forEach(balance -> {
			completeSubAccountInfoBuilder.addBalances(buildBanlaceMsg(balance));
		});
		return completeSubAccountInfoBuilder.build();
	}
	
	public static DepositMsg buildDepositMsg(Deposit deposit) {
		DepositMsg.Builder depositMsgBuilder = DepositMsg.newBuilder();
		depositMsgBuilder.setId(deposit.getId());
		depositMsgBuilder.setCurrency(deposit.getCurrency());
		depositMsgBuilder.setTxHash(deposit.getTxHash());
		depositMsgBuilder.setAmount(deposit.getAmount().toString());
		depositMsgBuilder.setAddress(deposit.getAddress());
		depositMsgBuilder.setAddressTag(deposit.getAddressTag());
		depositMsgBuilder.setFee(deposit.getFee().toString());
		depositMsgBuilder.setCreatedTimestamp(deposit.getCreatedTimestamp());
		depositMsgBuilder.setUpdatedTimestamp(deposit.getUpdatedTimestamp());
		depositMsgBuilder.setDepositState(deposit.getDepositState().name());
		return depositMsgBuilder.build();
	}
	
	public static DepthEntryMsg buildDepthEntryMsg(DepthEntry depthEntry) {
		DepthEntryMsg.Builder depthEntryBuilder = DepthEntryMsg.newBuilder();
		depthEntryBuilder.setPrice(depthEntry.getPrice().toString());
		depthEntryBuilder.setAmount(depthEntry.getAmount().toString());
		return depthEntryBuilder.build();
	}
	
	public static EtfSwapConfigMsg buildEtfSwapConfigMsg(EtfSwapConfig etfSwapConfig) {
		EtfSwapConfigMsg.Builder etfSwapConfigBuilder = EtfSwapConfigMsg.newBuilder();
		etfSwapConfigBuilder.setPurchaseMinAmount(etfSwapConfig.getPurchaseMinAmount());
		etfSwapConfigBuilder.setPurchaseMaxAmount(etfSwapConfig.getPurchaseMaxAmount());
		etfSwapConfigBuilder.setRedemptionMinAmount(etfSwapConfig.getRedemptionMinAmount());
		etfSwapConfigBuilder.setRedemptionMaxAmount(etfSwapConfig.getRedemptionMaxAmount());
		etfSwapConfigBuilder.setPurchaseFeeRate(etfSwapConfig.getPurchaseFeeRate().toString());
		etfSwapConfigBuilder.setRedemptionFeeRate(etfSwapConfig.getRedemptionFeeRate().toString());
		etfSwapConfigBuilder.setStatus(etfSwapConfig.getStatus().toString());
		etfSwapConfig.getUnitPriceList().forEach(unitPrice -> {
			etfSwapConfigBuilder.addUnitPriceList(buildUnitPriceMsg(unitPrice));
		});
		return etfSwapConfigBuilder.build();
	}
	
	public static EtfSwapHistoryMsg buildEtfSwapHistoryMsg(EtfSwapHistory etfSwapHistory) {
		EtfSwapHistoryMsg.Builder etfSwapHistoryBuilder = EtfSwapHistoryMsg.newBuilder();
		etfSwapHistoryBuilder.setCreatedTimestamp(etfSwapHistory.getCreatedTimestamp());
		etfSwapHistoryBuilder.setCurrency(etfSwapHistory.getCurrency());
		etfSwapHistoryBuilder.setAmount(etfSwapHistory.getAmount().toString());
		etfSwapHistoryBuilder.setType(etfSwapHistory.getType().toString());
		etfSwapHistoryBuilder.setStatus(etfSwapHistory.getStatus());
		etfSwapHistoryBuilder.setRate(etfSwapHistory.getRate().toString());
		etfSwapHistoryBuilder.setFee(etfSwapHistory.getFee().toString());
		etfSwapHistoryBuilder.setPointCardAmount(etfSwapHistory.getPointCardAmount().toString());
		etfSwapHistory.getUsedCurrencyList().forEach(unitPrice -> {
			etfSwapHistoryBuilder.addUsedCurrencyList(buildUnitPriceMsg(unitPrice));
		});
		etfSwapHistory.getObtainCurrencyList().forEach(unitPrice -> {
			etfSwapHistoryBuilder.addObtainCurrencyList(buildUnitPriceMsg(unitPrice));
		});
		return etfSwapHistoryBuilder.build();
	}
	
	public static ExchangeInfoMsg buildExchangeInfoMsg(ExchangeInfo exchangeInfo) {
		ExchangeInfoMsg.Builder exchangeInfoBuilder = ExchangeInfoMsg.newBuilder();
		exchangeInfo.getSymbolList().forEach(symbol -> {
			exchangeInfoBuilder.addSymbolList(buildSymbolMsg(symbol));
		});
		exchangeInfo.getCurrencies().forEach(currencie -> {
			exchangeInfoBuilder.addCurrencies(currencie);
		});
		return exchangeInfoBuilder.build();
	}
	
	public static LastTradeAndBestQuoteMsg buildLastTradeAndBestQuoteMsg(LastTradeAndBestQuote lastTradeAndBestQuote) {
		LastTradeAndBestQuoteMsg.Builder lastTradeAndBestQuoteBuilder = LastTradeAndBestQuoteMsg.newBuilder();
		lastTradeAndBestQuoteBuilder.setLastTradePrice(lastTradeAndBestQuote.getLastTradePrice().toString());
		lastTradeAndBestQuoteBuilder.setLastTradeAmount(lastTradeAndBestQuote.getLastTradeAmount().toString());
		lastTradeAndBestQuoteBuilder.setAskPrice(lastTradeAndBestQuote.getAskPrice().toString());
		lastTradeAndBestQuoteBuilder.setAskAmount(lastTradeAndBestQuote.getAskAmount().toString());
		lastTradeAndBestQuoteBuilder.setBidPrice(lastTradeAndBestQuote.getBidPrice().toString());
		lastTradeAndBestQuoteBuilder.setBidAmount(lastTradeAndBestQuote.getBidAmount().toString());
		return lastTradeAndBestQuoteBuilder.build();
	}
	
	public static LoanMsg buildLoanMsg(Loan loan) {
		LoanMsg.Builder loanBuilder = LoanMsg.newBuilder();
		loanBuilder.setId(loan.getId());
		loanBuilder.setUserId(loan.getUserId());
		loanBuilder.setAccountType(loan.getAccountType().toString());
		loanBuilder.setSymbol(loan.getSymbol());
		loanBuilder.setCurrency(loan.getCurrency());
		loanBuilder.setLoanAmount(loan.getLoanAmount().toString());
		loanBuilder.setLoanBalance(loan.getLoanBalance().toString());
		loanBuilder.setInterestRate(loan.getInterestRate().toString());
		loanBuilder.setInterestAmount(loan.getInterestAmount().toString());
		loanBuilder.setInterestBalance(loan.getInterestBalance().toString());
		loanBuilder.setState(loan.getState().toString());
		loanBuilder.setCreatedTimestamp(loan.getCreatedTimestamp());
		loanBuilder.setAccruedTimestamp(loan.getAccruedTimestamp());
		return loanBuilder.build();
	}
	
	public static MarginBalanceDetailMsg buildMarginBalanceDetailMsg(MarginBalanceDetail marginBalanceDetail) {
		MarginBalanceDetailMsg.Builder marginBalanceDetailBuilder = MarginBalanceDetailMsg.newBuilder();
		marginBalanceDetailBuilder.setId(marginBalanceDetail.getId());
		marginBalanceDetailBuilder.setSymbol(marginBalanceDetail.getSymbol());
		marginBalanceDetailBuilder.setState(marginBalanceDetail.getState().toString());
		marginBalanceDetailBuilder.setType(marginBalanceDetail.getType().toString());
		marginBalanceDetailBuilder.setRiskRate(marginBalanceDetail.getRiskRate().toString());
		marginBalanceDetailBuilder.setFlPrice(marginBalanceDetail.getFlPrice().toString());
		marginBalanceDetailBuilder.setFlType(marginBalanceDetail.getFlType());
		marginBalanceDetail.getSubAccountBalance().forEach(balance -> {
			marginBalanceDetailBuilder.addSubAccountBalance(buildBanlaceMsg(balance));
		});
		return marginBalanceDetailBuilder.build();
	}
	
	public static MatchResultMsg buildMatchResultMsg(MatchResult matchResult) {
		MatchResultMsg.Builder matchResultBuilder = MatchResultMsg.newBuilder();
		matchResultBuilder.setCreatedTimestamp(matchResult.getCreatedTimestamp());
		matchResultBuilder.setFilledAmount(matchResult.getFilledAmount().toString());
		matchResultBuilder.setFilledFees(matchResult.getFilledFees().toString());
		matchResultBuilder.setId(matchResult.getId());
		matchResultBuilder.setMatchId(matchResult.getMatchId());
		matchResultBuilder.setOrderId(matchResult.getOrderId());
		matchResultBuilder.setPrice(matchResult.getPrice().toString());
		matchResultBuilder.setSource(matchResult.getSource().toString());
		matchResultBuilder.setSymbol(matchResult.getSymbol());
		matchResultBuilder.setType(matchResult.getType().toString());
		return matchResultBuilder.build();
	}
	
	public static OrderMsg buildOrdermsg(Order order) {
		OrderMsg.Builder orderBuilder = OrderMsg.newBuilder();
		orderBuilder.setAccountType(order.getAccountType().name());
		orderBuilder.setAmount(order.getAmount().toString());
		orderBuilder.setPrice(order.getPrice().toString());
		orderBuilder.setCreatedTimestamp(order.getCreatedTimestamp());
		orderBuilder.setCanceledTimestamp(order.getCanceledTimestamp());
		orderBuilder.setFinishedTimestamp(order.getFinishedTimestamp());
		orderBuilder.setOrderId(order.getOrderId());
		orderBuilder.setSymbol(order.getSymbol());
		if(order.getType() != null) {
			orderBuilder.setType(order.getType().name());
		}
		orderBuilder.setFilledAmount(order.getFilledAmount().toString());
		orderBuilder.setFilledCashAmount(order.getFilledCashAmount().toString());
		orderBuilder.setFilledFees(order.getFilledFees().toString());
		if(order.getSource() != null) {
			orderBuilder.setSource(order.getSource().name());
		}
		if(order.getState() != null) {
			orderBuilder.setState(order.getState().name());
		}
		return orderBuilder.build();
	}
	
	public static PriceDepthMsg buildPriceDepthMsg(PriceDepth priceDepth) {
		PriceDepthMsg.Builder priceDepthBuilder = PriceDepthMsg.newBuilder();
		priceDepthBuilder.setTimestamp(priceDepth.getTimestamp());
		priceDepth.getAsks().forEach(depthEntry -> {
			priceDepthBuilder.addAsks(buildDepthEntryMsg(depthEntry));
		});
		priceDepth.getBids().forEach(depthEntry -> {
			priceDepthBuilder.addBids(buildDepthEntryMsg(depthEntry));
		});
		return priceDepthBuilder.build();
	}
	
	public static SymbolMsg buildSymbolMsg(Symbol symbol) {
		SymbolMsg.Builder symbolBuilder = SymbolMsg.newBuilder();
		symbolBuilder.setBaseCurrency(symbol.getBaseCurrency());
		symbolBuilder.setQuoteCurrency(symbol.getQuoteCurrency());
		symbolBuilder.setPricePrecision(symbol.getPricePrecision());
		symbolBuilder.setAmountPrecision(symbol.getAmountPrecision());
		symbolBuilder.setSymbolPartition(symbol.getSymbolPartition());
		symbolBuilder.setSymbol(symbol.getSymbol());
		return symbolBuilder.build();
	}

	public static TradeMsg buildTradeMsg(Trade trade) {
		TradeMsg.Builder tradeBuilder = TradeMsg.newBuilder();
		tradeBuilder.setTradeId(trade.getTradeId());
		tradeBuilder.setTimestamp(trade.getTimestamp());
		tradeBuilder.setPrice(trade.getPrice().toString());
		tradeBuilder.setAmount(trade.getAmount().toString());
		tradeBuilder.setDirection(trade.getDirection().toString());
		return tradeBuilder.build();
	}
	
	public static TradeStatisticsMsg buildTradeStatisticsMsg(TradeStatistics tradeStatistics) {
		TradeStatisticsMsg.Builder tradeStatisticsBuilder = TradeStatisticsMsg.newBuilder();
		tradeStatisticsBuilder.setTimestamp(tradeStatistics.getTimestamp());
		tradeStatisticsBuilder.setOpen(tradeStatistics.getOpen().toString());
		tradeStatisticsBuilder.setClose(tradeStatistics.getClose().toString());
		tradeStatisticsBuilder.setAmount(tradeStatistics.getAmount().toString());
		tradeStatisticsBuilder.setHigh(tradeStatistics.getHigh().toString());
		tradeStatisticsBuilder.setLow(tradeStatistics.getLow().toString());
		tradeStatisticsBuilder.setCount(tradeStatistics.getCount());
		tradeStatisticsBuilder.setVolume(tradeStatistics.getVolume().toString());
		return tradeStatisticsBuilder.build();
	}
	
	public static UnitPriceMsg buildUnitPriceMsg(UnitPrice unitPrice) {
		UnitPriceMsg.Builder unitPriceBuilder = UnitPriceMsg.newBuilder();
		unitPriceBuilder.setCurrency(unitPrice.getCurrency());
		unitPriceBuilder.setAmount(unitPrice.getAmount().toString());
		return unitPriceBuilder.build();
	}
	
	public static AccountEventMsg buildAccountChangeMsg(AccountEvent accountEvent) {
		AccountEventMsg.Builder accountEventBuilder = AccountEventMsg.newBuilder();
		accountEventBuilder.setTimestamp(accountEvent.getTimestamp());
		accountEventBuilder.setChangeType(accountEvent.getChangeType().name());
		accountEvent.getData().forEach(accountChange -> {
			accountEventBuilder.addAccountChangeList(buildAccountChangeMsg(accountChange));
		});
		return accountEventBuilder.build();
	}
	
	public static CandlestickEventMsg buildCandlestickEventMsg(CandlestickEvent candlestickEvent) {
		CandlestickEventMsg.Builder candlestickEventMsg = CandlestickEventMsg.newBuilder();
		candlestickEventMsg.setSymbol(candlestickEvent.getSymbol());
		candlestickEventMsg.setTimestamp(candlestickEvent.getTimestamp());
		candlestickEventMsg.setData(buildCandlestickMsg(candlestickEvent.getData()));
		return candlestickEventMsg.build();
	}
	
	public static OrderUpdateEventMsg buildOrderUpdateEventMsg(OrderUpdateEvent orderUpdateEvent) {
		OrderUpdateEventMsg.Builder orderUpdateEventMsg = OrderUpdateEventMsg.newBuilder();
		orderUpdateEventMsg.setSymbol(orderUpdateEvent.getSymbol());
		orderUpdateEventMsg.setTimestamp(orderUpdateEvent.getTimestamp());
		orderUpdateEventMsg.setData(buildOrdermsg(orderUpdateEvent.getData()));
		return orderUpdateEventMsg.build();
	}
	
	public static PriceDepthEventMsg buildPriceDepthEventMsg(PriceDepthEvent priceDepthEvent) {
		PriceDepthEventMsg.Builder priceDepthEventMsg = PriceDepthEventMsg.newBuilder();
		priceDepthEventMsg.setSymbol(priceDepthEvent.getSymbol());
		priceDepthEventMsg.setTimestamp(priceDepthEvent.getTimestamp());
		priceDepthEventMsg.setData(buildPriceDepthMsg(priceDepthEvent.getData()));
		return priceDepthEventMsg.build();
	}
	
	public static TradeEventMsg buildTradeEventMsg(TradeEvent tradeEvent) {
		TradeEventMsg.Builder tradeEventMsg = TradeEventMsg.newBuilder();
		tradeEventMsg.setSymbol(tradeEvent.getSymbol());
		tradeEventMsg.setTimestamp(tradeEvent.getTimestamp());
		tradeEvent.getTradeList().forEach(trade -> {
			tradeEventMsg.addTradeList(buildTradeMsg(trade));
		});
		return tradeEventMsg.build();
	}
	
	public static TradeStatisticsEventMsg buildTradeStatisticsEventMsg(TradeStatisticsEvent tradeStatisticsEvent) {
		TradeStatisticsEventMsg.Builder tradeStatisticsEventMsg = TradeStatisticsEventMsg.newBuilder();
		tradeStatisticsEventMsg.setSymbol(tradeStatisticsEvent.getSymbol());
		tradeStatisticsEventMsg.setTimeStamp(tradeStatisticsEvent.getTimeStamp());
		tradeStatisticsEventMsg.setTradeStatistics(buildTradeStatisticsMsg(tradeStatisticsEvent.getData()));
		return tradeStatisticsEventMsg.build();
	}
}

package com.gene.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.gene.proto.BeanProto.AccountChangeMsg;
import com.gene.proto.BeanProto.AccountEventMsg;
import com.gene.proto.BeanProto.AccountMsg;
import com.gene.proto.BeanProto.BalanceMsg;
import com.gene.proto.BeanProto.BatchCancelResultMsg;
import com.gene.proto.BeanProto.BestQuoteMsg;
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
import com.gene.proto.BeanProto.PriceDepthMsg;
import com.gene.proto.BeanProto.SymbolMsg;
import com.gene.proto.BeanProto.TradeMsg;
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
import com.huobi.client.model.enums.AccountChangeType;
import com.huobi.client.model.enums.AccountState;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceType;
import com.huobi.client.model.enums.DepositState;
import com.huobi.client.model.enums.EtfStatus;
import com.huobi.client.model.enums.EtfSwapType;
import com.huobi.client.model.enums.LoanOrderStates;
import com.huobi.client.model.enums.OrderSource;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.enums.TradeDirection;
import com.huobi.client.model.event.AccountEvent;

public class HbBeanBuilder {
	
	public static Account parseAccount(AccountMsg accountMsg) {
		Account account = new Account();
		account.setId(accountMsg.getId());
		account.setType(AccountType.valueOf(accountMsg.getType()));
		account.setState(AccountState.valueOf(accountMsg.getState()));
		List<Balance> balances = new ArrayList<>();
		accountMsg.getBalancesList().forEach(balanceMsg -> {
			balances.add(parseBalance(balanceMsg));
		});
		account.setBalances(balances);
		return account;
	}
	
	public static AccountChange parseAccountChange(AccountChangeMsg accountChangeMsg) {
		AccountChange accountChange = new AccountChange();
		accountChange.setCurrency(accountChangeMsg.getCurrency());
		accountChange.setAccountType(AccountType.valueOf(accountChangeMsg.getAccountType()));
		accountChange.setBalance(new BigDecimal(accountChangeMsg.getBalance()));
		accountChange.setBalanceType(BalanceType.valueOf(accountChangeMsg.getBalanceType()));
		return accountChange;
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
	
	public static BatchCancelResult parseBatchCancelResult(BatchCancelResultMsg batchCancelResultMsg) {
		BatchCancelResult batchCancelResult = new BatchCancelResult();
		batchCancelResult.setSuccessCount(batchCancelResultMsg.getSuccessCount());
		batchCancelResult.setFailedCount(batchCancelResultMsg.getFailedCount());
		return batchCancelResult;
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
	
	public static Candlestick parseCandlestick(CandlestickMsg candlestickMsg) {
		Candlestick candlestick = new Candlestick();
		candlestick.setTimestamp(candlestickMsg.getTimestamp());
		candlestick.setAmount(new BigDecimal(candlestickMsg.getAmount()));
		candlestick.setCount(candlestickMsg.getCount());
		candlestick.setOpen(new BigDecimal(candlestickMsg.getOpen()));
		candlestick.setClose(new BigDecimal(candlestickMsg.getClose()));
		candlestick.setLow(new BigDecimal(candlestickMsg.getLow()));
		candlestick.setHigh(new BigDecimal(candlestickMsg.getHigh()));
		candlestick.setVolume(new BigDecimal(candlestickMsg.getVolume()));
		return candlestick;
	}
	
	public static CompleteSubAccountInfo parseCompleteSubAccountInfo(CompleteSubAccountInfoMsg completeSubAccountInfoMsg) {
		CompleteSubAccountInfo completeSubAccountInfo = new CompleteSubAccountInfo();
		completeSubAccountInfo.setId(completeSubAccountInfoMsg.getId());
		completeSubAccountInfo.setType(AccountType.valueOf(completeSubAccountInfoMsg.getType()));
		List<Balance> balances = new ArrayList<>();
		completeSubAccountInfoMsg.getBalancesList().forEach(balanceMsg -> {
			balances.add(parseBalance(balanceMsg));
		});
		completeSubAccountInfo.setBalances(balances);
		return completeSubAccountInfo;
	}
	
	public static Deposit parseDeposit(DepositMsg depositMsg) {
		Deposit deposit = new Deposit();
		deposit.setId(depositMsg.getId());
		deposit.setCurrency(depositMsg.getCurrency());
		deposit.setTxHash(depositMsg.getTxHash());
		deposit.setAmount(new BigDecimal(depositMsg.getAmount()));
		deposit.setAddress(depositMsg.getAddress());
		deposit.setAddressTag(depositMsg.getAddressTag());
		deposit.setFee(new BigDecimal(depositMsg.getFee()));
		deposit.setCreatedTimestamp(depositMsg.getCreatedTimestamp());
		deposit.setUpdatedTimestamp(depositMsg.getUpdatedTimestamp());
		deposit.setDepositState(DepositState.valueOf(depositMsg.getDepositState()));
		return deposit;
	}

	
	public static DepthEntry parseDepthEntry(DepthEntryMsg depthEntryMsg) {
		DepthEntry depthEntry = new DepthEntry();
		depthEntry.setPrice(new BigDecimal(depthEntryMsg.getPrice()));
		depthEntry.setAmount(new BigDecimal(depthEntryMsg.getAmount()));
		return depthEntry;
	}
	
	public static EtfSwapConfig parseEtfSwapConfig(EtfSwapConfigMsg etfSwapConfigMsg) {
		EtfSwapConfig etfSwapConfig = new EtfSwapConfig();
		etfSwapConfig.setPurchaseMinAmount(etfSwapConfigMsg.getPurchaseMinAmount());
		etfSwapConfig.setPurchaseMaxAmount(etfSwapConfigMsg.getPurchaseMaxAmount());
		etfSwapConfig.setRedemptionMinAmount(etfSwapConfigMsg.getRedemptionMinAmount());
		etfSwapConfig.setRedemptionMaxAmount(etfSwapConfigMsg.getRedemptionMaxAmount());
		etfSwapConfig.setPurchaseFeeRate(new BigDecimal(etfSwapConfigMsg.getPurchaseFeeRate()));
		etfSwapConfig.setRedemptionFeeRate(new BigDecimal(etfSwapConfigMsg.getRedemptionFeeRate()));
		etfSwapConfig.setStatus(EtfStatus.valueOf(etfSwapConfigMsg.getStatus()));
		List<UnitPrice> unitPrices = new ArrayList<>();
		etfSwapConfigMsg.getUnitPriceListList().forEach(unitPriceMsg -> {
			unitPrices.add(parseUnitPrice(unitPriceMsg));
		});
		etfSwapConfig.setUnitPriceList(unitPrices);
		return etfSwapConfig;
	}
	
	public static EtfSwapHistory parseEtfSwapHistory(EtfSwapHistoryMsg etfSwapHistoryMsg) {
		EtfSwapHistory etfSwapHistory = new EtfSwapHistory();
		etfSwapHistory.setCreatedTimestamp(etfSwapHistoryMsg.getCreatedTimestamp());
		etfSwapHistory.setCurrency(etfSwapHistoryMsg.getCurrency());
		etfSwapHistory.setAmount(new BigDecimal(etfSwapHistoryMsg.getAmount()));
		etfSwapHistory.setType(EtfSwapType.valueOf(etfSwapHistoryMsg.getType()));
		etfSwapHistory.setStatus(etfSwapHistoryMsg.getStatus());
		etfSwapHistory.setRate(new BigDecimal(etfSwapHistoryMsg.getRate()));
		etfSwapHistory.setFee(new BigDecimal(etfSwapHistoryMsg.getFee()));
		etfSwapHistory.setPointCardAmount(new BigDecimal(etfSwapHistoryMsg.getPointCardAmount()));
		List<UnitPrice> usedCurrencyList = new ArrayList<>();
		etfSwapHistoryMsg.getUsedCurrencyListList().forEach(unitPriceMsg -> {
			usedCurrencyList.add(parseUnitPrice(unitPriceMsg));
		});
		etfSwapHistory.setUsedCurrencyList(usedCurrencyList);
		List<UnitPrice> obtainCurrencys = new ArrayList<>();
		etfSwapHistoryMsg.getObtainCurrencyListList().forEach(unitPriceMsg -> {
			obtainCurrencys.add(parseUnitPrice(unitPriceMsg));
		});
		etfSwapHistory.setObtainCurrencyList(obtainCurrencys);
		return etfSwapHistory;
	}

	public static ExchangeInfo parseExchangeInfo(ExchangeInfoMsg exchangeInfoMsg) {
		ExchangeInfo exchangeInfo = new ExchangeInfo();
		List<Symbol> symbols = new ArrayList<>();
		exchangeInfoMsg.getSymbolListList().forEach(symbolMsg -> {
			symbols.add(parseSymbol(symbolMsg));
		});
		exchangeInfo.setSymbolList(symbols);
		List<String> strings = new ArrayList<>();
		exchangeInfoMsg.getCurrenciesList().forEach(stringMsg -> {
			strings.add(stringMsg);
		});
		exchangeInfo.setCurrencies(strings);
		return exchangeInfo;
	}

	public static LastTradeAndBestQuote parseLastTradeAndBestQuote(LastTradeAndBestQuoteMsg lastTradeAndBestQuoteMsg) {
		LastTradeAndBestQuote lastTradeAndBestQuote = new LastTradeAndBestQuote();
		lastTradeAndBestQuote.setLastTradePrice(new BigDecimal(lastTradeAndBestQuoteMsg.getLastTradePrice()));
		lastTradeAndBestQuote.setLastTradeAmount(new BigDecimal(lastTradeAndBestQuoteMsg.getLastTradeAmount()));
		lastTradeAndBestQuote.setAskPrice(new BigDecimal(lastTradeAndBestQuoteMsg.getAskPrice()));
		lastTradeAndBestQuote.setAskAmount(new BigDecimal(lastTradeAndBestQuoteMsg.getAskAmount()));
		lastTradeAndBestQuote.setBidPrice(new BigDecimal(lastTradeAndBestQuoteMsg.getBidPrice()));
		lastTradeAndBestQuote.setBidAmount(new BigDecimal(lastTradeAndBestQuoteMsg.getBidAmount()));
		return lastTradeAndBestQuote;
	}

	public static Loan parseLoan(LoanMsg loanMsg) {
		Loan loan = new Loan();
		loan.setId(loanMsg.getId());
		loan.setUserId(loanMsg.getUserId());
		loan.setAccountType(AccountType.valueOf(loanMsg.getAccountType()));
		loan.setSymbol(loanMsg.getSymbol());
		loan.setCurrency(loanMsg.getCurrency());
		loan.setLoanAmount(new BigDecimal(loanMsg.getLoanAmount()));
		loan.setLoanBalance(new BigDecimal(loanMsg.getLoanBalance()));
		loan.setInterestRate(new BigDecimal(loanMsg.getInterestRate()));
		loan.setInterestAmount(new BigDecimal(loanMsg.getInterestAmount()));
		loan.setInterestBalance(new BigDecimal(loanMsg.getInterestBalance()));
		loan.setState(LoanOrderStates.valueOf(loanMsg.getState()));
		loan.setCreatedTimestamp(loanMsg.getCreatedTimestamp());
		loan.setAccruedTimestamp(loanMsg.getAccruedTimestamp());
		return loan;
	}

	public static MarginBalanceDetail parseMarginBalanceDetail(MarginBalanceDetailMsg marginBalanceDetailMsg) {
		MarginBalanceDetail marginBalanceDetail = new MarginBalanceDetail();
		marginBalanceDetail.setId(marginBalanceDetailMsg.getId());
		marginBalanceDetail.setSymbol(marginBalanceDetailMsg.getSymbol());
		marginBalanceDetail.setState(AccountState.valueOf(marginBalanceDetailMsg.getState()));
		marginBalanceDetail.setType(AccountType.valueOf(marginBalanceDetailMsg.getType()));
		marginBalanceDetail.setRiskRate(new BigDecimal(marginBalanceDetailMsg.getRiskRate()));
		marginBalanceDetail.setFlPrice(new BigDecimal(marginBalanceDetailMsg.getFlPrice()));
		marginBalanceDetail.setFlType(marginBalanceDetailMsg.getFlType());
		List<Balance> balances = new ArrayList<>();
		marginBalanceDetailMsg.getSubAccountBalanceList().forEach(balanceMsg -> {
			balances.add(parseBalance(balanceMsg));
		});
		marginBalanceDetail.setSubAccountBalance(balances);
		return marginBalanceDetail;
	}

	
	public static MatchResult parseMatchResult(MatchResultMsg matchResultMsg) {
		MatchResult matchResult = new MatchResult();
		matchResult.setCreatedTimestamp(matchResultMsg.getCreatedTimestamp());
		matchResult.setFilledAmount(new BigDecimal(matchResultMsg.getFilledAmount()));
		matchResult.setFilledFees(new BigDecimal(matchResultMsg.getFilledFees()));
		matchResult.setId(matchResultMsg.getId());
		matchResult.setMatchId(matchResultMsg.getMatchId());
		matchResult.setOrderId(matchResultMsg.getOrderId());
		matchResult.setPrice(new BigDecimal(matchResultMsg.getPrice()));
		matchResult.setSource(OrderSource.valueOf(matchResultMsg.getSource()));
		matchResult.setSymbol(matchResultMsg.getSymbol());
		matchResult.setType(OrderType.valueOf(matchResultMsg.getType()));
		return matchResult;
	}

	
	public static Order parseOrder(OrderMsg orderMsg) {
		Order order = new Order();
		order.setAccountType(AccountType.valueOf(orderMsg.getAccountType()));
		order.setAmount(new BigDecimal(orderMsg.getAmount()));
		order.setPrice(new BigDecimal(orderMsg.getPrice()));
		order.setCreatedTimestamp(orderMsg.getCreatedTimestamp());
		order.setCanceledTimestamp(orderMsg.getCanceledTimestamp());
		order.setFinishedTimestamp(orderMsg.getFinishedTimestamp());
		order.setOrderId(orderMsg.getOrderId());
		order.setSymbol(orderMsg.getSymbol());
		order.setType(OrderType.valueOf(orderMsg.getType()));
		order.setFilledAmount(new BigDecimal(orderMsg.getFilledAmount()));
		order.setFilledCashAmount(new BigDecimal(orderMsg.getFilledCashAmount()));
		order.setFilledFees(new BigDecimal(orderMsg.getFilledFees()));
		order.setSource(OrderSource.valueOf(orderMsg.getSource()));
		order.setState(OrderState.valueOf(orderMsg.getState()));
		return order;
	}
	
	public static PriceDepth parsePriceDepth(PriceDepthMsg priceDepthMsg) {
		PriceDepth priceDepth = new PriceDepth();
		priceDepth.setTimestamp(priceDepthMsg.getTimestamp());
		List<DepthEntry> bids = new ArrayList<>();
		priceDepthMsg.getBidsList().forEach(depthEntryMsg -> {
			bids.add(parseDepthEntry(depthEntryMsg));
		});
		priceDepth.setBids(bids);
		List<DepthEntry> asks = new ArrayList<>();
		priceDepthMsg.getAsksList().forEach(depthEntryMsg -> {
			asks.add(parseDepthEntry(depthEntryMsg));
		});
		priceDepth.setAsks(asks);
		return priceDepth;
	}

	public static Symbol parseSymbol(SymbolMsg symbolMsg) {
		Symbol symbol = new Symbol();
		symbol.setBaseCurrency(symbolMsg.getBaseCurrency());
		symbol.setQuoteCurrency(symbolMsg.getQuoteCurrency());
		symbol.setPricePrecision(symbolMsg.getPricePrecision());
		symbol.setAmountPrecision(symbolMsg.getAmountPrecision());
		symbol.setSymbolPartition(symbolMsg.getSymbolPartition());
		symbol.setSymbol(symbolMsg.getSymbol());
		return symbol;
	}

	public static Trade parseTrade(TradeMsg tradeMsg) {
		Trade trade = new Trade();
		trade.setTradeId(tradeMsg.getTradeId());
		trade.setTimestamp(tradeMsg.getTimestamp());
		trade.setPrice(new BigDecimal(tradeMsg.getPrice()));
		trade.setAmount(new BigDecimal(tradeMsg.getAmount()));
		trade.setDirection(TradeDirection.valueOf(tradeMsg.getDirection()));
		return trade;
	}

	public static TradeStatistics parseTradeStatistics(TradeStatisticsMsg tradeStatisticsMsg) {
		TradeStatistics tradeStatistics = new TradeStatistics();
		tradeStatistics.setTimestamp(tradeStatisticsMsg.getTimestamp());
		tradeStatistics.setOpen(new BigDecimal(tradeStatisticsMsg.getOpen()));
		tradeStatistics.setClose(new BigDecimal(tradeStatisticsMsg.getClose()));
		tradeStatistics.setAmount(new BigDecimal(tradeStatisticsMsg.getAmount()));
		tradeStatistics.setHigh(new BigDecimal(tradeStatisticsMsg.getHigh()));
		tradeStatistics.setLow(new BigDecimal(tradeStatisticsMsg.getLow()));
		tradeStatistics.setCount(tradeStatisticsMsg.getCount());
		tradeStatistics.setVolume(new BigDecimal(tradeStatisticsMsg.getVolume()));
		return tradeStatistics;
	}

	public static UnitPrice parseUnitPrice(UnitPriceMsg unitPriceMsg) {
		UnitPrice unitPrice = new UnitPrice();
		unitPrice.setCurrency(unitPriceMsg.getCurrency());
		unitPrice.setAmount(new BigDecimal(unitPriceMsg.getAmount()));
		return unitPrice;
	}
	
}

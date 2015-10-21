package com.acme.services.businesslogic;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acme.domain.Stock;
import com.acme.domain.Trade;
import com.acme.services.persistence.PersistenceException;
import com.acme.services.persistence.StocksPersistenceService;
import com.acme.services.persistence.TradesPersistenceService;
import com.acme.util.StringUtils;

/**
 * Default implementation of the stock market business logic service.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class StockMarketServiceImpl implements StockMarketService {
	private static final Logger logger = LogManager.getLogger(StockMarketServiceImpl.class);

	private StocksPersistenceService stocksPersistenceService;
	private TradesPersistenceService tradesPersistenceService;

	/**
	 * Calculates the dividend yield for a given stock and price.
	 * 
	 * @param stockSymbol
	 *            the stock symbol identifying the stock
	 * @param price
	 *            the price
	 * @return the computed dividend yield value
	 * @throws BusinesslogicException
	 */
	public double calculateDividentYield(String stockSymbol, double price) throws BusinesslogicException {
		if (price <= 0.0d) {
			throw new BusinesslogicException("Cannot compute divident yield for price [" + price + "]");
		}

		Stock stock = readStock(stockSymbol);

		double dividentYield = 0.0;

		switch (stock.getType()) {
			case COMMON:
				dividentYield = stock.getLastDividend() / price;
				break;
			case PREFERRED:
				dividentYield = stock.getFixedDividend() * stock.getParValue() / price;
				break;
		}

		return dividentYield;
	}

	/**
	 * Calculates the P/E ratio for a given stock and price.
	 * 
	 * @param stockSymbol
	 *            the stock symbol identifying the stock
	 * @param price
	 *            the price
	 * @return the computed P/E ratio value
	 * @throws BusinesslogicException
	 */
	public double calculatePERatio(String stockSymbol, double price) throws BusinesslogicException {
		Stock stock = readStock(stockSymbol);

		double peRatio = 0.0;

		try {
			peRatio = price / stock.getLastDividend();
		} catch (Exception e) {
			logger.error("Caught divide by 0 due to stock's divident value");
			throw new BusinesslogicException(e);
		}

		return peRatio;
	}

	/**
	 * Calculates the Volume Weighted Stock Price for the trades of a given stock and with a
	 * specified age in minutes.
	 * 
	 * @param stockSymbol
	 *            the stock symbol identifying the stock
	 * @param ageInMinutes
	 *            the trades age in minutes
	 * @return the computed volume weighted stock price
	 * @throws BusinesslogicException
	 */
	public double calculateVolumeWeightedStockPrice(String stockSymbol, int maxAgeInMinutes) throws BusinesslogicException {
		if (!StringUtils.isValid(stockSymbol)) {
			throw new BusinesslogicException("Invalid stock symbol: [" + stockSymbol + "]");
		}

		Collection<Trade> agedTrades = new LinkedList<Trade>();
		try {
			agedTrades = tradesPersistenceService.listAgedTrades(stockSymbol, maxAgeInMinutes);
		} catch (PersistenceException e) {
			logger.error("Exception occurred while listing stock aged trades", e);
			throw new BusinesslogicException(e);
		}

		if (agedTrades == null || agedTrades.size() == 0) {
			String message = "No matching aged trades have been found for stock [" + stockSymbol + "], age in minutes: [" + maxAgeInMinutes + "]";
			logger.warn(message);
			throw new BusinesslogicException(message);
		}

		double numerator = 0.0;
		double denominator = 0.0;

		for (Trade trade : agedTrades) {
			numerator += trade.getPrice() * trade.getQuantity();
			denominator += trade.getQuantity();
		}

		double volumeWeightedStockPrice = numerator / denominator;

		return volumeWeightedStockPrice;
	}

	/**
	 * Reads a stock definition from the persistent storage.
	 * 
	 * @param stockSymbol
	 *            the stock symbol
	 * @return the stock
	 * @throws BusinesslogicException
	 *             if an error occurs or the stock cannot be found in the datastore.
	 */
	private Stock readStock(String stockSymbol) throws BusinesslogicException {
		if (!StringUtils.isValid(stockSymbol)) {
			throw new BusinesslogicException("Invalid stock symbol: [" + stockSymbol + "]");
		}

		Stock stock = null;

		try {
			stock = stocksPersistenceService.read(stockSymbol);
		} catch (PersistenceException e) {
			logger.error("Exception occurred while reading stock", e);
			throw new BusinesslogicException(e);
		}

		if (stock == null) {
			throw new BusinesslogicException("Cannot match stock for symbol: [" + stockSymbol + "]");
		}

		return stock;
	}

	/**
	 * Calculates the GBCE All Share Index using the geometric mean of prices for all stocks.
	 * 
	 * @return the computed GBCE All Share Index
	 * @throws BusinesslogicException
	 */
	public double calculateGBCEAllShareIndex() throws BusinesslogicException {
		Collection<Trade> trades = new LinkedList<Trade>();
		try {
			trades = tradesPersistenceService.listAllTrades();
		} catch (PersistenceException e) {
			logger.error("Exception occurred while reading trades information.", e);
			throw new BusinesslogicException(e);
		}

		if (trades == null || trades.size() == 0) {
			logger.warn("Unable to find any trades information.");
			throw new BusinesslogicException("Unable to find any trades information.");
		}

		double product = 1.0;
		for (Trade trade : trades) {
			product = product * trade.getPrice();
		}

		double mean = Math.pow(product, 1.0 / trades.size());

		return mean;
	}

	/**
	 * @param stocksPersistenceService
	 *            the stocksPersistenceService to set
	 */
	public void setStocksPersistenceService(StocksPersistenceService stocksPersistenceService) {
		this.stocksPersistenceService = stocksPersistenceService;
	}

	/**
	 * @param tradesPersistenceService
	 *            the tradesPersistenceService to set
	 */
	public void setTradesPersistenceService(TradesPersistenceService tradesPersistenceService) {
		this.tradesPersistenceService = tradesPersistenceService;
	}
}

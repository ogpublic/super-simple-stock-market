package com.acme.services.businesslogic;

import java.util.Collection;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acme.domain.Stock;
import com.acme.domain.Trade;
import com.acme.services.persistence.PersistenceException;
import com.acme.services.persistence.StocksPersistenceService;
import com.acme.services.persistence.TradesPersistenceService;
import com.acme.util.StringUtils;

/**
 * Default implementation of the trades businesslogic service.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class TradesServiceImpl implements TradesService {
	private static final Logger logger = LogManager.getLogger(TradesServiceImpl.class);

	/**
	 * A reference to the stocks persistence service used by this implementation.
	 */
	private StocksPersistenceService stocksPersistenceService;

	/**
	 * A reference to the trades persistence service used by this implementation.
	 */
	private TradesPersistenceService tradesPersistenceService;

	/**
	 * Creates a new trade into the persistent storage.
	 * 
	 * @param trade
	 *            the new trade to be created
	 * @throws BusinesslogicException
	 *             if an internal error occurrs or if the passed trade object fails the
	 *             businesslogic validation.
	 */
	public void createTrade(Trade trade) throws BusinesslogicException {
		// perform simple businesslogic validation
		if (trade == null) {
			throw new BusinesslogicException("Received NULL trade information, cannot create trade.");
		}

		if (!StringUtils.isValid(trade.getStockSymbol())) {
			throw new BusinesslogicException("Trade does not contain referenced stock information, cannot create trade.");
		}

		if (trade.getPrice() <= 0) {
			throw new BusinesslogicException("Invalid price specified for a trade: [" + trade.getPrice() + "], cannot create trade.");
		}

		if (trade.getQuantity() <= 0) {
			throw new BusinesslogicException("Invalid quantity specified for a trade: [" + trade.getQuantity() + "], cannot create trade.");
		}

		Stock stock = null;
		try {
			stock = stocksPersistenceService.read(trade.getStockSymbol());
		} catch (PersistenceException e) {
			logger.error("Exception occurred while reading stock information from the datastore.", e);
			throw new BusinesslogicException(e);
		}

		if (stock == null) {
			throw new BusinesslogicException("Stock symbol [" + trade.getStockSymbol() + "] does not reffer to an existing stock.");
		}

		switch (trade.getType()) {
			case BUY:
				stock.setSharesCount(stock.getSharesCount() + trade.getQuantity());
				break;
			case SELL:
				if (trade.getQuantity() > stock.getSharesCount()) {
					throw new BusinesslogicException("Trade quantity exceeds the number of availabsle shares in stock.");
				}

				stock.setSharesCount(stock.getSharesCount() - trade.getQuantity());
				break;
		}

		try {
			stocksPersistenceService.update(stock);
		} catch (PersistenceException e) {
			logger.error("Exception occurred while updating stock information to the datastore.", e);
			throw new BusinesslogicException(e);
		}

		// always record the timestamp internally, do not take into account the passed in value
		trade.setTimestamp(new Date());

		try {
			tradesPersistenceService.save(trade);
		} catch (PersistenceException e) {
			logger.error("Exception occurred while saving trade information to the datastore.", e);
			throw new BusinesslogicException(e);
		}
	}

	/**
	 * Returns a collection of all trades defined into the datastore.
	 * 
	 * @return the collection of existing trades.
	 * @throws BusinesslogicException
	 */
	public Collection<Trade> listAllTrades() throws BusinesslogicException {
		try {
			return tradesPersistenceService.listAllTrades();
		} catch (PersistenceException e) {
			logger.error("Exception caught while listing trades.", e);
			throw new BusinesslogicException(e);
		}
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

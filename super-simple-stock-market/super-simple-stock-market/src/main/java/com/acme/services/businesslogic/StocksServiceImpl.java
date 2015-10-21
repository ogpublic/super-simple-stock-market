package com.acme.services.businesslogic;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acme.domain.Stock;
import com.acme.services.persistence.PersistenceException;
import com.acme.services.persistence.StocksPersistenceService;
import com.acme.util.StringUtils;

/**
 * Default implementation of the stocks businesslogic service.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class StocksServiceImpl implements StocksService {
	private static final Logger logger = LogManager.getLogger(StocksServiceImpl.class);

	/**
	 * The stocks persistence service used by this businesslogic service implementation.
	 */
	private StocksPersistenceService stocksPersistenceService;

	/**
	 * Creates a new stock in the datastore.
	 * 
	 * @param stock
	 *            the new stock to be created
	 * @throws BusinesslogicException
	 *             if an internal error occurs or if the stock fails the businesslogic validation.
	 */
	public void createStock(Stock stock) throws BusinesslogicException {
		// perform simple businesslogic validation
		if (stock == null) {
			throw new BusinesslogicException("Received NULL stock, cannot persist to datastore.");
		}

		if (!StringUtils.isValid(stock.getSymbol())) {
			throw new BusinesslogicException("No stock symbol information, cannot persist to datastore.");
		}

		if (stock.getSharesCount() <= 0) {
			throw new BusinesslogicException("No stock shares information, cannot persist to datastore.");
		}

		try {
			stocksPersistenceService.save(stock);
		} catch (PersistenceException e) {
			logger.error("Error occured while saving stock information to datastore.", e);
			throw new BusinesslogicException(e);
		}
	}

	/**
	 * Reads a stock definition from the datastore
	 * 
	 * @param stockSymbol
	 *            the stock symbol to read
	 * @return the stock or null if not found
	 * @throws BusinesslogicException
	 */
	public Stock getStock(String stockSymbol) throws BusinesslogicException {
		if (!StringUtils.isValid(stockSymbol)) {
			throw new BusinesslogicException("Received invalid stock symbol, cannot read from datastore.");
		}

		try {
			return stocksPersistenceService.read(stockSymbol);
		} catch (PersistenceException e) {
			logger.error("Error occured while reading stock from datastore.", e);
			throw new BusinesslogicException(e);
		}
	}

	/**
	 * Lists all stocks defined in the datastore.
	 * 
	 * @return the collection of all defined stocks in the persistent storage.
	 * @throws BusinesslogicException
	 */
	public Collection<Stock> listAllStocks() throws BusinesslogicException {
		try {
			return stocksPersistenceService.listAll();
		} catch (PersistenceException e) {
			logger.error("Error occured while reading stocks from datastore.", e);
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
}

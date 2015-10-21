package com.acme.services.persistence;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acme.domain.Stock;
import com.acme.storage.DatastoreException;
import com.acme.storage.StocksDatastore;
import com.acme.storage.filter.StockFilter;

/**
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class StocksPersistenceServiceImpl implements StocksPersistenceService {
	private static final Logger logger = LogManager.getLogger(StocksPersistenceServiceImpl.class);

	/**
	 * A reference to the stocks persistence storage implementation.
	 */
	private StocksDatastore stocksDatastore;

	public StocksPersistenceServiceImpl() {
	}

	/**
	 * Saves a new stock into the datastore.
	 * 
	 * @param stock
	 *            the stock to be persisted
	 * @throws PersistenceException
	 */
	public void save(Stock stock) throws PersistenceException {
		try {
			stocksDatastore.store(stock);
		} catch (DatastoreException e) {
			logger.error("Exception occurred while persisting stock.", e);
			throw new PersistenceException(e);
		}
	}

	/**
	 * Update a current existing stock in the datastore with the information on the passed in stock.
	 * 
	 * @param stock
	 *            the stock to be updated in the datastore
	 * @return true if the update operation has succedded or false otherwise
	 * @throws PersistenceException
	 */
	public boolean update(Stock stock) throws PersistenceException {
		try {
			return stocksDatastore.update(stock);
		} catch (DatastoreException e) {
			logger.error("Exception occurred while updating stock.", e);
			throw new PersistenceException(e);
		}
	}

	/**
	 * Reads an existing stock from the datastore based on the passed in stock symbol.
	 * 
	 * @param stockSymbol
	 * @return the stock or null if not found
	 * @throws PersistenceException
	 */
	public Stock read(String stockSymbol) throws PersistenceException {
		StockFilter stockFilter = new StockFilter(stockSymbol);

		Collection<Stock> matches = new LinkedList<Stock>();
		try {
			matches = stocksDatastore.filter(stockFilter);
		} catch (DatastoreException e) {
			logger.error("Exception occurred while reading stock.", e);
			throw new PersistenceException(e);
		}

		if (matches.size() == 0) {
			return null;
		}

		if (matches.size() > 1) {
			String message = "Multiple matches found for stock symbol [" + stockSymbol + "]";
			logger.error(message);
			throw new PersistenceException(message);
		}

		return matches.toArray(new Stock[1])[0];
	}

	/**
	 * Returns a collection of all stocks defined into the persistent storage.
	 * 
	 * @return the collection of existing stocks
	 * @throws PersistenceException
	 */
	public Collection<Stock> listAll() throws PersistenceException {
		try {
			return stocksDatastore.list();
		} catch (DatastoreException e) {
			logger.error("Exception occurred while listing stocks information.", e);
			throw new PersistenceException(e);
		}
	}

	/**
	 * @param stocksDatastore
	 *            the stocksDatastore to set
	 */
	public void setStocksDatastore(StocksDatastore stocksDatastore) {
		this.stocksDatastore = stocksDatastore;
	}
}

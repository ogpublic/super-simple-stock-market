package com.acme.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acme.domain.Stock;
import com.acme.storage.filter.StockFilter;
import com.acme.util.StringUtils;

/**
 * {@link Datastore} implementation, specialized in managing stocks information.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class StocksDatastore extends AbstractDatastore<Stock> {
	private static final Logger logger = LogManager.getLogger(StocksDatastore.class);

	/**
	 * Internal table used to hold the stocks into.
	 */
	private Map<String, Stock> stocksTable;

	public StocksDatastore() {
		super();
	}

	@Override
	protected void initDatastore() {
		logger.debug("Initializing " + getClass().getSimpleName() + "...");

		this.stocksTable = new HashMap<String, Stock>();

		logger.debug("Initialization of " + getClass().getSimpleName() + " completed!");
	}

	/**
	 * Stores a new stock in this datastore. If the stock is already present, the operation will
	 * throw a {@link DatastoreException}.
	 */
	public void store(Stock data) throws DatastoreException {
		integrityCheck(data);

		if (stocksTable.containsKey(data.getSymbol())) {
			throw new DatastoreException("Stock already defined in datastore.");
		}

		stocksTable.put(data.getSymbol(), data);
	}

	/**
	 * Updates an existing stock in this datastore.
	 */
	public boolean update(Stock updatedStock) throws DatastoreException {
		integrityCheck(updatedStock);

		boolean updated = false;

		Stock stock = stocksTable.get(updatedStock.getSymbol());
		if (stock != null) {
			stock.setParValue(updatedStock.getParValue());
			stock.setSharesCount(updatedStock.getSharesCount());
			stock.setLastDividend(updatedStock.getLastDividend());
			stock.setFixedDividend(updatedStock.getFixedDividend());
		} else {
			// silently do nothing and allow to return false
		}

		return updated;
	}

	/**
	 * Returns the number of stocks which are currently stored by this datastore.
	 */
	public int count() throws DatastoreException {
		return stocksTable.values().size();
	}

	/**
	 * Returns a collection of filtered stocks.
	 */
	public Collection<Stock> filter(DatastoreFilter condition) throws DatastoreException {
		if (condition == null) {
			throw new DatastoreException("Null filter received by this datastore.");
		}

		if (!(condition instanceof StockFilter)) {
			throw new DatastoreException("Unknown filter [" + condition.getClass().getSimpleName() + "] for this datastore.");
		}

		if (!StringUtils.isValid(((StockFilter) condition).getStockSymbol())) {
			throw new DatastoreException("Invalid filter definition, missing search key.");
		}

		List<Stock> items = new LinkedList<Stock>();

		Stock stock = stocksTable.get(((StockFilter) condition).getStockSymbol());
		if (stock != null) {
			Stock stockCopy = new Stock(stock);
			items.add(new Stock(stockCopy));
		}

		return items;
	}

	/**
	 * Returna a collection of all stocks stored in this datastore.
	 */
	public Collection<Stock> list() throws DatastoreException {
		List<Stock> stocks = new LinkedList<Stock>();

		for (Stock stock : stocksTable.values()) {
			Stock stockCopy = new Stock(stock);
			stocks.add(stockCopy);
		}

		return stocks;
	}

	/**
	 * Performs simple integrity check on the stock information.
	 * 
	 * @param data
	 *            the stock to be validated
	 * @throws DatastoreException if validation fails
	 */
	private void integrityCheck(Stock data) throws DatastoreException {
		if (data == null) {
			throw new DatastoreException("Stock integrity check validation failed - cannot persist null stock information.");
		}

		if (!StringUtils.isValid(data.getSymbol())) {
			throw new DatastoreException("Stock integrity check validation failed - missing stock symbol (required).");
		}
	}
}

package com.acme.services.persistence;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acme.domain.Trade;
import com.acme.storage.DatastoreException;
import com.acme.storage.TradesDatastore;
import com.acme.storage.filter.TradeFilter;

/**
 * Default implementation of the trades persistence service.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class TradesPersistenceServiceImpl implements TradesPersistenceService {
	private static final Logger logger = LogManager.getLogger(TradesPersistenceServiceImpl.class);

	/**
	 * A reference to the trades datastore used by this persistence service implementation.
	 */
	private TradesDatastore tradesDatastore;

	/**
	 * Saves a new trade into the persistent storage.
	 * 
	 * @param trade
	 * @throws PersistenceException
	 */
	public void save(Trade trade) throws PersistenceException {
		try {
			tradesDatastore.store(trade);
		} catch (DatastoreException e) {
			logger.error("Exception occurred while persisting trade.", e);
			throw new PersistenceException(e);
		}
	}

	/**
	 * Returns a collection of all trades associated with a given stock symbol, with a specified
	 * maximum age.
	 * 
	 * @param stockSymbol
	 *            the symbol identifying the stock
	 * @param maxAgeInMinutes
	 *            the trades age in minutes.
	 */
	public Collection<Trade> listAgedTrades(String stockSymbol, int maxAgeInMinutes) throws PersistenceException {
		TradeFilter tradeFilter = new TradeFilter(stockSymbol, maxAgeInMinutes);

		Collection<Trade> agedTrades = new LinkedList<Trade>();
		try {
			agedTrades = tradesDatastore.filter(tradeFilter);
		} catch (DatastoreException e) {
			logger.error("Exception occurred while filtering datastore for aged trades, stock: [" + stockSymbol + "], max age in minutes: ["
					+ maxAgeInMinutes + "]");
			throw new PersistenceException(e);
		}

		return agedTrades;
	}

	/**
	 * Returns a collection of all trades defined in the datastore.
	 * 
	 * @return the trades collection.
	 * @throws PersistenceException
	 */
	public Collection<Trade> listAllTrades() throws PersistenceException {
		try {
			return tradesDatastore.list();
		} catch (DatastoreException e) {
			logger.error("Exception occurred while listing trades", e);
			throw new PersistenceException(e);
		}
	}

	/**
	 * @param tradesDatastore
	 *            the tradesDatastore to set
	 */
	public void setTradesDatastore(TradesDatastore tradesDatastore) {
		this.tradesDatastore = tradesDatastore;
	}
}

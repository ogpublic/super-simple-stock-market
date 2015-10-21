package com.acme.services.persistence;

import java.util.Collection;

import com.acme.domain.Trade;

/**
 * Interface defining the general contract of a trades persistence service.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public interface TradesPersistenceService {
	/**
	 * Saves a new trade into the persistent storage.
	 * 
	 * @param trade
	 * @throws PersistenceException
	 */
	void save(Trade trade) throws PersistenceException;

	/**
	 * Returns a collection of all trades associated with a given stock symbol, with a specified
	 * maximum age.
	 * 
	 * @param stockSymbol
	 *            the symbol identifying the stock
	 * @param maxAgeInMinutes
	 *            the trades age in minutes.
	 */
	Collection<Trade> listAgedTrades(String stockSymbol, int maxAgeInMinutes) throws PersistenceException;

	/**
	 * Returns a collection of all trades defined in the datastore.
	 * 
	 * @return the trades collection.
	 * @throws PersistenceException
	 */
	Collection<Trade> listAllTrades() throws PersistenceException;
}

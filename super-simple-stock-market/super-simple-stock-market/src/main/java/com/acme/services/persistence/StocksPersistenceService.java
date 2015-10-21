package com.acme.services.persistence;

import java.util.Collection;

import com.acme.domain.Stock;

/**
 * Interface defining the general contract of a stocks persistence service.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public interface StocksPersistenceService {
	/**
	 * Saves a new stock into the datastore.
	 * 
	 * @param stock
	 *            the stock to be persisted
	 * @throws PersistenceException
	 */
	void save(Stock stock) throws PersistenceException;

	/**
	 * Update a current existing stock in the datastore with the information on the passed in stock.
	 * 
	 * @param stock
	 *            the stock to be updated in the datastore
	 * @return true if the update operation has succedded or false otherwise
	 * @throws PersistenceException
	 */
	boolean update(Stock stock) throws PersistenceException;

	/**
	 * Reads an existing stock from the datastore based on the passed in stock symbol.
	 * 
	 * @param stockSymbol
	 * @return the stock or null if not found
	 * @throws PersistenceException
	 */
	Stock read(String stockSymbol) throws PersistenceException;

	/**
	 * Returns a collection of all stocks defined into the persistent storage.
	 * 
	 * @return the collection of existing stocks
	 * @throws PersistenceException
	 */
	Collection<Stock> listAll() throws PersistenceException;
}

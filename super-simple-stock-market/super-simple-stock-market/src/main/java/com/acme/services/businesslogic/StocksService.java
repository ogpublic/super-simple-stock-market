package com.acme.services.businesslogic;

import java.util.Collection;

import com.acme.domain.Stock;

/**
 * Interfaces defining the general contract (operations) a businesslogic stocks service must obey
 * to.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public interface StocksService {
	/**
	 * Creates a new stock in the datastore.
	 * 
	 * @param stock
	 *            the new stock to be created
	 * @throws BusinesslogicException
	 *             if an internal error occurs or if the stock fails the businesslogic validation.
	 */
	void createStock(Stock stock) throws BusinesslogicException;

	/**
	 * Reads a stock definition from the datastore
	 * 
	 * @param stockSymbol
	 *            the stock symbol to read
	 * @return the stock or null if not found
	 * @throws BusinesslogicException
	 */
	Stock getStock(String stockSymbol) throws BusinesslogicException;

	/**
	 * Lists all stocks defined in the datastore.
	 * 
	 * @return the collection of all defined stocks in the persistent storage.
	 * @throws BusinesslogicException
	 */
	Collection<Stock> listAllStocks() throws BusinesslogicException;
}

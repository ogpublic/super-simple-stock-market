package com.acme.services.businesslogic;

import java.util.Collection;

import com.acme.domain.Trade;

/**
 * Interfaces defining the general contract (operations) a businesslogic trades service must obey
 * to.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public interface TradesService {
	/**
	 * Creates a new trade into the persistent storage.
	 * 
	 * @param trade
	 *            the new trade to be created
	 * @throws BusinesslogicException
	 *             if an internal error occurrs or if the passed trade object fails the
	 *             businesslogic validation.
	 */
	void createTrade(Trade trade) throws BusinesslogicException;

	/**
	 * Returns a collection of all trades defined into the datastore.
	 * 
	 * @return the collection of existing trades.
	 * @throws BusinesslogicException
	 */
	Collection<Trade> listAllTrades() throws BusinesslogicException;
}

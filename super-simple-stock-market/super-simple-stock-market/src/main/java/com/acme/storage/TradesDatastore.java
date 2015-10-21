package com.acme.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acme.domain.Trade;
import com.acme.storage.filter.TradeFilter;
import com.acme.util.DateUtils;
import com.acme.util.StringUtils;

/**
 * {@link Datastore} implementation, specialized in managing trades information.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class TradesDatastore extends AbstractDatastore<Trade> {
	private static final Logger logger = LogManager.getLogger(TradesDatastore.class);

	/**
	 * Internal table holding for each stock symbol a collection of defined trades.
	 */
	private Map<String, Collection<Trade>> tradesTable;

	public TradesDatastore() {
		super();
	}

	@Override
	protected void initDatastore() {
		logger.debug("Initializing " + getClass().getSimpleName() + "...");

		this.tradesTable = new HashMap<String, Collection<Trade>>();

		logger.debug("Initialization of " + getClass().getSimpleName() + " completed!");
	}

	/**
	 * Stores a new trade in this datastore.
	 */
	public void store(Trade data) throws DatastoreException {
		integrityCheck(data);

		Collection<Trade> trades = tradesTable.get(data.getStockSymbol());
		if (trades == null) {
			trades = new LinkedList<Trade>();
			tradesTable.put(data.getStockSymbol(), trades);
		}
		trades.add(data);
	}

	/**
	 * Operation not implemented by this datastore.
	 */
	public boolean update(Trade updatedData) throws DatastoreException {
		throw new DatastoreException("Operation not supported by this datastore.");
	}

	/**
	 * Returns the count of all trades defined in this datastore.
	 */
	public int count() throws DatastoreException {
		return list().size();
	}

	/**
	 * Returns a collection of filtered trades matching the filtering conditions.
	 */
	public Collection<Trade> filter(DatastoreFilter condition) throws DatastoreException {
		if (condition == null) {
			throw new DatastoreException("Null filter received by this datastore.");
		}

		if (!(condition instanceof TradeFilter)) {
			throw new DatastoreException("Unknown filter [" + condition.getClass().getSimpleName() + "] for this datastore.");
		}

		if (!StringUtils.isValid(((TradeFilter) condition).getStockSymbol()) || ((TradeFilter) condition).getAgeInMinutes() <= 0) {
			throw new DatastoreException("Invalid filter definition, missing search keys information.");
		}

		List<Trade> items = new LinkedList<Trade>();

		Collection<Trade> trades = tradesTable.get(((TradeFilter) condition).getStockSymbol());
		if (trades != null) {
			for (Trade trade : trades) {
				int ageInMinutes = ((TradeFilter) condition).getAgeInMinutes();
				if (DateUtils.isNewerOrEqualTo(trade.getTimestamp(), ageInMinutes)) {
					Trade tradeCopy = new Trade(trade);
					items.add(tradeCopy);
				}
			}
		}

		return items;
	}

	/**
	 * Returns a collection of all trades stored by this datastore implementation.
	 */
	public Collection<Trade> list() throws DatastoreException {
		List<Trade> tradesCopy = new LinkedList<Trade>();

		for (Collection<Trade> trades : tradesTable.values()) {
			for (Trade trade : trades) {
				Trade tradeCopy = new Trade(trade);
				tradesCopy.add(tradeCopy);
			}
		}

		return tradesCopy;
	}

	/**
	 * Performs simple integrity check on a trade object
	 * 
	 * @param trade
	 *            the trade object to be validated
	 * @throws DatastoreException
	 *             if validation fails
	 */
	private void integrityCheck(Trade trade) throws DatastoreException {
		if (trade == null) {
			throw new DatastoreException("Trade integrity check validation failed - cannot persist null trade information.");
		}

		if (trade.getTimestamp() == null) {
			throw new DatastoreException("Trade integrity check validation failed - missing timestamp information (required).");
		}

		if (!StringUtils.isValid(trade.getStockSymbol())) {
			throw new DatastoreException("Trade integrity check validation failed - missing stock symbol information (required).");
		}
	}
}

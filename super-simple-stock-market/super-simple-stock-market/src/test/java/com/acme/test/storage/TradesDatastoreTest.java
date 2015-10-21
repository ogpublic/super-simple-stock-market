package com.acme.test.storage;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.acme.domain.Trade;
import com.acme.domain.TradeType;
import com.acme.storage.DatastoreException;
import com.acme.storage.TradesDatastore;
import com.acme.storage.filter.TradeFilter;
import com.acme.test.AbstractTest;

/**
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class TradesDatastoreTest extends AbstractTest {
	private static final Logger logger = LogManager.getLogger(TradesDatastoreTest.class);
	
	private TradesDatastore tradesDatastore;

	@Override
	protected void initTest() {
		tradesDatastore = getBean("tradesDatastore");
	}
	
	/**
	 * Test method for {@link com.acme.storage.TradesDatastore#store(com.acme.domain.Trade)}.
	 */
	@Test
	public void testFailedStore() {
		Trade trade = new Trade(null, TradeType.BUY, 100, 100.0, new Date());
		
		try {
			tradesDatastore.store(trade);
		} catch (DatastoreException e) {
			logger.info("Expected failure: Testing store operation failed due to: " + e.getMessage(), e);
		}
		
		int tradesCount = -1;
		try {
			tradesCount = tradesDatastore.count();
		} catch (DatastoreException e) {
			logger.error("Error occurred while counting stored objects", e);
			fail("Error occurred while counting stored objects: " + e.getMessage());
		}
		
		Assert.assertTrue(tradesCount == 0);
	}

	/**
	 * Test method for {@link com.acme.storage.TradesDatastore#store(com.acme.domain.Trade)}.
	 */
	@Test
	public void testSuccessfulStore() {
		Trade trade = new Trade("TEA", TradeType.BUY, 100, 100.0, new Date());
		
		try {
			tradesDatastore.store(trade);
		} catch (DatastoreException e) {
			logger.info("Unexpected failure: Testing store operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing store operation failed due to: " + e.getMessage());
		}
		
		int tradesCount = -1;
		try {
			tradesCount = tradesDatastore.count();
		} catch (DatastoreException e) {
			logger.error("Error occurred while counting stored objects", e);
			fail("Error occurred while counting stored objects: " + e.getMessage());
		}
		
		Assert.assertTrue(tradesCount == 1);
	}

	/**
	 * Test method for {@link com.acme.storage.TradesDatastore#update(com.acme.domain.Trade)}.
	 */
	@Test
	public void testUpdate() {
		Trade trade = new Trade("TEA", TradeType.BUY, 100, 100.0, new Date());
		
		try {
			tradesDatastore.store(trade);
		} catch (DatastoreException e) {
			logger.info("Unexpected failure: Testing store operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing store operation failed due to: " + e.getMessage());
		}
		
		Trade updatedTrade = new Trade(trade);
		updatedTrade.setQuantity(1000);
		
		try {
			tradesDatastore.update(updatedTrade);
		} catch (DatastoreException e) {
			logger.info("Expected failure: Testing update operation failed due to: " + e.getMessage(), e);
		}
		
		Collection<Trade> matches = new LinkedList<Trade>();
		try {
			matches = tradesDatastore.filter(new TradeFilter("TEA", 1));
		} catch (DatastoreException e) {
			logger.info("Unexpected failure: Testing filter operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing filter operation failed due to: " + e.getMessage());
		}
		
		Assert.assertTrue(matches != null && matches.size() == 1);
		
		Trade persistentTrade = matches.toArray(new Trade[1])[0];
		
		Assert.assertTrue(persistentTrade.getQuantity() == trade.getQuantity());
	}

	/**
	 * Test method for {@link com.acme.storage.TradesDatastore#count()}.
	 */
	@Test
	public void testCount() {
		try {
			tradesDatastore.store(new Trade("TEA", TradeType.BUY, 100, 100.0, new Date()));
			tradesDatastore.store(new Trade("TEA", TradeType.BUY, 100, 100.0, new Date()));
			tradesDatastore.store(new Trade("POP", TradeType.BUY, 100, 100.0, new Date()));
			tradesDatastore.store(new Trade("ALE", TradeType.BUY, 100, 100.0, new Date()));
		} catch (DatastoreException e) {
			logger.info("Unexpected failure: Testing store operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing store operation failed due to: " + e.getMessage());
		}
		
		int tradesCount = -1;
		try {
			tradesCount = tradesDatastore.count();
		} catch (DatastoreException e) {
			logger.info("Unexpected failure: Testing count operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing count operation failed due to: " + e.getMessage());
		}
		
		Assert.assertTrue(tradesCount == 4);
	}

	/**
	 * Test method for {@link com.acme.storage.TradesDatastore#filter(com.acme.storage.DatastoreFilter)}.
	 */
	@Test
	public void testFilter() {
		try {
			tradesDatastore.store(new Trade("TEA", TradeType.BUY, 100, 100.0, new Date()));
			tradesDatastore.store(new Trade("TEA", TradeType.BUY, 100, 100.0, new Date()));
			tradesDatastore.store(new Trade("POP", TradeType.BUY, 100, 100.0, new Date()));
			tradesDatastore.store(new Trade("ALE", TradeType.BUY, 100, 100.0, new Date()));
		} catch (DatastoreException e) {
			logger.info("Unexpected failure: Testing store operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing store operation failed due to: " + e.getMessage());
		}
		
		Collection<Trade> matches = new LinkedList<Trade>();
		try {
			matches = tradesDatastore.filter(new TradeFilter("TEA", 1));
		} catch (DatastoreException e) {
			logger.info("Unexpected failure: Testing filter operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing filter operation failed due to: " + e.getMessage());
		}
		
		Assert.assertTrue(matches != null && matches.size() == 2);
	}

	/**
	 * Test method for {@link com.acme.storage.TradesDatastore#list()}.
	 */
	@Test
	public void testList() {
		try {
			tradesDatastore.store(new Trade("TEA", TradeType.BUY, 100, 100.0, new Date()));
			tradesDatastore.store(new Trade("TEA", TradeType.BUY, 100, 100.0, new Date()));
			tradesDatastore.store(new Trade("POP", TradeType.BUY, 100, 100.0, new Date()));
			tradesDatastore.store(new Trade("ALE", TradeType.BUY, 100, 100.0, new Date()));
		} catch (DatastoreException e) {
			logger.info("Unexpected failure: Testing store operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing store operation failed due to: " + e.getMessage());
		}
		
		Collection<Trade> trades = new LinkedList<Trade>();
		try {
			trades = tradesDatastore.list();
		} catch (DatastoreException e) {
			logger.info("Unexpected failure: Testing list operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing list operation failed due to: " + e.getMessage());
		}
		
		Assert.assertTrue(trades != null && trades.size() == 4);
	}
}

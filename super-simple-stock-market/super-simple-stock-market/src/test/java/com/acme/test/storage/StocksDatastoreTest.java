package com.acme.test.storage;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.acme.domain.Stock;
import com.acme.domain.StockType;
import com.acme.storage.DatastoreException;
import com.acme.storage.StocksDatastore;
import com.acme.storage.filter.StockFilter;
import com.acme.test.AbstractTest;

/**
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class StocksDatastoreTest extends AbstractTest {
	private static final Logger logger = LogManager.getLogger(StocksDatastoreTest.class);
	
	private StocksDatastore stocksDatastore;

	@Override
	protected void initTest() {
		stocksDatastore = getBean("stocksDatastore");
	}
	
	/**
	 * Test method for {@link com.acme.storage.StocksDatastore#store(com.acme.domain.Stock)}.
	 */
	@Test
	public void testFailedStore() {
		Stock stock = new Stock(null, StockType.COMMON, 100, 100, 20, 0);
		
		try {
			stocksDatastore.store(stock);
		} catch (DatastoreException e) {
			logger.info("Expected failure: Testing store operation failed due to: " + e.getMessage(), e);
		}
		
		int stocksCount = -1;
		try {
			stocksCount = stocksDatastore.count();
		} catch (DatastoreException e) {
			logger.error("Error occurred while counting stocks", e);
		}
		
		Assert.assertTrue(stocksCount == 0);
	}

	/**
	 * Test method for {@link com.acme.storage.StocksDatastore#store(com.acme.domain.Stock)}.
	 */
	@Test
	public void testSuccessfulStore() {
		Stock stock = new Stock("TEA", StockType.COMMON, 100, 100, 20, 0);
		
		try {
			stocksDatastore.store(stock);
		} catch (DatastoreException e) {
			logger.error("Unexpected failure: Testing store operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing store operation failed due to: " + e.getMessage());
		}
		
		int stocksCount = -1;
		try {
			stocksCount = stocksDatastore.count();
		} catch (DatastoreException e) {
			logger.error("Error occurred while counting stored objects", e);
			fail("Unexpected failure: Error occurred while counting stored objects: " + e.getMessage());
		}
		
		Assert.assertTrue(stocksCount == 1);
	}

	/**
	 * Test method for {@link com.acme.storage.StocksDatastore#update(com.acme.domain.Stock)}.
	 */
	@Test
	public void testUpdate() {
		Stock stock = new Stock("TEA", StockType.COMMON, 100, 100, 20, 0);
		
		try {
			stocksDatastore.store(stock);
		} catch (DatastoreException e) {
			logger.error("Unexpected failure: Testing store operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Store operation failed due to: " + e.getMessage());
		}
		
		Stock updatedStock = new Stock(stock);
		updatedStock.setSharesCount(1000);
		
		try {
			stocksDatastore.update(updatedStock);
		} catch (DatastoreException e) {
			logger.error("Unexpected failure: Testing update operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing update operation failed due to: " + e.getMessage());
		}

		Collection<Stock> matches = new LinkedList<Stock>();
		try {
			matches = stocksDatastore.filter(new StockFilter("TEA"));
		} catch (DatastoreException e) {
			logger.error("Unexpected failure: filter operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: filter operation failed due to: " + e.getMessage());
		}
		
		Assert.assertTrue(matches != null && matches.size() == 1);
		
		Stock persistentStock = matches.toArray(new Stock[1])[0];
		
		Assert.assertTrue(persistentStock.getSharesCount() == stock.getSharesCount() && stock.getSharesCount() == 1000);
	}

	/**
	 * Test method for {@link com.acme.storage.StocksDatastore#filter(com.acme.storage.DatastoreFilter)}.
	 */
	@Test
	public void testFilter() {
		try {
			stocksDatastore.store(new Stock("TEA", StockType.COMMON, 100, 100, 20, 0));
			stocksDatastore.store(new Stock("POP", StockType.COMMON, 100, 100, 20, 0));
			stocksDatastore.store(new Stock("ALE", StockType.COMMON, 100, 100, 20, 0));
		} catch (DatastoreException e) {
			logger.error("Unexpected failure: Testing store operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Store operation failed due to: " + e.getMessage());
		}

		try {
			Assert.assertTrue(stocksDatastore.count() == 3);
		} catch (DatastoreException e) {
			logger.error("Error occurred while counting stored objects", e);
			fail("Unexpected failure: Error occurred while counting stored objects: " + e.getMessage());
		}

		Collection<Stock> matches = new LinkedList<Stock>();
		try {
			matches = stocksDatastore.filter(new StockFilter("TEA"));
		} catch (DatastoreException e) {
			logger.error("Unexpected failure: filter operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: filter operation failed due to: " + e.getMessage());
		}
		
		Assert.assertTrue(matches != null && matches.size() == 1);
	}

	/**
	 * Test method for {@link com.acme.storage.StocksDatastore#list()}.
	 */
	@Test
	public void testList() {
		try {
			stocksDatastore.store(new Stock("TEA", StockType.COMMON, 100, 100, 20, 0));
			stocksDatastore.store(new Stock("POP", StockType.COMMON, 100, 100, 20, 0));
			stocksDatastore.store(new Stock("ALE", StockType.COMMON, 100, 100, 20, 0));
		} catch (DatastoreException e) {
			logger.error("Unexpected failure: Testing store operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Store operation failed due to: " + e.getMessage());
		}

		try {
			Assert.assertTrue(stocksDatastore.count() == 3);
		} catch (DatastoreException e) {
			logger.error("Error occurred while counting stored objects", e);
			fail("Unexpected failure: Error occurred while counting stored objects: " + e.getMessage());
		}
		
		Collection<Stock> stocks = new LinkedList<Stock>();
		try {
			stocks = stocksDatastore.list();
		} catch (DatastoreException e) {
			logger.error("Error occurred while listing stored objects", e);
			fail("Unexpected failure: Error occurred while listing stored objects: " + e.getMessage());
		}
		
		Assert.assertTrue(stocks.size() == 3);
	}
}

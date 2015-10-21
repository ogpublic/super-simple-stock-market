package com.acme.test.persistence;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.acme.domain.Stock;
import com.acme.domain.StockType;
import com.acme.services.persistence.PersistenceException;
import com.acme.services.persistence.StocksPersistenceService;
import com.acme.test.AbstractTest;

/**
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class StocksPersistenceServiceTest extends AbstractTest {
	private static final Logger logger = LogManager.getLogger(StocksPersistenceServiceTest.class);
	
	private StocksPersistenceService stocksPersistenceService;
	
	@Override
	protected void initTest() {
		stocksPersistenceService = getBean("stocksPersistenceService");
	}

	/**
	 * Test method for {@link com.acme.services.persistence.StocksPersistenceService#save(com.acme.domain.Stock)}.
	 */
	@Test
	public void testFailedSave() {
		Stock stock = new Stock(null, StockType.COMMON, 100, 100, 20, 0);
		
		try {
			stocksPersistenceService.save(stock);
		} catch (PersistenceException e) {
			logger.info("Expected failure: Testing save operation failed due to: " + e.getMessage(), e);
		}
		
		int stocksCount = -1;
		try {
			stocksCount = stocksPersistenceService.listAll().size();
		} catch (PersistenceException e) {
			logger.error("Error occurred while counting stocks", e);
			fail("Error occurred while counting stocks");
		}
		
		Assert.assertTrue(stocksCount == 0);
	}

	/**
	 * Test method for {@link com.acme.services.persistence.StocksPersistenceService#save(com.acme.domain.Stock)}.
	 */
	@Test
	public void testSuccessfulSave() {
		Stock stock = new Stock("TEA", StockType.COMMON, 100, 100, 20, 0);
		
		try {
			stocksPersistenceService.save(stock);
		} catch (PersistenceException e) {
			logger.info("Expected failure: Testing save operation failed due to: " + e.getMessage(), e);
		}
		
		int stocksCount = -1;
		try {
			stocksCount = stocksPersistenceService.listAll().size();
		} catch (PersistenceException e) {
			logger.error("Error occurred while counting stocks", e);
			fail("Error occurred while counting stocks");
		}
		
		Assert.assertTrue(stocksCount == 1);
	}

	/**
	 * Test method for {@link com.acme.services.persistence.StocksPersistenceService#update(com.acme.domain.Stock)}.
	 */
	@Test
	public void testUpdate() {
		Stock stock = new Stock("TEA", StockType.COMMON, 100, 100, 20, 0);
		
		try {
			stocksPersistenceService.save(stock);
		} catch (PersistenceException e) {
			logger.error("Unexpected failure: Testing save operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Save operation failed due to: " + e.getMessage());
		}
		
		Stock updatedStock = new Stock(stock);
		updatedStock.setSharesCount(1000);
		
		try {
			stocksPersistenceService.update(updatedStock);
		} catch (PersistenceException e) {
			logger.error("Unexpected failure: Testing update operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing update operation failed due to: " + e.getMessage());
		}

		Stock persistentStock = null;
		try {
			persistentStock = stocksPersistenceService.read("TEA");
		} catch (PersistenceException e) {
			logger.error("Unexpected failure: read operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: read operation failed due to: " + e.getMessage());
		}
		
		Assert.assertTrue(persistentStock != null);
		Assert.assertTrue(persistentStock.getSharesCount() == stock.getSharesCount() && stock.getSharesCount() == 1000);
	}

	/**
	 * Test method for {@link com.acme.services.persistence.StocksPersistenceService#read(java.lang.String)}.
	 */
	@Test
	public void testRead() {
		Stock stock = new Stock("TEA", StockType.COMMON, 100, 100, 20, 0);
		
		try {
			stocksPersistenceService.save(stock);
		} catch (PersistenceException e) {
			logger.error("Unexpected failure: Testing save operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Save operation failed due to: " + e.getMessage());
		}
		
		Stock persistentStock = null;
		try {
			persistentStock = stocksPersistenceService.read("TEA");
		} catch (PersistenceException e) {
			logger.error("Unexpected failure: read operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: read operation failed due to: " + e.getMessage());
		}
		
		Assert.assertTrue(persistentStock != null);
	}

	/**
	 * Test method for {@link com.acme.services.persistence.StocksPersistenceService#listAll()}.
	 */
	@Test
	public void testListAll() {
		try {
			stocksPersistenceService.save(new Stock("TEA", StockType.COMMON, 100, 100, 20, 0));
			stocksPersistenceService.save(new Stock("POP", StockType.COMMON, 100, 100, 20, 0));
			stocksPersistenceService.save(new Stock("ALE", StockType.COMMON, 100, 100, 20, 0));
			stocksPersistenceService.save(new Stock("GIN", StockType.PREFERRED, 100, 100, 20, 100));
		} catch (PersistenceException e) {
			logger.error("Unexpected failure: Testing save operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Save operation failed due to: " + e.getMessage());
		}

		Collection<Stock> stocks = new LinkedList<Stock>();
		try {
			stocks = stocksPersistenceService.listAll();
		} catch (PersistenceException e) {
			logger.error("Unexpected failure: Testing list operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: List operation failed due to: " + e.getMessage());
		}
		
		Assert.assertTrue(stocks != null && stocks.size() == 4);
	}

}

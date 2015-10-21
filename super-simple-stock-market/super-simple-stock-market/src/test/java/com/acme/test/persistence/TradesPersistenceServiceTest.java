/**
 * 
 */
package com.acme.test.persistence;

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
import com.acme.services.persistence.PersistenceException;
import com.acme.services.persistence.TradesPersistenceService;
import com.acme.test.AbstractTest;
import com.acme.util.DateUtils;

/**
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class TradesPersistenceServiceTest extends AbstractTest {
	private static final Logger logger = LogManager.getLogger(TradesPersistenceServiceTest.class);
	
	private TradesPersistenceService tradesPersistenceService;
	
	@Override
	protected void initTest() {
		tradesPersistenceService = getBean("tradesPersistenceService");
	}

	/**
	 * Test method for {@link com.acme.services.persistence.TradesPersistenceService#save(com.acme.domain.Trade)}.
	 */
	@Test
	public void testFailedSave() {
		Trade trade = new Trade(null, TradeType.BUY, 100, 100.0, new Date());
		
		try {
			tradesPersistenceService.save(trade);
		} catch (PersistenceException e) {
			logger.info("Expected failure: Testing save operation failed due to: " + e.getMessage(), e);
		}
	}

	/**
	 * Test method for {@link com.acme.services.persistence.TradesPersistenceService#save(com.acme.domain.Trade)}.
	 */
	@Test
	public void testSuccessfulSave() {
		Trade trade = new Trade("TEA", TradeType.BUY, 100, 100.0, new Date());
		
		try {
			tradesPersistenceService.save(trade);
		} catch (PersistenceException e) {
			logger.error("Unexpected failure: Testing save operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing save operation failed due to: " + e.getMessage());
		}
		
		Collection<Trade> trades = new LinkedList<Trade>();
		try {
			trades = tradesPersistenceService.listAgedTrades("TEA", 1);
		} catch (PersistenceException e) {
			logger.error("Unexpected failure: Testing list operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing list operation failed due to: " + e.getMessage());
		}
		
		Assert.assertTrue(trades != null && trades.size() == 1);
	}

	/**
	 * Test method for {@link com.acme.services.persistence.TradesPersistenceService#listAgedTrades(java.lang.String, int)}.
	 */
	@Test
	public void testListAgedTrades() {
		try {
			tradesPersistenceService.save(new Trade("TEA", TradeType.BUY, 100, 100.0, DateUtils.parseDate("20150922", "yyyyMMdd")));
			tradesPersistenceService.save(new Trade("TEA", TradeType.BUY, 100, 100.0, DateUtils.parseDate("20150923", "yyyyMMdd")));
			tradesPersistenceService.save(new Trade("TEA", TradeType.BUY, 100, 100.0, DateUtils.parseDate("20150924", "yyyyMMdd")));
			tradesPersistenceService.save(new Trade("TEA", TradeType.BUY, 100, 100.0, new Date()));
			tradesPersistenceService.save(new Trade("TEA", TradeType.BUY, 100, 100.0, new Date()));
			tradesPersistenceService.save(new Trade("TEA", TradeType.BUY, 100, 100.0, new Date()));
		} catch (PersistenceException e) {
			logger.error("Unexpected failure: Testing save operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing save operation failed due to: " + e.getMessage());
		}
		
		Collection<Trade> trades = new LinkedList<Trade>();
		try {
			trades = tradesPersistenceService.listAgedTrades("TEA", 15);
		} catch (PersistenceException e) {
			logger.error("Unexpected failure: Testing list operation failed due to: " + e.getMessage(), e);
			fail("Unexpected failure: Testing list operation failed due to: " + e.getMessage());
		}
		
		Assert.assertTrue(trades != null && trades.size() == 3);
	}

}

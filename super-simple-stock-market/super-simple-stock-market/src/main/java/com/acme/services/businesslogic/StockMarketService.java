package com.acme.services.businesslogic;

/**
 * Interfaces defining the general contract (operations) a businesslogic stock market service must
 * obey to.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public interface StockMarketService {
	/**
	 * Calculates the dividend yield for a given stock and price.
	 * 
	 * @param stockSymbol
	 *            the stock symbol identifying the stock
	 * @param price
	 *            the price
	 * @return the computed dividend yield value
	 * @throws BusinesslogicException
	 */
	double calculateDividentYield(String stockSymbol, double price) throws BusinesslogicException;

	/**
	 * Calculates the P/E ratio for a given stock and price.
	 * 
	 * @param stockSymbol
	 *            the stock symbol identifying the stock
	 * @param price
	 *            the price
	 * @return the computed P/E ratio value
	 * @throws BusinesslogicException
	 */
	double calculatePERatio(String stockSymbol, double price) throws BusinesslogicException;

	/**
	 * Calculates the Volume Weighted Stock Price for the trades of a given stock and with a
	 * specified age in minutes.
	 * 
	 * @param stockSymbol
	 *            the stock symbol identifying the stock
	 * @param ageInMinutes
	 *            the trades age in minutes
	 * @return the computed volume weighted stock price
	 * @throws BusinesslogicException
	 */
	double calculateVolumeWeightedStockPrice(String stockSymbol, int ageInMinutes) throws BusinesslogicException;

	/**
	 * Calculates the GBCE All Share Index using the geometric mean of prices for all stocks.
	 * 
	 * @return the computed GBCE All Share Index
	 * @throws BusinesslogicException
	 */
	double calculateGBCEAllShareIndex() throws BusinesslogicException;
}

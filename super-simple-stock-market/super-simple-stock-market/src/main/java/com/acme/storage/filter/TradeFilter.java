package com.acme.storage.filter;

import com.acme.storage.DatastoreFilter;

/**
 * Implementation of a {@link DatastoreFilter} used to filter trades by a stock symbol and age
 * specified in minutes.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class TradeFilter implements DatastoreFilter {
	/**
	 * The symbol identifying the stock based on which the trade was created.
	 */
	private String stockSymbol;
	/**
	 * The trade age in minutes.
	 */
	private int ageInMinutes;

	public TradeFilter() {
		this(null, -1);
	}

	public TradeFilter(String stockSymbol, int ageInMinutes) {
		this.stockSymbol = stockSymbol;
		this.ageInMinutes = ageInMinutes;
	}

	/**
	 * @return the stockSymbol
	 */
	public String getStockSymbol() {
		return stockSymbol;
	}

	/**
	 * @param stockSymbol
	 *            the stockSymbol to set
	 */
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	/**
	 * @return the ageInMinutes
	 */
	public int getAgeInMinutes() {
		return ageInMinutes;
	}

	/**
	 * @param ageInMinutes
	 *            the ageInMinutes to set
	 */
	public void setAgeInMinutes(int ageInMinutes) {
		this.ageInMinutes = ageInMinutes;
	}
}

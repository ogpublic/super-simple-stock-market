package com.acme.storage.filter;

import com.acme.storage.DatastoreFilter;

/**
 * Implementation of a {@link DatastoreFilter} used to filter stocks by a stock symbol.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class StockFilter implements DatastoreFilter {
	/**
	 * The stock symbol used to filter the stocks.
	 */
	private String stockSymbol;

	public StockFilter() {
		this(null);
	}

	public StockFilter(String stockSymbol) {
		this.stockSymbol = stockSymbol;
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
}

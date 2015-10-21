package com.acme.domain;

/**
 * Domain object definition mapping the structure of a stock.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 */
public class Stock {
	/**
	 * Stock symbol (e.g. "TEA", "POP", etc.)
	 */
	private String symbol;
	/**
	 * The stock type.
	 */
	private StockType type;
	/**
	 * Number of shares held by this stock.
	 */
	private int sharesCount;
	/**
	 * Stock par value.
	 */
	private double parValue;
	/**
	 * Stock last dividend value.
	 */
	private double lastDividend;
	/**
	 * Stock fixed dividend value.
	 */
	private double fixedDividend;

	/**
	 * Default constructor
	 */
	public Stock() {
		super();
	}

	/**
	 * Creates a new stock as a copy of the stock parameter
	 * 
	 * @param stock
	 *            the original stock
	 */
	public Stock(Stock stock) {
		this.symbol = stock.getSymbol();
		this.type = stock.getType();
		this.sharesCount = stock.getSharesCount();
		this.parValue = stock.getParValue();
		this.lastDividend = stock.getLastDividend();
		this.fixedDividend = stock.getFixedDividend();
	}

	/**
	 * Creates a new stock object.
	 * 
	 * @param symbol
	 *            the stock symbol
	 * @param type
	 *            the stock type
	 * @param sharesCount
	 *            the number of shares in this stock
	 * @param parValue
	 *            the par value
	 * @param lastDividend
	 *            the last dividend value
	 * @param fixedDividend
	 *            the fixed dividend value
	 */
	public Stock(String symbol, StockType type, int sharesCount, double parValue, double lastDividend, double fixedDividend) {
		this.symbol = symbol;
		this.type = type;
		this.sharesCount = sharesCount;
		this.parValue = parValue;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the type
	 */
	public StockType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(StockType type) {
		this.type = type;
	}

	/**
	 * @return the sharesCount
	 */
	public int getSharesCount() {
		return sharesCount;
	}

	/**
	 * @param sharesCount
	 *            the sharesCount to set
	 */
	public void setSharesCount(int sharesCount) {
		this.sharesCount = sharesCount;
	}

	/**
	 * @return the parValue
	 */
	public double getParValue() {
		return parValue;
	}

	/**
	 * @param parValue
	 *            the parValue to set
	 */
	public void setParValue(double parValue) {
		this.parValue = parValue;
	}

	/**
	 * @return the lastDividend
	 */
	public double getLastDividend() {
		return lastDividend;
	}

	/**
	 * @param lastDividend
	 *            the lastDividend to set
	 */
	public void setLastDividend(double lastDividend) {
		this.lastDividend = lastDividend;
	}

	/**
	 * @return the fixedDividend
	 */
	public double getFixedDividend() {
		return fixedDividend;
	}

	/**
	 * @param fixedDividend
	 *            the fixedDividend to set
	 */
	public void setFixedDividend(double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fixedDividend);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lastDividend);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(parValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + sharesCount;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Stock other = (Stock) obj;
		if (Double.doubleToLongBits(fixedDividend) != Double.doubleToLongBits(other.fixedDividend)) {
			return false;
		}
		if (Double.doubleToLongBits(lastDividend) != Double.doubleToLongBits(other.lastDividend)) {
			return false;
		}
		if (Double.doubleToLongBits(parValue) != Double.doubleToLongBits(other.parValue)) {
			return false;
		}
		if (sharesCount != other.sharesCount) {
			return false;
		}
		if (symbol == null) {
			if (other.symbol != null) {
				return false;
			}
		} else if (!symbol.equals(other.symbol)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Stock [symbol=" + symbol + ", type=" + type + ", sharesCount=" + sharesCount + ", parValue=" + parValue + ", lastDividend="
				+ lastDividend + ", fixedDividend=" + fixedDividend + "]";
	}
}

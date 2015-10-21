package com.acme.domain;

import java.util.Date;

/**
 * Domain object definition mapping the structure of a trade.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 */
public class Trade {
	/**
	 * The symbol of the stock referenced by this trade
	 */
	private String stockSymbol;
	/**
	 * The trade type
	 */
	private TradeType type;
	/**
	 * Trade quantity
	 */
	private int quantity;
	/**
	 * Trade price
	 */
	private double price;
	/**
	 * Creation time and date of this trade
	 */
	private Date timestamp;

	/**
	 * Empty constructor
	 */
	public Trade() {
		super();
	}

	/**
	 * Creates a new trade as a copy of the received one as a parameter
	 * 
	 * @param trade
	 *            original trade to be copied
	 */
	public Trade(Trade trade) {
		this.stockSymbol = trade.getStockSymbol();
		this.type = trade.getType();
		this.quantity = trade.getQuantity();
		this.price = trade.getPrice();
		this.timestamp = trade.getTimestamp();
	}

	/**
	 * Creates a new trade based on the passed in parameters
	 * 
	 * @param stockSymbol
	 *            the stock symbol
	 * @param type
	 *            trade type
	 * @param quantity
	 *            trade quantity
	 * @param price
	 *            trade price
	 * @param timestamp
	 *            trade timestamp
	 */
	public Trade(String stockSymbol, TradeType type, int quantity, double price, Date timestamp) {
		this.stockSymbol = stockSymbol;
		this.type = type;
		this.quantity = quantity;
		this.price = price;
		this.timestamp = timestamp;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the type
	 */
	public TradeType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(TradeType type) {
		this.type = type;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		result = prime * result + ((stockSymbol == null) ? 0 : stockSymbol.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
		Trade other = (Trade) obj;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price)) {
			return false;
		}
		if (quantity != other.quantity) {
			return false;
		}
		if (stockSymbol == null) {
			if (other.stockSymbol != null) {
				return false;
			}
		} else if (!stockSymbol.equals(other.stockSymbol)) {
			return false;
		}
		if (timestamp == null) {
			if (other.timestamp != null) {
				return false;
			}
		} else if (!timestamp.equals(other.timestamp)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Trade [stockSymbol=" + stockSymbol + ", type=" + type + ", quantity=" + quantity + ", price=" + price + ", timestamp=" + timestamp
				+ "]";
	}
}

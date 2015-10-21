package com.acme.storage;

/**
 * Exception used by datastoree implementations.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class DatastoreException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DatastoreException() {
		super();
	}

	/**
	 * @param message
	 */
	public DatastoreException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DatastoreException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DatastoreException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DatastoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

package com.acme.services.businesslogic;

/**
 * Exception used by businesslogic services implementations.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public class BusinesslogicException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Empty constructor
	 */
	public BusinesslogicException() {
		super();
	}

	/**
	 * @param message
	 */
	public BusinesslogicException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public BusinesslogicException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BusinesslogicException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public BusinesslogicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

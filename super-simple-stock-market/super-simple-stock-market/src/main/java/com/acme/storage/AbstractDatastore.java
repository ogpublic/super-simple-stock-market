package com.acme.storage;

/**
 * Abstract implementation of a {@link Datastore}.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public abstract class AbstractDatastore<T> implements Datastore<T> {

	public AbstractDatastore() {
		initDatastore();
	}

	/**
	 * Allows custom initialization in subclasses.
	 */
	protected abstract void initDatastore();
}

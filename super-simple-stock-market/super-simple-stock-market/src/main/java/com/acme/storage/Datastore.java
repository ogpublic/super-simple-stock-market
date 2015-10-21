package com.acme.storage;

import java.util.Collection;

/**
 * Interface defining the general contract and operations which need to be implemented and provided
 * by an implementation.
 * 
 * @author Ovidiu Guse - ovidiu.guse@gmail.com, 2015
 *
 */
public interface Datastore<T> {
	/**
	 * Stores a new object in the datastore.
	 * 
	 * @param data
	 *            the object to be persisted.
	 * @throws DatastoreException
	 */
	void store(T data) throws DatastoreException;

	/**
	 * Updates an existing persistent object in the datastore with the information provided through
	 * the updated data parameter.
	 * 
	 * @param updatedData
	 *            the object containing the update information for the persistent object in the
	 *            datastore.
	 * @return true if update has been successful, false otherwise.
	 * @throws DatastoreException
	 */
	boolean update(T updatedData) throws DatastoreException;

	/**
	 * Returns the number of objects in the datastore
	 * 
	 * @return
	 * @throws DatastoreException
	 */
	int count() throws DatastoreException;

	/**
	 * Filters the objects stored on this datastore based on a specific filter/condition definition
	 * 
	 * @param condition
	 *            the filtering information to be applied when performing the filtering.
	 * @return a collection of filtered objects from this datastore.
	 * @throws DatastoreException
	 */
	Collection<T> filter(DatastoreFilter condition) throws DatastoreException;

	/**
	 * Returns a collection with all objects defined in this datastore.
	 * 
	 * @return the collection of persistent objects defined in the datastore.
	 * @throws DatastoreException
	 */
	Collection<T> list() throws DatastoreException;
}

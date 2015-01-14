package com.github.vsspt.common.db.dao.api;

import java.io.Serializable;

import java.util.List;

import com.github.vsspt.common.db.dao.api.util.PagingList;
import com.github.vsspt.common.db.dao.api.util.Parameter;

import com.github.vsspt.common.db.dao.api.search.GenericSearch;

public interface IGenericDAO<T extends Serializable, PK extends Serializable> {
    /**
     * Deletes the database record with the supplied ID
     * 
     * @param id
     *            The PK of the object to Delete
     */
    void delete(PK id);

    /**
     * Finds all instances of a given class
     * 
     * @return all instances
     */
    List<T> findAll();

    /**
     * Tries to find the Database Records similar to the supplied object
     * 
     * @param instance
     *            The object supplied
     * @return A list of similar objects
     */
    List<T> findByExample(T instance);

    /**
     * Tries to find the Database Record with the supplied ID
     * 
     * @param id
     *            The PK of the object to find
     * @return The object, if found, null otherwise
     */
    T findById(PK id);

    /**
     * Gets a Page of T records, based on the search criteria
     * 
     * @param search
     *            The Search Criteria Object
     * @return A Page of Database Records that conform to the search criteria.
     */
    PagingList<T> get(GenericSearch search);

    /**
     * Gets the name of the class Model
     * 
     * @return the name of the class Model
     */
    String getClassName();

    /**
     * Updates the Object in the database
     * 
     * @param detachedInstance
     *            The object to Update
     * @return The updated object
     */

    void update(T detachedInstance);

    /**
     * Saves a Model Object in his correspondent Database Table
     * 
     * @param transientInstance
     *            The Model Object
     * @return The PK of the saved object
     */
    PK save(T transientInstance);
	
	
    /**
     * Executes the Query specified by its ID
     * 
     * @param queryId : The ID of the query
     *            
     * @return The List of results.
     */
    List<T> execute(String queryId, Parameter ... params);

    
    /**
     * Executes the Query specified by its ID
     * 
     * @param queryId : The ID of the query
     *            
     * @return The List of results.
     */    
    List<?> executeQuery(String queryId, Parameter ... params);

    /**
     * Merges the Object in the database
     * 
     * @param detachedInstance
     *            The object to Merge
     * @return The merged object
     */    
    void merge(T detachedInstance);

    
    /**
     * Saves Or Updates the Object in the database
     * 
     * @param detachedInstance
     *            The object to SaveOrUpdate
     * @return The saved object
     */        
    void saveOrUpdate(T detachedInstance);

}

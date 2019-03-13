package com.excilys.cdb.main.persistence;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.main.model.Entity;

/**
 * 
 * @author bbinet
 *
 *         Base class for DAOs Contains default queries ready to accept
 *         parameters
 * 
 * @param <T> Type of DAO, must extend Entity
 */
public abstract class DAO<T extends Entity> {

    Connection dbConnection;
    Mapper<T> mapper;
    
    /**
     * Static field to create standard INSERT queries.
     * <ul>
     *      <li>The table in which the insert will occur</li>
     *      <li>The list of columns in which the values will be inserted, separated by commas</li>
     *      <li>Must be a list of question marks, one for each column in the columns param</li>
     * </ul>
     */
    final static String INSERT_QUERY = "INSERT INTO %s(%s) VALUES(%s);";
    
    /**
     * Static field to create standard UPDATE queries.
     * <ul>
     *      <li>The table in which the update will occur</li>
     *      <li>The list of columns to update, such as : "column=?", seperated by commas</li>
     *      <li>The id of the object to update</li>
     * </ul>
     */
    final static String UPDATE_QUERY = "UPDATE %s SET %s WHERE id=?;";
    
    /**
     * Static field to create standard SELECT queries.
     * <ul>
     *      <li>The list of columns to fetch, separated by commas</li>
     *      <li>The table in which the update will occur</li>
     *      <li>Optionnal restrictions</li>
     * </ul>
     */
    final static String SELECT_QUERY = "SELECT %s FROM %s %s;";
    
    /**
     * Static field to create standard DELETE queries.
     * <ul>
     *      <li>The table in which the delete will occur</li>
     *      <li>The id of the object to delete</li>
     * </ul>     
     */
    final static String DELETE_QUERY = "DELETE FROM %s WHERE id=?;";

    static final Logger LOG = LoggerFactory.getLogger(JDBCSingleton.class);

    /**
     * Standard constructor, 
     */
    public DAO() {
        this.dbConnection = JDBCSingleton.INSTANCE.getConnection();
        LOG.info("Creating DAO");
    }

    /**
     * Create a row for an object in database.
     * @param object The object to create.
     * @return true if query succeeded, false otherwise.
     */
    public abstract boolean create(T object);

    /**
     * Find a row with an id.
     * @param id The id of the object to find.
     * @return An instance of the object initialized with the values in the database.
     */
    public abstract T read(Integer id);

    /**
     * Update an object in database using its id.
     * @param object The object to update.
     * @return true if query succeeded, false otherwise.
     */
    public abstract boolean update(T object);

    /**
     * Delete an object from the database.
     * @param object The object to delete
     * @return true if query succeeded, false otherwise.
     */
    public abstract boolean delete(T object);

    /**
     * Fetch the whole list of object from database
     * @return A List containing all entities from database.
     */
    public abstract List<T> list();
}

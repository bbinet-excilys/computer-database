package com.excilys.cdb.main.model;

/**
 * 
 * @author bbinet
 * Base class for database entity
 */
public abstract class Entity {
    /**
     * The id of the entity in the database
     */
    Integer id;
    
    /**
     * 
     * @return The Entity ID
     */
    public Integer getId() {
        return this.id;
    }
    
    /**
     * 
     * @param id The Entity ID to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Basic toString override
     */
    public abstract String toString();
    
}

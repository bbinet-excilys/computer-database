package com.excilys.cdb.main.model;

/**
 * 
 * @author bbinet
 * Base class for database entity
 */
public abstract class Entity {
    
    Integer id;
    
    public abstract String toString();
    
    public abstract Integer getId();
    
    public abstract void setId(Integer id);

}

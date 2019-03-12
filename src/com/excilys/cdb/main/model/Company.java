package com.excilys.cdb.main.model;
/**
 * 
 * @author bbinet
 * Model class for database company entity
 */
public class Company extends Entity {

    private String  name;

    public Company() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return String.format("%5s | %30s", this.id, this.name);
    }

}

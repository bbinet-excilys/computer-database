package com.excilys.cdb.main.model;

import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.main.model.Entity;


/**
 * 
 * @author bbinet
 * Pagination class for database entity
 * @param <T> Class of the entity (must extend Entity)
 */
public class EntityPage<T extends Entity> {

    Integer pageSize, offset;
    List<T> entities;

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public List<T> getEntities() {
        return this.entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }
    
    public int getPage() {
        return 1+((this.offset % this.entities.size() + this.entities.size())%this.entities.size())/this.pageSize;
    }

    public List<T> getPage(int direction) {
        List<T> rEntityList = new ArrayList<T>();
        this.offset = this.offset + (direction * this.pageSize);
        int index = (this.offset % this.entities.size() + this.entities.size())%this.entities.size();
        for (int i = 0; i < this.pageSize; i++) {
            rEntityList.add(this.entities.get(index));
            index = (index + 1) % this.entities.size();
        }
        return rEntityList;
    }

}

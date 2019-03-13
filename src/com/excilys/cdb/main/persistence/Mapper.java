package com.excilys.cdb.main.persistence;

import java.sql.ResultSet;
import java.util.List;

import com.excilys.cdb.main.model.Entity;

public abstract class Mapper<T extends Entity> {
    
    public abstract T map(ResultSet resultSet);
    
    public abstract List<T> mapList(ResultSet resultSet);
    
//    public abstract Statement unmap(T object);
    
}

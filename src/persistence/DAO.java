package persistence;

import java.sql.Connection;
import java.util.List;

import model.Entity;

public abstract class DAO<T extends Entity> {

    Connection dbConnection;

    public DAO(Connection connection) {
        this.dbConnection = connection;
    }

    public abstract boolean create(T object);

    public abstract T read(Integer id);

    public abstract boolean update(T object);

    public abstract boolean delete(T object);
    
    public abstract List<T> list(Integer size, Integer offset);
    public abstract List<T> list();
}

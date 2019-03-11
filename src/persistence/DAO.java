package persistence;

import java.sql.Connection;

public abstract class DAO<T> {

    Connection dbConnection;

    public DAO(Connection connection) {
        this.dbConnection = connection;
    }

    public abstract boolean create(T object);

    public abstract T read(Integer id);

    public abstract boolean update(T object);

    public abstract boolean delete(T object);
}

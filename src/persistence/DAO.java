package persistence;

import java.sql.Connection;
import java.util.List;

import model.Entity;

public abstract class DAO<T extends Entity> {

    Connection dbConnection;

    final static String INSERT_QUERY = "INSERT INTO %s(%s) VALUES(%s);";
    final static String UPDATE_QUERY = "UPDATE %s SET %s WHERE id=?;";
    final static String SELECT_QUERY = "SELECT %s FROM %s %s;";
    final static String DELETE_QUERY = "DELETE FROM %s WHERE id=?;";

    public DAO(Connection connection) {
        this.dbConnection = connection;
    }

    public abstract boolean create(T object);

    public abstract T read(Integer id);

    public abstract boolean update(T object);

    public abstract boolean delete(T object);

    public abstract List<T> list();
}

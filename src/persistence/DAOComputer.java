package persistence;

import java.sql.Connection;

import model.Computer;

public class DAOComputer extends DAO<Computer> {

    public DAOComputer(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Computer object) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Computer read(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean update(Computer object) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(Computer object) {
        // TODO Auto-generated method stub
        return false;
    }

}

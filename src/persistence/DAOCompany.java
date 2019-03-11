package persistence;

import java.sql.Connection;

import model.Company;

public class DAOCompany extends DAO<Company> {

    public DAOCompany(Connection connection) {
        super(connection);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean create(Company object) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Company read(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean update(Company object) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(Company object) {
        // TODO Auto-generated method stub
        return false;
    }

}

package com.excilys.cdb.main.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.main.model.Company;

public class DAOCompany extends DAO<Company>{

    public DAOCompany() {
        super();
    }

    @Override
    public boolean create(Company object) {
        PreparedStatement mPreparedStatement = null;
        try {
            mPreparedStatement = dbConnection.prepareStatement(String.format(INSERT_QUERY, "company", "name", "?"));
            mPreparedStatement.setString(1, object.getName());
            mPreparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (mPreparedStatement != null) {
                try {
                    mPreparedStatement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public Company read(Integer id) {
        PreparedStatement mPreparedStatement = null;
        ResultSet         mResultSet         = null;
        Company           rCompany           = null;
        try {
            mPreparedStatement = dbConnection
                    .prepareStatement(String.format(SELECT_QUERY, "id, name", "company", "WHERE id=?"));
            mPreparedStatement.setInt(1, id);
            mResultSet = mPreparedStatement.executeQuery();
            if (mResultSet.first()) {
                rCompany = new Company();
                rCompany.setId(mResultSet.getInt("id"));
                rCompany.setName(mResultSet.getString("name"));
                return rCompany;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (mPreparedStatement != null) {
                try {
                    mPreparedStatement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (mResultSet != null) {
                try {
                    mResultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    public boolean update(Company object) {
        PreparedStatement mPreparedStatement = null;
        try {
            mPreparedStatement = dbConnection
                    .prepareStatement(String.format(UPDATE_QUERY, "company", "name=?", "id=?"));
            mPreparedStatement.setString(1, object.getName());
            mPreparedStatement.setInt(2, object.getId());
            mPreparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (mPreparedStatement != null) {
                try {
                    mPreparedStatement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public boolean delete(Company object) {
        PreparedStatement mPreparedStatement = null;
        try {
            mPreparedStatement = dbConnection.prepareStatement(String.format(DELETE_QUERY, "company"));
            mPreparedStatement.setInt(1, object.getId());
            mPreparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (mPreparedStatement != null) {
                try {
                    mPreparedStatement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    @Override
    public List<Company> list() {
        PreparedStatement mPreparedStatement = null;
        ResultSet         mResultSet         = null;
        List<Company>     rCompanyList       = null;
        try {
            rCompanyList = new ArrayList<Company>();
            mPreparedStatement = dbConnection.prepareStatement(String.format(SELECT_QUERY, "id, name", "company", ""));
            mResultSet         = mPreparedStatement.executeQuery();
            rCompanyList       = new ArrayList<Company>();
            while (mResultSet.next()) {
                Company tCompany = new Company();
                tCompany.setId(mResultSet.getInt("id"));
                tCompany.setName(mResultSet.getString("name"));
                rCompanyList.add(tCompany);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (mPreparedStatement != null) {
                try {
                    mPreparedStatement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (mResultSet != null) {
                try {
                    mResultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rCompanyList;
    }

}
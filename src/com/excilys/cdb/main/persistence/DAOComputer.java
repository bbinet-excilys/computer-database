package com.excilys.cdb.main.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.main.model.Company;
import com.excilys.cdb.main.model.Computer;

public class DAOComputer extends DAO<Computer> {

    public DAOComputer() {
        super();
    }

    @Override
    public boolean create(Computer object) {
        PreparedStatement mPreparedStatement = null;
        try {
            mPreparedStatement = this.dbConnection.prepareStatement(
                    String.format(INSERT_QUERY, "computer", "name, introduced, discontinued, company_id", "?,?,?,?"));
            mPreparedStatement.setString(1, object.getName());
            mPreparedStatement.setDate(2, object.getIntroduced());
            mPreparedStatement.setDate(3, object.getDiscontinued());
            mPreparedStatement.setObject(4, object.getCompanyId());
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
    public Computer read(Integer id) {
        Computer          rComputer                  = null;
        PreparedStatement mComputerPreparedStatement = null;
        PreparedStatement mCompanyPreparedStatement  = null;
        ResultSet         mComputerResultSet         = null;
        ResultSet         mCompanyResultSet          = null;
        try {
            mComputerPreparedStatement = dbConnection.prepareStatement(String.format(SELECT_QUERY,
                    "id, name, introduced, discontinued, company_id", "computer", "WHERE id=?"));
            mComputerPreparedStatement.setInt(1, id);
            mComputerResultSet = mComputerPreparedStatement.executeQuery();
            if (mComputerResultSet.first()) {
                rComputer = new Computer();
                Company rCompany = null;
                rComputer.setId(mComputerResultSet.getInt("id"));
                rComputer.setName(mComputerResultSet.getString("name"));
                rComputer.setIntroduced(mComputerResultSet.getDate("introduced"));
                rComputer.setDiscontinued(mComputerResultSet.getDate("discontinued"));
                rComputer.setCompanyId(mComputerResultSet.getInt("company_id"));
                mCompanyPreparedStatement = dbConnection
                        .prepareStatement(String.format(SELECT_QUERY, "id, name", "company", "WHERE id=?"));
                mCompanyPreparedStatement.setInt(1, rComputer.getCompanyId());
                mCompanyResultSet = mCompanyPreparedStatement.executeQuery();
                if (mCompanyResultSet.first()) {
                    rCompany = new Company();
                    rCompany.setId(mCompanyResultSet.getInt("id"));
                    rCompany.setName(mCompanyResultSet.getString("name"));
                }
                rComputer.setCompany(rCompany);
                return rComputer;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (mComputerPreparedStatement != null) {
                try {
                    mComputerPreparedStatement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (mComputerResultSet != null) {
                try {
                    mComputerResultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return rComputer;
    }

    @Override
    public boolean update(Computer object) {
        PreparedStatement mPreparedStatement = null;
        try {
            mPreparedStatement = dbConnection.prepareStatement(
                    String.format(UPDATE_QUERY, "computer", "name=?, introduced=?, discontinued=?, company_id=?"));
            mPreparedStatement.setString(1, object.getName());
            mPreparedStatement.setDate(2, object.getIntroduced());
            mPreparedStatement.setDate(3, object.getDiscontinued());
            mPreparedStatement.setObject(4, object.getCompanyId());
            mPreparedStatement.setInt(5, object.getId());
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
    public boolean delete(Computer object) {
        PreparedStatement mPreparedStatement = null;
        try {
            mPreparedStatement = dbConnection.prepareStatement(String.format(DELETE_QUERY, "computer"));
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
    public List<Computer> list() {
        List<Computer>    rComputerList      = null;
        PreparedStatement mPreparedStatement = null;
        ResultSet         mResultSet         = null;
        ResultSet         mCompanyResultSet  = null;
        PreparedStatement mCompanyPreparedStatement  = null;
        try {
            rComputerList      = new ArrayList<Computer>();
            mPreparedStatement = dbConnection.prepareStatement(
                    String.format(SELECT_QUERY, "id, name, introduced, discontinued, company_id", "computer", ""));
            mResultSet         = mPreparedStatement.executeQuery();
            while (mResultSet.next()) {
                Computer tComputer = new Computer();
                Company rCompany = null;
                tComputer.setId(mResultSet.getInt("id"));
                tComputer.setName(mResultSet.getString("name"));
                tComputer.setIntroduced(mResultSet.getDate("introduced"));
                tComputer.setDiscontinued(mResultSet.getDate("discontinued"));
                tComputer.setCompanyId(mResultSet.getInt("company_id"));
                mCompanyPreparedStatement = dbConnection
                        .prepareStatement(String.format(SELECT_QUERY, "id, name", "company", "WHERE id=?"));
                mCompanyPreparedStatement.setInt(1, tComputer.getCompanyId());
                mCompanyResultSet = mCompanyPreparedStatement.executeQuery();
                if (mCompanyResultSet.first()) {
                    rCompany = new Company();
                    rCompany.setId(mCompanyResultSet.getInt("id"));
                    rCompany.setName(mCompanyResultSet.getString("name"));
                }
                tComputer.setCompany(rCompany);
                rComputerList.add(tComputer);
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
        return rComputerList;
    }

}

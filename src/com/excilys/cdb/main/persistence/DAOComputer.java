package com.excilys.cdb.main.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.main.model.Computer;

public class DAOComputer extends DAO<Computer> {

    static final Logger LOG = LoggerFactory.getLogger(DAOComputer.class);

    static final String SELECT_COMPUTER_AND_COMPANY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name \n"
            + "FROM computer\n" + "LEFT OUTER JOIN company\n" + "ON computer.company_id=company.id %s;";

    {
        this.mapper = new ComputerMapper();
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
            LOG.warn("Couldn't execute insert query : " + e.getMessage());
        }
        finally {
            if (mPreparedStatement != null) {
                try {
                    mPreparedStatement.close();
                }
                catch (SQLException e) {
                    LOG.warn("Couldn't close PreparedStatement : " + e.getMessage());
                }
            }
        }
        return false;
    }

    @Override
    public Computer read(Integer id) {
        Computer          rComputer          = null;
        PreparedStatement mPreparedStatement = null;
        ResultSet         mResultSet         = null;
        try {
            mPreparedStatement = dbConnection
                    .prepareStatement(String.format(SELECT_COMPUTER_AND_COMPANY, "WHERE id=?"));
            mPreparedStatement.setInt(1, id);
            mResultSet = mPreparedStatement.executeQuery();
            if (mResultSet.first()) {
                rComputer = this.mapper.map(mResultSet);
            }
        }
        catch (SQLException e) {
            LOG.warn("Coudn't execute select query :" + e.getMessage());
        }
        finally {
            if (mPreparedStatement != null) {
                try {
                    mPreparedStatement.close();
                }
                catch (SQLException e) {
                    LOG.warn("Couldn't close select preparedStatement : " + e.getMessage());
                }
            }
            if (mResultSet != null) {
                try {
                    mResultSet.close();
                }
                catch (SQLException e) {
                    LOG.warn("Couldn't close select resultSet : " + e.getMessage());
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
            LOG.warn("Couldn't update : " + e.getMessage());
        }
        finally {
            if (mPreparedStatement != null) {
                try {
                    mPreparedStatement.close();
                }
                catch (SQLException e) {
                    LOG.warn("Couldn't close preparedStatement : " + e.getMessage());
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
            LOG.warn("Couldn't execute delete query : " + e.getMessage());
        }
        finally {
            if (mPreparedStatement != null) {
                try {
                    mPreparedStatement.close();
                }
                catch (SQLException e) {
                    LOG.warn("Couldn't close preparedStatement : " + e.getMessage());
                }
            }
        }
        return false;
    }

    @Override
    public List<Computer> list() {
        List<Computer>    rComputerList             = null;
        PreparedStatement mPreparedStatement        = null;
        ResultSet         mResultSet                = null;
        try {
            mPreparedStatement = dbConnection.prepareStatement(String.format(SELECT_COMPUTER_AND_COMPANY, ""));
            mResultSet         = mPreparedStatement.executeQuery();
            if (mResultSet.first()) {
                rComputerList = this.mapper.mapList(mResultSet);
            }
        }
        catch (SQLException e) {
            LOG.warn("Couldn't execute select in list : " + e.getMessage());
        }
        finally {
            if (mPreparedStatement != null) {
                try {
                    mPreparedStatement.close();
                }
                catch (SQLException e) {
                    LOG.warn("Couldn't close preparedStatement : " + e.getMessage());
                }
            }
            if (mResultSet != null) {
                try {
                    mResultSet.close();
                }
                catch (SQLException e) {
                    LOG.warn("Couldn't close resultSet : " + e.getMessage());
                }
            }
        }
        return rComputerList;
    }

}

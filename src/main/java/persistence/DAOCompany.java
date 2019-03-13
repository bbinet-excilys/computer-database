package persistence;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;

public class DAOCompany extends DAO<Company> {

    static final Logger LOG = LoggerFactory.getLogger(DAOCompany.class);
    
    {this.mapper = new CompanyMapper();}

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
            LOG.error("Error in Create query : " + e.getMessage());
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
                rCompany = this.mapper.map(mResultSet);
            }
        }
        catch (SQLException e) {
            LOG.warn("Couldn't execute select query : " + e.getMessage());
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
        return rCompany;
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
            LOG.warn("Couldn't execute update query : " + e.getMessage());
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
    public boolean delete(Company object) {
        PreparedStatement mPreparedStatement = null;
        try {
            mPreparedStatement = dbConnection.prepareStatement(String.format(DELETE_QUERY, "company"));
            mPreparedStatement.setInt(1, object.getId());
            mPreparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            LOG.warn("Couldn't execute delete query: " + e.getMessage());
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
    public List<Company> list() {
        PreparedStatement mPreparedStatement = null;
        ResultSet         mResultSet         = null;
        List<Company>     rCompanyList       = null;
        try {
            mPreparedStatement = dbConnection.prepareStatement(String.format(SELECT_QUERY, "id, name", "company", ""));
            mResultSet         = mPreparedStatement.executeQuery();
            if(mResultSet.first())
                rCompanyList       = this.mapper.mapList(mResultSet);
        }
        catch (SQLException e) {
            LOG.warn("Couldn't execute select query : " + e.getMessage());
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
        return rCompanyList;
    }

}

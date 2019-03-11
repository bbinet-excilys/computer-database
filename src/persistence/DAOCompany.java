package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Company;

public class DAOCompany extends DAO<Company> {

    public DAOCompany(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Company object) {
        PreparedStatement mPreparedStatement = null;
        try {
            mPreparedStatement = dbConnection.prepareStatement("INSERT INTO company(name) VALUES(?);");
            mPreparedStatement.setString(1, object.getName());
            int status = mPreparedStatement.executeUpdate();
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
            mPreparedStatement = dbConnection.prepareStatement("SELECT id, name FROM company WHERE id=?;");
            mResultSet         = mPreparedStatement.executeQuery();
            mResultSet.first();
            rCompany = new Company();
            rCompany.setId(mResultSet.getInt("id"));
            rCompany.setName(mResultSet.getString("name"));
            return rCompany;
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
            mPreparedStatement = dbConnection.prepareStatement("UPDATE computer SET name=? WHERE id=?;");
            mPreparedStatement.setString(1, object.getName());
            mPreparedStatement.setInt(2, object.getId());
            int status = mPreparedStatement.executeUpdate();
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
            mPreparedStatement = dbConnection.prepareStatement("DELETE FROM company WHERE id=?;");
            mPreparedStatement.setInt(1, object.getId());
            int status = mPreparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(mPreparedStatement != null) {
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

}

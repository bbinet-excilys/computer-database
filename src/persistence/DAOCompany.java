package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            mPreparedStatement.setInt(1, id);
            mResultSet         = mPreparedStatement.executeQuery();
            if(mResultSet.first()) {
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
        ResultSet mResultSet = null;
        List<Company> rCompanyList = null;
        try {
            mPreparedStatement = dbConnection.prepareStatement("SELECT id, name FROM company;");
            mResultSet = mPreparedStatement.executeQuery();
            rCompanyList = new ArrayList<Company>();
            while(mResultSet.next()) {
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
            if(mPreparedStatement != null) {
                try {
                    mPreparedStatement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(mResultSet != null) {
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

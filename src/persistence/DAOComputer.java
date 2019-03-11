package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Computer;

public class DAOComputer extends DAO<Computer> {

    public DAOComputer(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Computer object) {
        PreparedStatement mPreparedStatement = null;
        try {
            mPreparedStatement = this.dbConnection.prepareStatement(
                    "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?,?,?,?,?);");
            mPreparedStatement.setString(1, object.getName());
            mPreparedStatement.setDate(2, object.getIntroduced());
            mPreparedStatement.setDate(3, object.getDiscontinued());
            mPreparedStatement.setInt(4, object.getCompanyId());
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
        Computer          rComputer          = null;
        PreparedStatement mPreparedStatement = null;
        ResultSet         mResultSet         = null;
        try {
            mPreparedStatement = dbConnection
                    .prepareStatement("SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id=?");
            mPreparedStatement.setInt(1, id);
            mResultSet = mPreparedStatement.executeQuery();
            mResultSet.first();
            rComputer = new Computer();
            rComputer.setId(mResultSet.getInt("id"));
            rComputer.setName(mResultSet.getString("name"));
            rComputer.setIntroduced(mResultSet.getDate("introduced"));
            rComputer.setDiscontinued(mResultSet.getDate("discontinued"));
            rComputer.setCompanyId(mResultSet.getInt("company_id"));
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
        return rComputer;
    }

    @Override
    public boolean update(Computer object) {
        PreparedStatement mPreparedStatement = null;
        try {
            mPreparedStatement = dbConnection.prepareStatement(
                    "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE ID=?");
            mPreparedStatement.setString(1, object.getName());
            mPreparedStatement.setDate(2, object.getIntroduced());
            mPreparedStatement.setDate(3, object.getDiscontinued());
            mPreparedStatement.setInt(4, object.getCompanyId());
            mPreparedStatement.setInt(5, object.getId());
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
    public boolean delete(Computer object) {
        PreparedStatement mPreparedStatement = null;
        try {
            mPreparedStatement = dbConnection.prepareStatement("DELETE FROM computer WHERE id=?");
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
    public List<Computer> list() {
        List<Computer>    rComputerList      = null;
        PreparedStatement mPreparedStatement = null;
        ResultSet         mResultSet         = null;
        try {
            rComputerList      = new ArrayList<Computer>();
            mPreparedStatement = dbConnection
                    .prepareStatement("SELECT id, name, introduced, discontinued, company_id FROM computer;");
            mResultSet         = mPreparedStatement.executeQuery();
            while (mResultSet.next()) {
                Computer tComputer = new Computer();
                tComputer.setId(mResultSet.getInt("id"));
                tComputer.setName(mResultSet.getString("name"));
                tComputer.setIntroduced(mResultSet.getDate("introduced"));
                tComputer.setDiscontinued(mResultSet.getDate("discontinued"));
                tComputer.setCompanyId(mResultSet.getInt("company_id"));
                rComputerList.add(tComputer);
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
        return rComputerList;
    }

}

package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;
import model.Entity;

public class DAOCompany extends DAO {

  /**
   * Logger for the DAOCompany class.
   */
  static final Logger LOG = LoggerFactory.getLogger(DAOCompany.class);

  /**
   * Set the Mapper on DAOCompany instanciation.
   */
  Mapper mapper = new CompanyMapper();

  @Override
  public boolean create(Entity entity) {
    PreparedStatement mPreparedStatement = null;
    if (entity instanceof Company) {
      Company company = (Company) entity;
      try {
        mPreparedStatement = this.dbConnection.prepareStatement(String.format(INSERT_QUERY, "company", "name", "?"));
        mPreparedStatement.setString(1, company.getName());
        mPreparedStatement.executeUpdate();
        return true;
      }
      catch (SQLException e) {
        LOG.error("Error in Create query : " + e.getMessage());
        return false;
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
    }
    return false;
  }

  @Override
  public boolean delete(Entity entity) {
    if (entity instanceof Company) {
      Company           company            = (Company) entity;
      PreparedStatement mPreparedStatement = null;
      try {
        mPreparedStatement = this.dbConnection.prepareStatement(String.format(DELETE_QUERY, "company"));
        mPreparedStatement.setInt(1, company.getId());
        mPreparedStatement.executeUpdate();
        return true;
      }
      catch (SQLException e) {
        LOG.warn("Couldn't execute delete query: " + e.getMessage());
        return false;
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
    }
    return false;
  }

  @Override
  public List<Entity> list() {
    PreparedStatement mPreparedStatement = null;
    ResultSet         mResultSet         = null;
    List<Entity>      rCompanyList       = null;
    try {
      mPreparedStatement = this.dbConnection.prepareStatement(String.format(SELECT_QUERY, "id, name", "company", ""));
      mResultSet         = mPreparedStatement.executeQuery();
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

  @Override
  public Entity read(Integer id) {
    PreparedStatement mPreparedStatement = null;
    ResultSet         mResultSet         = null;
    Entity            rCompany           = null;
    try {
      mPreparedStatement = this.dbConnection
          .prepareStatement(String.format(SELECT_QUERY, "id, name", "company", "WHERE id=?"));
      mPreparedStatement.setInt(1, id);
      mResultSet = mPreparedStatement.executeQuery();
      rCompany   = this.mapper.map(mResultSet);
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
  public boolean update(Entity entity) {
    if (entity instanceof Company) {
      Company           company            = (Company) entity;
      PreparedStatement mPreparedStatement = null;
      try {
        mPreparedStatement = this.dbConnection
            .prepareStatement(String.format(UPDATE_QUERY, "company", "name=?", "id=?"));
        mPreparedStatement.setString(1, company.getName());
        mPreparedStatement.setInt(2, company.getId());
        mPreparedStatement.executeUpdate();
        return true;
      }
      catch (SQLException e) {
        LOG.warn("Couldn't execute update query : " + e.getMessage());
        return false;
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
    }
    return false;
  }

  @Override
  public Integer count() {
    PreparedStatement mPreparedStatement = null;
    Integer           count              = null;
    ResultSet         mResultSet         = null;
    try {
      mPreparedStatement = this.dbConnection.prepareStatement(String.format(COUNT_QUERY, "company"));
      mResultSet         = mPreparedStatement.executeQuery();
      if (mResultSet.first()) {
        count = mResultSet.getInt("count");
      }
    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute count query : " + e.getMessage());
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
    return count;
  }

  @Override
  public List<Entity> paginatedList(Integer size, Integer offset) {
    // TODO Auto-generated method stub
    return null;
  }

}

package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Computer;
import model.Entity;

public class DAOComputer extends DAO {

  /**
   * Logger for the DAOComputer Class.
   */
  static final Logger LOG = LoggerFactory.getLogger(DAOComputer.class);

  /**
   * SELECT query for a computer. Uses a join to fetch company informations.
   */
  static final String SELECT_COMPUTER_AND_COMPANY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name \n"
      + "FROM computer\n" + "LEFT OUTER JOIN company\n" + "ON computer.company_id=company.id %s;";

  Mapper mapper = new ComputerMapper();

  @Override
  public boolean create(Entity entity) {
    if (entity instanceof Computer) {
      Computer          computer           = (Computer) entity;
      PreparedStatement mPreparedStatement = null;
      try {
        mPreparedStatement = this.dbConnection.prepareStatement(
            String.format(INSERT_QUERY, "computer", "name, introduced, discontinued, company_id", "?,?,?,?"));
        mPreparedStatement.setString(1, computer.getName());
        mPreparedStatement.setDate(2, computer.getIntroduced());
        mPreparedStatement.setDate(3, computer.getDiscontinued());
        mPreparedStatement.setObject(4, computer.getCompanyId());
        mPreparedStatement.executeUpdate();
        return true;
      }
      catch (SQLException e) {
        LOG.warn("Couldn't execute insert query : " + e.getMessage());
        return false;
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
    }
    return false;
  }

  @Override
  public boolean delete(Entity entity) {
    if (entity instanceof Computer) {
      Computer          computer           = (Computer) entity;
      PreparedStatement mPreparedStatement = null;
      try {
        mPreparedStatement = this.dbConnection.prepareStatement(String.format(DELETE_QUERY, "computer"));
        mPreparedStatement.setInt(1, computer.getId());
        mPreparedStatement.executeUpdate();
        return true;
      }
      catch (SQLException e) {
        LOG.warn("Couldn't execute delete query : " + e.getMessage());
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
    List<Entity>      rComputerList      = null;
    PreparedStatement mPreparedStatement = null;
    ResultSet         mResultSet         = null;
    try {
      mPreparedStatement = this.dbConnection.prepareStatement(String.format(SELECT_COMPUTER_AND_COMPANY, ""));
      mResultSet         = mPreparedStatement.executeQuery();
      if (mResultSet.first()) {
        rComputerList = this.mapper.mapList(mResultSet);
      }
    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute select in list : " + e.getMessage());
    }
    catch (NullPointerException e) {
      LOG.error("An object is null (probably connection : " + this.dbConnection);
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

  @Override
  public Entity read(Integer id) {
    Entity            rComputer          = null;
    PreparedStatement mPreparedStatement = null;
    ResultSet         mResultSet         = null;
    try {
      mPreparedStatement = this.dbConnection
          .prepareStatement(String.format(SELECT_COMPUTER_AND_COMPANY, "WHERE computer.id=?"));
      mPreparedStatement.setInt(1, id);
      mResultSet = mPreparedStatement.executeQuery();
      rComputer  = this.mapper.map(mResultSet);
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
  public boolean update(Entity entity) {
    if (entity instanceof Computer) {
      Computer          computer           = (Computer) entity;
      PreparedStatement mPreparedStatement = null;
      try {
        mPreparedStatement = this.dbConnection.prepareStatement(
            String.format(UPDATE_QUERY, "computer", "name=?, introduced=?, discontinued=?, company_id=?"));
        mPreparedStatement.setString(1, computer.getName());
        mPreparedStatement.setDate(2, computer.getIntroduced());
        mPreparedStatement.setDate(3, computer.getDiscontinued());
        mPreparedStatement.setObject(4, computer.getCompanyId());
        mPreparedStatement.setInt(5, computer.getId());
        mPreparedStatement.executeUpdate();
        return true;
      }
      catch (SQLException e) {
        LOG.warn("Couldn't update : " + e.getMessage());
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
  public List<Entity> paginatedList(Integer size, Integer offset) {
    List<Entity>      rComputerList      = null;
    PreparedStatement mPreparedStatement = null;
    ResultSet         mResultSet         = null;
    try {
      if (size != null && offset != null) {
        mPreparedStatement = this.dbConnection
            .prepareStatement(String.format(SELECT_COMPUTER_AND_COMPANY, "LIMIT ? OFFSET ?"));
        mPreparedStatement.setInt(1, size);
        mPreparedStatement.setInt(2, offset);
        mResultSet    = mPreparedStatement.executeQuery();
        rComputerList = this.mapper.mapList(mResultSet);
      }
    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute select in list : " + e.getMessage());
    }
    catch (NullPointerException e) {
      LOG.error("An object is null (probably connection : " + this.dbConnection);
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

  @Override
  public Integer count() {
    PreparedStatement mPreparedStatement = null;
    Integer           count              = null;
    ResultSet         mResultSet         = null;
    try {
      mPreparedStatement = this.dbConnection.prepareStatement(String.format(COUNT_QUERY, "computer"));
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
}

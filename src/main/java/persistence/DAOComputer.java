package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Computer;

public class DAOComputer {

  /**
   * Logger for the DAOComputer Class.
   */
  static final Logger  LOG            = LoggerFactory.getLogger(DAOComputer.class);
  private final String SELECT         = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer LEFT OUTER JOIN company on computer.company_id=company.id;";
  private final String CREATE         = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?);";
  private final String UPDATE         = "UPDATE computer SET name=?, introduced=?, dicsontinued=?, company_id=? WHERE id=?;";
  private final String DELETE         = "DELETE FROM computer WHERE id=?;";
  private final String SELECT_WHEREID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer LEFT OUTER JOIN company on computer.company_id=company.id WHERE computer.id=?;";
  private final String COUNT          = "SELECT count(*) as count FROM computer;";

  ComputerMapper mapper = new ComputerMapper();

  public boolean create(Computer computer) {
    try (Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.CREATE)) {
      preparedStatement.setString(1, computer.getName());
      preparedStatement.setDate(2, computer.getIntroduced());
      preparedStatement.setDate(3, computer.getDiscontinued());
      preparedStatement.setObject(4, computer.getCompany().getId());
      preparedStatement.executeUpdate();
      return true;
    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute insert query : " + e.getMessage());
      return false;
    }

  }

  public boolean delete(Computer computer) {
    try (Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.DELETE)) {
      preparedStatement.setLong(1, computer.getId());
      preparedStatement.executeUpdate();
      return true;
    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute delete query : " + e.getMessage());
      return false;
    }
  }

  public List<Computer> list() {
    List<Computer> rComputerList = null;
    ResultSet      mResultSet    = null;
    try (Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.SELECT)) {
      mResultSet = preparedStatement.executeQuery();
      if (mResultSet.first()) {
        rComputerList = this.mapper.mapList(mResultSet);
      }
    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute select in list : " + e.getMessage());
    }
    catch (NullPointerException e) {
      LOG.error("An object is null (probably connection) : ");
    }
    return rComputerList;
  }

  public Computer read(Long id) {
    Computer rComputer = null;
    try (Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.SELECT_WHEREID);
        ResultSet resultSet = preparedStatement.executeQuery();) {
      preparedStatement.setLong(1, id);
      rComputer = this.mapper.map(resultSet);
    }
    catch (SQLException e) {
      LOG.warn("Coudn't execute select query :" + e.getMessage());
    }
    return rComputer;
  }

  public boolean update(Computer computer) {
    PreparedStatement mPreparedStatement = null;
    try (Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.UPDATE)) {
      preparedStatement.setString(1, computer.getName());
      preparedStatement.setDate(2, computer.getIntroduced());
      preparedStatement.setDate(3, computer.getDiscontinued());
      preparedStatement.setObject(4, computer.getCompany().getId());
      preparedStatement.setLong(5, computer.getId());
      preparedStatement.executeUpdate();
      return true;
    }
    catch (SQLException e) {
      LOG.warn("Couldn't update : " + e.getMessage());
      return false;
    }
  }

  public List<Computer> paginatedList(Integer size, Integer offset) {
    List<Computer> rComputerList = null;
    ResultSet      mResultSet    = null;
    try (Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.SELECT)) {
      if (size != null && offset != null) {
        preparedStatement.setInt(1, size);
        preparedStatement.setInt(2, offset);
        mResultSet    = preparedStatement.executeQuery();
        rComputerList = this.mapper.mapList(mResultSet);
      }
    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute select in list : " + e.getMessage());
    }
    catch (NullPointerException e) {
      LOG.error("An object is null (probably connection) ");
    }
    return rComputerList;
  }

  public Integer count() {
    Integer count = null;
    try (Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.COUNT);
        ResultSet resultSet = preparedStatement.executeQuery();) {
      if (resultSet.first()) {
        count = resultSet.getInt("count");
      }
    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute count query : " + e.getMessage());
    }
    return count;
  }
}

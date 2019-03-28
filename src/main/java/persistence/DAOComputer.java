package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.DAOUnexecutedQuery;
import exception.PropertiesNotFoundException;
import mapping.ComputerMapper;
import model.Company;
import model.Computer;

public class DAOComputer {

  /**
   * Logger for the DAOComputer Class.
   */
  static final Logger  LOG            = LoggerFactory.getLogger(DAOComputer.class);
  private final String SELECT         = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer LEFT OUTER JOIN company on computer.company_id=company.id;";
  private final String SELECT_LIMIT   = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer LEFT OUTER JOIN company on computer.company_id=company.id LIMIT ? OFFSET ?;";
  private final String CREATE         = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?,?,?,?);";
  private final String UPDATE         = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
  private final String DELETE         = "DELETE FROM computer WHERE id=?;";
  private final String SELECT_WHEREID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer LEFT OUTER JOIN company on computer.company_id=company.id WHERE computer.id=?;";
  private final String COUNT          = "SELECT count(*) as count FROM computer;";

  ComputerMapper mapper = new ComputerMapper();

  public void create(Computer computer) throws DAOUnexecutedQuery, PropertiesNotFoundException {
    try (
        Connection connection = JDBCSingleton.INSTANCE.getHikariConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CREATE)
    ) {
      preparedStatement.setString(1, computer.getName());
      preparedStatement.setDate(2, computer.getIntroduced());
      preparedStatement.setDate(3, computer.getDiscontinued());
      Optional<Company> oCompany  = Optional.ofNullable(computer.getCompany());
      Long              companyId = oCompany.map(Company::getId).orElse(null);
      preparedStatement.setObject(4, companyId);
      preparedStatement.executeUpdate();
    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute insert query : " + e.getMessage());
      throw new DAOUnexecutedQuery("Couldn't create computer", e);
    }
  }

  public void delete(Computer computer) throws DAOUnexecutedQuery, PropertiesNotFoundException {
    try (
        Connection connection = JDBCSingleton.INSTANCE.getHikariConnection();
        PreparedStatement preparedStatement = prepareDeleteStatement(connection, computer.getId());
    ) {
      preparedStatement.executeUpdate();
    }
    catch (SQLException e) {
      throw new DAOUnexecutedQuery("Couldn't execute delete query", e);
    }
  }

  private PreparedStatement prepareDeleteStatement(Connection connection, Long id)
      throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
    preparedStatement.setLong(1, id);
    return preparedStatement;
  }

  public List<Computer> list() throws PropertiesNotFoundException {
    List<Computer> rComputerList = new ArrayList<>();
    ResultSet      mResultSet    = null;
    try (
        Connection connection = JDBCSingleton.INSTANCE.getHikariConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT)
    ) {
      mResultSet    = preparedStatement.executeQuery();
      rComputerList = mapper.mapList(mResultSet);

    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute select in list : " + e.getMessage());
    }
    catch (NullPointerException e) {
      LOG.error("An object is null (probably connection)");
    }
    return rComputerList;
  }

  public Optional<Computer> read(Long id) throws DAOUnexecutedQuery, PropertiesNotFoundException {
    Optional<Computer> oComputer = Optional.empty();
    try (
        Connection connection = JDBCSingleton.INSTANCE.getHikariConnection();
        PreparedStatement preparedStatement = prepareReadStatement(connection, id);
        ResultSet resultSet = preparedStatement.executeQuery();
    ) {
      oComputer = mapper.map(resultSet);
    }
    catch (SQLException e) {
      LOG.error("Couldn't execute the select query.");
      throw new DAOUnexecutedQuery("Couldn't execute the select query", e);
    }
    return oComputer;
  }

  private PreparedStatement prepareReadStatement(Connection connection, Long id)
      throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WHEREID);
    preparedStatement.setLong(1, id);
    return preparedStatement;
  }

  public void update(Computer computer) throws PropertiesNotFoundException {
    try (
        Connection connection = JDBCSingleton.INSTANCE.getHikariConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)
    ) {
      preparedStatement.setString(1, computer.getName());
      preparedStatement.setDate(2, computer.getIntroduced());
      preparedStatement.setDate(3, computer.getDiscontinued());
      preparedStatement.setObject(4, computer.getCompany().getId());
      preparedStatement.setLong(5, computer.getId());
      preparedStatement.executeUpdate();
    }
    catch (SQLException e) {
      LOG.warn("Couldn't update : " + e.getMessage());
    }
  }

  public List<Computer> paginatedList(Integer size, Integer offset)
      throws PropertiesNotFoundException {
    List<Computer> rComputerList = null;
    try (
        Connection connection = JDBCSingleton.INSTANCE.getHikariConnection();
        PreparedStatement preparedStatement = preparedSelectLimitStatement(connection, size,
                                                                           offset);
        ResultSet mResultSet = preparedStatement.executeQuery();
    ) {
      if (size != null && offset != null) {
        rComputerList = mapper.mapList(mResultSet);
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

  private PreparedStatement preparedSelectLimitStatement(Connection connection, Integer size,
      Integer offset)
      throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LIMIT);
    preparedStatement.setInt(1, size);
    preparedStatement.setInt(2, offset);
    return preparedStatement;
  }

  public Integer count() throws PropertiesNotFoundException {
    Integer count = null;
    try (
        Connection connection = JDBCSingleton.INSTANCE.getHikariConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(COUNT);
        ResultSet resultSet = preparedStatement.executeQuery();
    ) {
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

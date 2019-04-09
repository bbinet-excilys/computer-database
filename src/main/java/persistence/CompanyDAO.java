package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.DAOUnexecutedQuery;
import exception.PropertiesNotFoundException;
import mapping.CompanyMapper;
import model.Company;

public class CompanyDAO {

  /**
   * Logger for the DAOCompany class.
   */
  private final Logger LOG            = LoggerFactory.getLogger(CompanyDAO.class);
  private final String SELECT         = "SELECT id, name FROM company;";
  private final String SELECT_LIMIT   = "SELECT id, name FROM company LIMIT ? OFFSET ?;";
  private final String CREATE         = "INSERT INTO company(name) VALUES(?);";
  private final String UPDATE         = "UPDATE company SET name=? WHERE id=?;";
  private final String DELETE         = "DELETE FROM company WHERE id=?;";
  private final String SELECT_WHEREID = "SELECT id, name FROM company WHERE id=?;";
  private final String COUNT          = "SELECT count(*) as count FROM company;";
  /**
   * Set the Mapper on DAOCompany instanciation.
   */
  CompanyMapper        mapper         = new CompanyMapper();

  public List<Company> list() throws PropertiesNotFoundException {
    ResultSet     mResultSet   = null;
    List<Company> rCompanyList = null;
    try (
        Connection connection = JDBCSingleton.INSTANCE.getHikariConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT);
    ) {
      mResultSet   = preparedStatement.executeQuery();
      rCompanyList = mapper.mapList(mResultSet);
    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute select query : " + e.getMessage());
    }
    return rCompanyList;
  }

  public Optional<Company> read(Long id) throws PropertiesNotFoundException {
    Optional<Company> oCompany = Optional.empty();
    try (
        Connection connection = JDBCSingleton.INSTANCE.getHikariConnection();
        PreparedStatement preparedStatement = prepareReadStatement(connection, id);
        ResultSet resultSet = preparedStatement.executeQuery();
    ) {
      oCompany = mapper.map(resultSet);
    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute select query : " + e.getMessage());
    }
    return oCompany;
  }

  private PreparedStatement prepareReadStatement(Connection connection, Long id)
    throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WHEREID);
    preparedStatement.setLong(1, id);
    return preparedStatement;
  }

  public void update(Company company) throws PropertiesNotFoundException {
    try (
        Connection connection = JDBCSingleton.INSTANCE.getHikariConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
    ) {
      preparedStatement.setString(1, company.getName());
      preparedStatement.setLong(2, company.getId());
      preparedStatement.executeUpdate();
    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute update query : " + e.getMessage());
    }

  }

  public List<Company> paginatedList(Integer size, Integer offset)
    throws PropertiesNotFoundException {
    List<Company> rCompanyList = null;
    try (
        Connection connection = JDBCSingleton.INSTANCE.getHikariConnection();
        PreparedStatement preparedStatement = preparedSelectLimitStatement(connection, size,
                                                                           offset);
        ResultSet mResultSet = preparedStatement.executeQuery();
    ) {
      if (size != null && offset != null) {
        rCompanyList = mapper.mapList(mResultSet);
      }
    }
    catch (SQLException e) {
      LOG.warn("Couldn't execute select in list : " + e.getMessage());
    }
    catch (NullPointerException e) {
      LOG.error("An object is null (probably connection) ");
    }
    return rCompanyList;
  }

  private PreparedStatement preparedSelectLimitStatement(Connection connection, Integer size,
      Integer offset)
    throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LIMIT);
    preparedStatement.setInt(1, size);
    preparedStatement.setInt(2, offset);
    return preparedStatement;
  }

  public void delete(Company company) throws DAOUnexecutedQuery, PropertiesNotFoundException {
    try (
        Connection connection = JDBCSingleton.INSTANCE.getHikariConnection();
        PreparedStatement preparedStatement = prepareDeleteStatement(connection, company.getId());
    ) {
      preparedStatement.executeUpdate();
    }
    catch (SQLException e) {
      throw new DAOUnexecutedQuery("Couldn't delete company", e);
    }
  }

  public PreparedStatement prepareDeleteStatement(Connection connection, Long companyId)
    throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
    preparedStatement.setLong(1, companyId);
    return preparedStatement;
  }

}

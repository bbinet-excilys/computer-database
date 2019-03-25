package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;

public class DAOCompany {

  /**
   * Logger for the DAOCompany class.
   */
  private final Logger LOG            = LoggerFactory.getLogger(DAOCompany.class);
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

  public List<Company> list() {
    ResultSet     mResultSet   = null;
    List<Company> rCompanyList = null;
    try (
        Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.SELECT);
    ) {
      mResultSet   = preparedStatement.executeQuery();
      rCompanyList = this.mapper.mapList(mResultSet);
    }
    catch (SQLException e) {
      this.LOG.warn("Couldn't execute select query : " + e.getMessage());
    }
    return rCompanyList;
  }

  public Optional<Company> read(Long id) {
    Optional<Company> oCompany = Optional.empty();
    try (
        Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = prepareReadStatement(connection, id);
        ResultSet resultSet = preparedStatement.executeQuery();
    ) {
      oCompany = this.mapper.map(resultSet);
    }
    catch (SQLException e) {
      this.LOG.warn("Couldn't execute select query : " + e.getMessage());
    }
    return oCompany;
  }

  private PreparedStatement prepareReadStatement(Connection connection, Long id)
      throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(this.SELECT_WHEREID);
    preparedStatement.setLong(1, id);
    return preparedStatement;
  }

  public void update(Company company) {
    try (
        Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.UPDATE);
    ) {
      preparedStatement.setString(1, company.getName());
      preparedStatement.setLong(2, company.getId());
      preparedStatement.executeUpdate();
    }
    catch (SQLException e) {
      this.LOG.warn("Couldn't execute update query : " + e.getMessage());
    }

  }

  public Integer count() {
    Integer   count      = null;
    ResultSet mResultSet = null;
    try (
        Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.COUNT);
    ) {
      mResultSet = preparedStatement.executeQuery();
      if (mResultSet.first()) {
        count = mResultSet.getInt("count");
      }
    }
    catch (SQLException e) {
      this.LOG.warn("Couldn't execute count query : " + e.getMessage());
    }
    return count;
  }

  public List<Company> paginatedList(Integer size, Integer offset) {
    List<Company> rCompanyList = null;
    try (
        Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = preparedSelectLimitStatement(connection, size,
            offset);
        ResultSet mResultSet = preparedStatement.executeQuery();
    ) {
      if (size != null && offset != null) {
        rCompanyList = this.mapper.mapList(mResultSet);
      }
    }
    catch (SQLException e) {
      this.LOG.warn("Couldn't execute select in list : " + e.getMessage());
    }
    catch (NullPointerException e) {
      this.LOG.error("An object is null (probably connection) ");
    }
    return rCompanyList;
  }

  private PreparedStatement preparedSelectLimitStatement(Connection connection, Integer size,
      Integer offset) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(this.SELECT_LIMIT);
    preparedStatement.setInt(1, size);
    preparedStatement.setInt(2, offset);
    return preparedStatement;
  }

}

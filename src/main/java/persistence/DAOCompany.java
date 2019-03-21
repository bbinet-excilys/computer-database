package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;

public class DAOCompany {

  /**
   * Logger for the DAOCompany class.
   */
  private final Logger LOG            = LoggerFactory.getLogger(DAOCompany.class);
  private final String SELECT         = "SELECT id, name FROM company;";
  private final String CREATE         = "INSERT INTO company(name) VALUES(?);";
  private final String UPDATE         = "UPDATE company SET name=? WHERE id=?;";
  private final String DELETE         = "DELETE FROM company WHERE id=?;";
  private final String SELECT_WHEREID = "SELECT id, name FROM company WHERE id=?;";
  private final String COUNT          = "SELECT count(*) as count FROM company;";
  /**
   * Set the Mapper on DAOCompany instanciation.
   */
  CompanyMapper        mapper         = new CompanyMapper();

  public boolean create(Company company) {
    try (Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.CREATE);) {
      preparedStatement.setString(1, company.getName());
      preparedStatement.executeUpdate();
      return true;
    }
    catch (SQLException e) {
      this.LOG.error("Error in Create query : " + e.getMessage());
      return false;
    }
  }

  public boolean delete(Company company) {
    try (Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.DELETE);) {
      preparedStatement.setLong(1, company.getId());
      preparedStatement.executeUpdate();
      return true;
    }
    catch (SQLException e) {
      this.LOG.warn("Couldn't execute delete query: " + e.getMessage());
      return false;
    }
  }

  public List<Company> list() {
    ResultSet     mResultSet   = null;
    List<Company> rCompanyList = null;
    try (Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.SELECT);) {
      mResultSet   = preparedStatement.executeQuery();
      rCompanyList = this.mapper.mapList(mResultSet);
    }
    catch (SQLException e) {
      this.LOG.warn("Couldn't execute select query : " + e.getMessage());
    }
    return rCompanyList;
  }

  public Company read(Long id) {
    ResultSet mResultSet = null;
    Company   rCompany   = null;
    try (Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.SELECT_WHEREID);) {
      preparedStatement.setLong(1, id);
      mResultSet = preparedStatement.executeQuery();
      rCompany   = this.mapper.map(mResultSet);
    }
    catch (SQLException e) {
      this.LOG.warn("Couldn't execute select query : " + e.getMessage());
    }
    return rCompany;
  }

  public boolean update(Company company) {
    PreparedStatement mPreparedStatement = null;
    try (Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.UPDATE);) {

      preparedStatement.setString(1, company.getName());
      preparedStatement.setLong(2, company.getId());
      preparedStatement.executeUpdate();
      return true;
    }
    catch (SQLException e) {
      this.LOG.warn("Couldn't execute update query : " + e.getMessage());
      return false;
    }

  }

  public Integer count() {
    Integer   count      = null;
    ResultSet mResultSet = null;
    try (Connection connection = JDBCSingleton.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(this.COUNT);) {
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
    // TODO Auto-generated method stub
    return null;
  }

}

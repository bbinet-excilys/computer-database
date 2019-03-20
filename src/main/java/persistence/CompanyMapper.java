package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;
import model.Entity;

public class CompanyMapper implements Mapper {

  /**
   * Logger for the CompanyMapper class.
   */
  static final Logger LOG = LoggerFactory.getLogger(CompanyMapper.class);

  @Override
  public Company map(ResultSet result) {
    Company rCompany = null;
    try {
      if (result.first()) {
        rCompany = new Company();
        rCompany.setId(result.getInt("id"));
        rCompany.setName(result.getString("name"));
      }
    }
    catch (SQLException e) {
      LOG.warn("Error in mapping : " + e.getMessage());
    }
    catch (IllegalArgumentException e) {
      LOG.warn("An argument used for the creation of the company is invalid : " + e.getMessage());
    }
    return rCompany;
  }

  @Override
  public List<Entity> mapList(ResultSet resultSet) {
    List<Entity> rCompanyList = null;
    try {
      rCompanyList = new ArrayList<Entity>();
      while (resultSet.next()) {
        Company tCompany = new Company();
        tCompany.setId(resultSet.getInt("id"));
        tCompany.setName(resultSet.getString("name"));
        rCompanyList.add(tCompany);
      }
    }
    catch (SQLException e) {
      LOG.warn("Error in mappingList : " + e.getMessage());
    }
    catch (IllegalArgumentException e) {
      LOG.warn("An argument used for the creation of a company is invalid : " + e.getMessage());
    }
    return rCompanyList;
  }

}

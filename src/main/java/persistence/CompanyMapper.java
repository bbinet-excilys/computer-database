package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;
import model.Company.CompanyBuilder;

public class CompanyMapper {

  /**
   * Logger for the CompanyMapper class.
   */
  static final Logger LOG = LoggerFactory.getLogger(CompanyMapper.class);

  public Company map(ResultSet result) {
    Company rCompany = null;
    try {
      if (result.first()) {
        CompanyBuilder companyBuilder = new CompanyBuilder();
        companyBuilder.setId(result.getLong("id"));
        companyBuilder.setName(result.getString("name"));
        rCompany = companyBuilder.build();
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

  public List<Company> mapList(ResultSet resultSet) {
    List<Company> rCompanyList = null;
    try {
      rCompanyList = new ArrayList<Company>();
      while (resultSet.next()) {
        CompanyBuilder companyBuilder = new CompanyBuilder();
        companyBuilder.setId(resultSet.getLong("id"));
        companyBuilder.setName(resultSet.getString("name"));
        rCompanyList.add(companyBuilder.build());
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

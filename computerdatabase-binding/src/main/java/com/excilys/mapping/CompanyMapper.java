package com.excilys.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.CompanyDTO.CompanyDTOBuilder;
import com.excilys.model.Company;
import com.excilys.model.Company.CompanyBuilder;

public class CompanyMapper implements RowMapper<Company> {

  /**
   * Logger for the CompanyMapper class.
   */
  private static final Logger LOG = LoggerFactory.getLogger(CompanyMapper.class);

  public Optional<Company> map(ResultSet result) {
    Optional<Company> oCompany = Optional.empty();
    try {
      if (result.first()) {
        CompanyBuilder companyBuilder = Company.builder();
        companyBuilder.withId(result.getLong("id"));
        companyBuilder.withName(result.getString("name"));
        oCompany = Optional.of(companyBuilder.build());
      }
    }
    catch (SQLException e) {
      LOG.warn("Error in mapping : " + e.getMessage());
    }
    catch (IllegalArgumentException e) {
      LOG.warn("An argument used for the creation of the company is invalid : " + e.getMessage());
    }
    return oCompany;
  }

  public List<Company> mapList(ResultSet resultSet) {
    List<Company> rCompanyList = null;
    try {
      rCompanyList = new ArrayList<>();
      while (resultSet.next()) {
        CompanyBuilder companyBuilder = Company.builder();
        companyBuilder.withId(resultSet.getLong("id"));
        companyBuilder.withName(resultSet.getString("name"));
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

  public static CompanyDTO companyToDTO(Company company) {
    CompanyDTOBuilder cDTOBuilder = CompanyDTO.builder();
    cDTOBuilder.withId(company.getId());
    cDTOBuilder.withName(company.getName());
    return cDTOBuilder.build();
  }

  public static Company companyFromDTO(CompanyDTO companyDTO) {
    CompanyBuilder cBuilder = Company.builder();
    cBuilder.withId(companyDTO.getId());
    cBuilder.withName(companyDTO.getName());
    return cBuilder.build();
  }

  @Override
  public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
    rs.absolute(rowNum + 1);
    CompanyBuilder companyBuilder = Company.builder();
    companyBuilder.withId(rs.getLong("id"));
    companyBuilder.withName(rs.getString("name"));
    return companyBuilder.build();
  }
}

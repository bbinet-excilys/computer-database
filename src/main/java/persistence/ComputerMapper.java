package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;
import model.Company.CompanyBuilder;
import model.Computer;
import model.Computer.ComputerBuilder;

public class ComputerMapper {

  /**
   * Logger for the ComputerMapper Factory.
   */
  static final Logger LOG = LoggerFactory.getLogger(ComputerMapper.class);

  public Computer map(ResultSet resultSet) {
    Computer rComputer = null;
    try {
      if (resultSet.first()) {
        ComputerBuilder computerBuilder = new ComputerBuilder();
        computerBuilder.setId(resultSet.getLong("computer.id"));
        computerBuilder.setName(resultSet.getString("computer.name"));
        computerBuilder.setIntroduced(resultSet.getDate("computer.introduced"));
        computerBuilder.setDiscontinued(resultSet.getDate("computer.discontinued"));
        CompanyBuilder companyBuilder = new CompanyBuilder();
        companyBuilder.setId(resultSet.getLong("company.id"));
        companyBuilder.setName(resultSet.getString("company.name"));
        Company rCompany = companyBuilder.build();
        computerBuilder.setCompany(rCompany);
        rComputer = computerBuilder.build();
      }
    }
    catch (SQLException e) {
      LOG.warn("Error in mapping : " + e.getMessage());
    }
    return rComputer;
  }

  public List<Computer> mapList(ResultSet resultSet) {
    List<Computer> rComputerList = null;
    try {
      rComputerList = new ArrayList<Computer>();
      while (resultSet.next()) {
        ComputerBuilder computerBuilder = new ComputerBuilder();
        computerBuilder.setId(resultSet.getLong("computer.id"));
        computerBuilder.setName(resultSet.getString("computer.name"));
        computerBuilder.setIntroduced(resultSet.getDate("computer.introduced"));
        computerBuilder.setDiscontinued(resultSet.getDate("computer.discontinued"));
        CompanyBuilder companyBuilder = new CompanyBuilder();
        companyBuilder.setId(resultSet.getLong("company.id"));
        companyBuilder.setName(resultSet.getString("company.name"));
        Company rCompany = companyBuilder.build();
        computerBuilder.setCompany(rCompany);
        rComputerList.add(computerBuilder.build());
      }
    }
    catch (SQLException e) {
      LOG.warn("Error in list mapping : " + e.getMessage());
    }
    return rComputerList;
  }

}

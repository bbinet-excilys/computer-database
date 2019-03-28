package mapping;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.ComputerDTO;
import dto.ComputerDTO.ComputerDTOBuilder;
import model.Company;
import model.Company.CompanyBuilder;
import model.Computer;
import model.Computer.ComputerBuilder;

public class ComputerMapper {

  /**
   * Logger for the ComputerMapper Factory.
   */
  static final Logger            LOG            = LoggerFactory.getLogger(ComputerMapper.class);
  static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

  public Optional<Computer> map(ResultSet resultSet) {
    Optional<Computer> oComputer = Optional.empty();
    try {
      if (resultSet.first()) {
        ComputerBuilder computerBuilder = Computer.builder();
        computerBuilder.withId(resultSet.getLong("computer.id"));
        computerBuilder.withName(resultSet.getString("computer.name"));
        computerBuilder.withIntroduced(resultSet.getDate("computer.introduced"));
        computerBuilder.withDiscontinued(resultSet.getDate("computer.discontinued"));
        CompanyBuilder companyBuilder = Company.builder();
        companyBuilder.withId(resultSet.getLong("company.id"));
        companyBuilder.withName(resultSet.getString("company.name"));
        Company rCompany = companyBuilder.build();
        computerBuilder.withCompany(rCompany);
        oComputer = Optional.of(computerBuilder.build());
      }
    }
    catch (SQLException e) {
      LOG.warn("Error in mapping : " + e.getMessage());
    }
    return oComputer;
  }

  public List<Computer> mapList(ResultSet resultSet) {
    List<Computer> rComputerList = null;
    try {
      rComputerList = new ArrayList<Computer>();
      while (resultSet.next()) {
        ComputerBuilder computerBuilder = Computer.builder();
        computerBuilder.withId(resultSet.getLong("computer.id"));
        computerBuilder.withName(resultSet.getString("computer.name"));
        computerBuilder.withIntroduced(resultSet.getDate("computer.introduced"));
        computerBuilder.withDiscontinued(resultSet.getDate("computer.discontinued"));
        CompanyBuilder companyBuilder = Company.builder();
        companyBuilder.withId(resultSet.getLong("company.id"));
        companyBuilder.withName(resultSet.getString("company.name"));
        Company rCompany = companyBuilder.build();
        computerBuilder.withCompany(rCompany);
        rComputerList.add(computerBuilder.build());
      }
    }
    catch (SQLException e) {
      LOG.warn("Error in list mapping : " + e.getMessage());
    }
    return rComputerList;
  }

  public static ComputerDTO computerToDTO(Computer computer) {
    ComputerDTOBuilder cDTOBuilder = ComputerDTO.builder();
    cDTOBuilder.withId(computer.getId());
    cDTOBuilder.withName(computer.getName());
    cDTOBuilder.withIntroduced(DATE_FORMATTER.format(computer.getIntroduced().toLocalDate()));
    cDTOBuilder.withDiscontinued(DATE_FORMATTER.format(computer.getDiscontinued().toLocalDate()));
    cDTOBuilder.withCompanyId(computer.getCompany().getId());
    cDTOBuilder.withCompanyName(computer.getCompany().getName());
    return cDTOBuilder.build();
  }

  public Computer computerFromDTO(ComputerDTO computerDTO) {
    ComputerBuilder cBuilder = Computer.builder();
    cBuilder.withId(computerDTO.getId());
    cBuilder.withName(computerDTO.getName());
    LocalDate ldtIntroduced = LocalDate.parse(computerDTO.getIntroduced(), DATE_FORMATTER);
    Date      introduced    = Date.valueOf(ldtIntroduced);
    cBuilder.withIntroduced(introduced);
    LocalDate ldtDiscontinued = LocalDate.parse(computerDTO.getDiscontinued(),
                                                DATE_FORMATTER);
    Date      discontinued    = Date.valueOf(ldtDiscontinued);
    cBuilder.withDiscontinued(discontinued);

    return null;
  }
}

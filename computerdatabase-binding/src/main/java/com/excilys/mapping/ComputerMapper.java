package com.excilys.mapping;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.validator.routines.DateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import com.excilys.model.Company;
import com.excilys.model.Company.CompanyBuilder;
import com.excilys.model.Computer;
import com.excilys.model.Computer.ComputerBuilder;

public class ComputerMapper {

  /**
   * Logger for the ComputerMapper Factory.
   */
  static final Logger            LOG            = LoggerFactory.getLogger(ComputerMapper.class);
  static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

  public Optional<Computer> mapResultSet(ResultSet resultSet) {
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

  public List<Computer> mapResultSetList(ResultSet resultSet) {
    List<Computer> rComputerList = null;
    try {
      rComputerList = new ArrayList<>();
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
    Optional.ofNullable(computer.getIntroduced())
            .map(Date::toLocalDate)
            .map(LocalDate::toString)
            .ifPresent(date -> cDTOBuilder.withIntroduced(date));
    Optional.ofNullable(computer.getDiscontinued())
            .map(Date::toLocalDate)
            .map(LocalDate::toString)
            .ifPresent(date -> cDTOBuilder.withDiscontinued(date));
    Optional.ofNullable(computer.getCompany())
            .map(Company::getId)
            .ifPresent(id -> cDTOBuilder.withCompanyId(id));
    Optional.ofNullable(computer.getCompany())
            .map(Company::getName)
            .ifPresent(name -> cDTOBuilder.withCompanyName(name));
    return cDTOBuilder.build();
  }

  public static Computer computerFromDTO(ComputerDTO computerDTO) {
    DateValidator   dValidator      = new DateValidator();
    ComputerBuilder computerBuilder = Computer.builder();
    computerBuilder.withId(computerDTO.getId());
    computerBuilder.withName(computerDTO.getName());
    Optional.ofNullable(computerDTO.getIntroduced()).ifPresent(strDate -> {
      Date date = new Date(dValidator.validate(strDate, "yyyy-mm-dd").getTime());
      computerBuilder.withIntroduced(date);
    });
    Optional.ofNullable(computerDTO.getDiscontinued()).ifPresent(strDate -> {
      Date date = new Date(dValidator.validate(strDate, "yyyy-mm-dd").getTime());
      computerBuilder.withDiscontinued(date);
    });
    CompanyBuilder companyBuilder = Company.builder();
    Optional.ofNullable(computerDTO.getCompanyId())
            .ifPresent(companyId -> companyBuilder.withId(companyId));
    Optional.ofNullable(computerDTO.getCompanyName())
            .ifPresent(companyName -> companyBuilder.withName(companyName));
    computerBuilder.withCompany(companyBuilder.build());
    return computerBuilder.build();
  }
}

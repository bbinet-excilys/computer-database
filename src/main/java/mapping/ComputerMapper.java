package mapping;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.validator.routines.DateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import dto.ComputerDTO;
import dto.ComputerDTO.ComputerDTOBuilder;
import model.Company;
import model.Company.CompanyBuilder;
import model.Computer;
import model.Computer.ComputerBuilder;
import service.CompanyService;

public class ComputerMapper implements RowMapper<Computer> {

  private static CompanyService companyService;

  public static void setCompanyService(CompanyService companyService) {
    ComputerMapper.companyService = companyService;
  }

  /**
   * Logger for the ComputerMapper Factory.
   */
  static final Logger LOG = LoggerFactory.getLogger(ComputerMapper.class);

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
    Optional.ofNullable(computerDTO.getIntroduced())
            .filter(Predicate.not(String::isBlank))
            .ifPresent(strDate -> {
              Date date = new Date(dValidator.validate(strDate, "yyyy-MM-dd").getTime());
              computerBuilder.withIntroduced(date);
            });
    Optional.ofNullable(computerDTO.getDiscontinued())
            .filter(Predicate.not(String::isBlank))
            .ifPresent(strDate -> {
              Date date = new Date(dValidator.validate(strDate, "yyyy-MM-dd").getTime());
              computerBuilder.withDiscontinued(date);
            });

    Optional<Long> oCompanyId = Optional.ofNullable(computerDTO.getCompanyId());
    if (oCompanyId.isPresent()) {
      companyService.read(oCompanyId.get()).ifPresent(companyDTO -> computerBuilder.withCompany(CompanyMapper.companyFromDTO(companyDTO)));
    }

    return computerBuilder.build();
  }

  @Override
  public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
    rs.absolute(rowNum + 1);
    ComputerBuilder computerBuilder = Computer.builder();
    computerBuilder.withId(rs.getLong("computer.id"));
    computerBuilder.withName(rs.getString("computer.name"));
    computerBuilder.withIntroduced(rs.getDate("computer.introduced"));
    computerBuilder.withDiscontinued(rs.getDate("computer.discontinued"));
    CompanyBuilder companyBuilder = Company.builder();
    companyBuilder.withId(rs.getLong("company.id"));
    companyBuilder.withName(rs.getString("company.name"));
    Company rCompany = companyBuilder.build();
    computerBuilder.withCompany(rCompany);
    return computerBuilder.build();
  }
}

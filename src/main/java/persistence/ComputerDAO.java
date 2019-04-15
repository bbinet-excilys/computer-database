package persistence;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import mapping.ComputerMapper;
import model.Company;
import model.Computer;

public class ComputerDAO {

  private final String SELECT_NAME    = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer LEFT OUTER JOIN company ON computer.company_id=company.id WHERE computer.name like :name OR company.name like :name;";
  private final String SELECT         = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer LEFT OUTER JOIN company ON computer.company_id=company.id;";
  private final String CREATE         = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(:name,:introduced,:discontinued,:companyId);";
  private final String UPDATE         = "UPDATE computer SET name=:name, introduced=:introduced, discontinued=:discontinued, company_id=:companyId WHERE id=:id;";
  private final String DELETE         = "DELETE FROM computer WHERE id=:id;";
  private final String SELECT_WHEREID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer LEFT OUTER JOIN company on computer.company_id=company.id WHERE computer.id=:id;";
  private final String DELETE_COMPANY = "DELETE FROM computer WHERE company_id = :id;";

  private NamedParameterJdbcTemplate jdbcTemplate;
  private ComputerMapper             computerMapper;

  private final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

  public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void setComputerMapper(ComputerMapper computerMapper) {
    this.computerMapper = computerMapper;
  }

  public void create(Computer computer) {
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(computer);
    jdbcTemplate.update(CREATE, parameters);
  }

  public void delete(Computer computer) {
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(computer);
    jdbcTemplate.update(DELETE, parameters);
  }

  public List<Computer> list() {
    return jdbcTemplate.query(SELECT, computerMapper);
  }

  public Computer read(Long id) {
    SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
    return jdbcTemplate.queryForObject(SELECT_WHEREID, parameters, computerMapper);
  }

  public void update(Computer computer) {
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(computer);
    jdbcTemplate.update(UPDATE, parameters);
  }

  public List<Computer> searchByName(String name) {
    SqlParameterSource parameters = new MapSqlParameterSource().addValue("name", name);
    return jdbcTemplate.query(SELECT_NAME, parameters, computerMapper);
  }

  public void deleteCompany(Company company) {
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(company);
    LOGGER.debug(company.toString());
    jdbcTemplate.update(DELETE_COMPANY, parameters);
  }
}

package persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.querydsl.jpa.impl.JPAQueryFactory;

import mapping.ComputerMapper;
import model.Company;
import model.Computer;
import model.QComputer;

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
  private SessionFactory             sessionFactory;

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

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
    QComputer       qComputer    = QComputer.computer;
    JPAQueryFactory queryFactory = new JPAQueryFactory(sessionFactory.createEntityManager());
    List<Computer>  computersjpa = queryFactory.selectFrom(qComputer).fetchAll().fetch();
    LOGGER.debug(computersjpa.size() + "");
    return computersjpa;
  }

  public Optional<Computer> read(Long id) {
    SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
    Optional<Computer> rComputer;
    try {
      rComputer = Optional.of(jdbcTemplate.queryForObject(SELECT_WHEREID, parameters,
                                                          computerMapper));
    }
    catch (EmptyResultDataAccessException e) {
      LOGGER.debug("No matching company in database");
      rComputer = Optional.empty();
    }
    return rComputer;
  }

  public void update(Computer computer) {
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(computer);
    logParameters(parameters);
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

  private void logParameters(SqlParameterSource parameters) {
    String[]       names      = parameters.getParameterNames();
    Stream<String> nameStream = Stream.of(names);
    nameStream.forEach(name -> {
      LOGGER.debug(String.format("\tName %20s | Type %20s | Value %30s ", name, parameters.getSqlType(name), parameters.getValue(name)));
    });
  }
}

package persistence;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import mapping.CompanyMapper;
import model.Company;

public class CompanyDAO {

  private final Logger LOGGER         = LoggerFactory.getLogger(CompanyDAO.class);
  private final String SELECT         = "SELECT id, name FROM company;";
  private final String UPDATE         = "UPDATE company SET name=:name WHERE id=:id;";
  private final String DELETE         = "DELETE FROM company WHERE id=:id;";
  private final String SELECT_WHEREID = "SELECT id, name FROM company WHERE id=:id;";

  private NamedParameterJdbcTemplate jdbcTemplate;
  private CompanyMapper              companyMapper;

  public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void setCompanyMapper(CompanyMapper companyMapper) {
    this.companyMapper = companyMapper;
  }

  public List<Company> list() {
    return jdbcTemplate.query(SELECT, companyMapper);
  }

  public Optional<Company> read(Long id) {
    SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
    Optional<Company>  rCompany;
    try {
      rCompany = Optional.of(jdbcTemplate.queryForObject(SELECT_WHEREID, parameters,
                                                         companyMapper));
    }
    catch (EmptyResultDataAccessException e) {
      LOGGER.debug("No matching company in database");
      rCompany = Optional.empty();
    }
    return rCompany;
  }

  public void update(Company company) {
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(company);
    jdbcTemplate.update(UPDATE, parameters);
  }

  public void delete(Company company) {
    SqlParameterSource parameters = new BeanPropertySqlParameterSource(company);
    jdbcTemplate.update(DELETE, parameters);
  }

}

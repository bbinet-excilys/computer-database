package model;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bbinet Model class for db computer entity
 */
public class Computer extends Entity {

  /**
   * The name of the computer.
   */
  private String  name;
  /**
   * Date of introduction of the computer.
   */
  private Date    introduced;
  /**
   * Date of discontinuation of the computer.
   */
  private Date    discontinued;
  /**
   * ID of the company the computer belongs to.
   */
  private Integer companyId;
  /**
   * Company object corresponding to the companyId field.
   */
  private Company company;

  static final Logger LOG = LoggerFactory.getLogger(Computer.class);

  /**
   * Parametered constructor.
   *
   * @param id
   * @param name
   * @param introduced
   * @param discontinued
   * @param companyId
   * @param company
   */
  public Computer(Integer id, String name, Date introduced, Date discontinued, Integer companyId, Company company) {
    super();
    setId(id);
    if (name != null) {
      this.name = (name.trim().isEmpty()) ? null : name.trim();
    }
    else {
      this.name = name;
    }
    if (introduced != null && discontinued != null) {
      if (introduced.before(discontinued)) {
        this.introduced   = introduced;
        this.discontinued = discontinued;
      }
      else {
        LOG.warn("Discontinuation date cannot be before introduction date : introduced before discontinued :"
            + introduced.before(discontinued));
        this.introduced   = null;
        this.discontinued = null;
      }
    }
    else if (introduced == null && discontinued != null) {
      LOG.warn("There can't be a discontinuation date without an introduction date");
      this.introduced   = null;
      this.discontinued = null;
    }
    else {
      this.introduced   = introduced;
      this.discontinued = discontinued;
    }
    if (companyId != null) {
      this.companyId = (companyId < 0) ? null : companyId;
    }
    else {
      this.companyId = companyId;
    }
    this.company = company;
  }

  /**
   * Base constructor.
   */
  public Computer() {
  }

  /**
   * @return The Company object the Computer belongs to
   */
  public Company getCompany() {
    return this.company;
  }

  /**
   * @return The ID of the Company the computer belongs to
   */
  public Integer getCompanyId() {
    return this.companyId;
  }

  /**
   * @return The java.sql.Date object of discontinuation of the Computer
   */
  public Date getDiscontinued() {
    return this.discontinued;
  }

  /**
   * @return The java.sql.Date object of introduction of the Computer
   */
  public Date getIntroduced() {
    return this.introduced;
  }

  /**
   * @return The name of the Computer
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param company
   *                The Company object the Computer belongs to
   */
  public void setCompany(Company company) {
    this.company = company;
  }

  /**
   * @param companyId
   *                  The ID of the Company the computer belongs to
   */
  public void setCompanyId(Integer companyId) {
    if (companyId != null) {
      this.companyId = companyId;
    }
  }

  /**
   * @param discontinued
   *                     The java.sql.Date object of discontinuation of the
   *                     Computer
   */
  public void setDiscontinued(Date discontinued) {
    if (discontinued != null) {
      this.discontinued = discontinued;
    }
  }

  /**
   * @param introduced
   *                   The java.sql.Date object of introduction of the Computer
   */
  public void setIntroduced(Date introduced) {
    if (introduced != null) {
      this.introduced = introduced;
    }
  }

  /**
   * @param name
   *             The name of the Computer
   */
  public void setName(String name) {
    if (name != null) {
      this.name = name;
    }
  }

  @Override
  public String toString() {
    return String.format("%5s | %30.30s | %10s | %10s | %s", getId(), this.name, this.introduced, this.discontinued,
        (this.company != null) ? this.company.toString() : String.format("%5s", "null"));
  }

}

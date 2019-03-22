package model;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bbinet Model class for db computer entity
 */
public class Computer extends Entity {

  /**
   * Date of introduction of the computer.
   */
  private Date    introduced;
  /**
   * Date of discontinuation of the computer.
   */
  private Date    discontinued;
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
   * @param company
   */
  public Computer(Long id, String name, Date introduced, Date discontinued, Company company) {
    super();
    this.id = id;
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
        this.introduced   = null;
        this.discontinued = null;
      }
    }
    else if (introduced == null && discontinued != null) {
      this.introduced   = null;
      this.discontinued = null;
    }
    else {
      this.introduced   = introduced;
      this.discontinued = discontinued;
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
   * @param company
   *                The Company object the Computer belongs to
   */
  public void setCompany(Company company) {
    this.company = company;
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

  @Override
  public String toString() {
    return String.format("%5s | %30.30s | %10s | %10s | %s", getId(), this.name, this.introduced,
        this.discontinued,
        (this.company != null) ? this.company.toString() : String.format("%5s", "null"));
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Computer)) {
      return false;
    }
    else {
      Computer computer = (Computer) obj;
      return getId() == computer.getId() && getName() == computer.getName()
          && getIntroduced() == computer.getIntroduced()
          && getDiscontinued() == computer.getDiscontinued()
          && getCompany().equals(computer.getCompany());
    }
  }

  @Override
  public int hashCode() {
    return getId().hashCode() + getName().hashCode() + getIntroduced().hashCode()
        + getDiscontinued().hashCode() + getCompany().hashCode();
  }

  public static class ComputerBuilder {
    private Long    id;
    private String  name;
    private Date    introduced;
    private Date    discontinued;
    private Company company;

    public void setId(Long id) {
      this.id = id;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setIntroduced(Date introduced) {
      this.introduced = introduced;
    }

    public void setDiscontinued(Date discontinued) {
      this.discontinued = discontinued;
    }

    public void setCompany(Company company) {
      this.company = company;
    }

    public Computer build() {
      return new Computer(this.id, this.name, this.introduced, this.discontinued, this.company);
    }

  }
}

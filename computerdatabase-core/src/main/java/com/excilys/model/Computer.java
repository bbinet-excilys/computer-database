package com.excilys.model;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bbinet Model class for db computer entity
 */
public class Computer {
  protected Long id;

  /**
   * The name of the computer.
   */
  protected String name;

  /**
   * @return The Entity ID
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *           The Entity ID to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return The name of the Computer
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *             The name of the Entity
   */
  public void setName(String name) {
    if (name != null) {
      this.name = name;
    }
  }

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

  private Computer(ComputerBuilder builder) {
    id           = builder.id;
    name         = builder.name;
    introduced   = builder.introduced;
    discontinued = builder.discontinued;
    company      = builder.company;
  }

  /**
   * Base constructor.
   */
  public Computer() {}

  public Date getIntroduced() {
    return introduced;
  }

  public void setIntroduced(Date introduced) {
    this.introduced = introduced;
  }

  public Date getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(Date discontinued) {
    this.discontinued = discontinued;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public Long getCompanyId() {
    return company.getId();
  }

  @Override
  public String toString() {
    return String
                 .format("%5s | %30.30s | %10s | %10s | %s", getId(), name, introduced,
                         discontinued,
                         (company != null) ? company.toString()
                             : String.format("%5s", "null"));
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

  public static ComputerBuilder builder() {
    return new ComputerBuilder();
  }

  public static final class ComputerBuilder {
    private Long    id;
    private String  name;
    private Date    introduced;
    private Date    discontinued;
    private Company company;

    private ComputerBuilder() {}

    public ComputerBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public ComputerBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public ComputerBuilder withIntroduced(Date introduced) {
      this.introduced = introduced;
      return this;
    }

    public ComputerBuilder withDiscontinued(Date discontinued) {
      this.discontinued = discontinued;
      return this;
    }

    public ComputerBuilder withCompany(Company company) {
      this.company = company;
      return this;
    }

    public Computer build() {
      return new Computer(this);
    }
  }

}

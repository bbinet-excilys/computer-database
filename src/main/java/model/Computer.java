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

  private Computer(ComputerBuilder builder) {
    this.id           = builder.id;
    this.name         = builder.name;
    this.introduced   = builder.introduced;
    this.discontinued = builder.discontinued;
    this.company      = builder.company;
  }

  /**
   * Parametered constructor.
   *
   * @param id
   * @param name
   * @param introduced
   * @param discontinued
   * @param company
   */

  /**
   * Base constructor.
   */
  public Computer() {}

  public Date getIntroduced() {
    return this.introduced;
  }

  public void setIntroduced(Date introduced) {
    this.introduced = introduced;
  }

  public Date getDiscontinued() {
    return this.discontinued;
  }

  public void setDiscontinued(Date discontinued) {
    this.discontinued = discontinued;
  }

  public Company getCompany() {
    return this.company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  @Override
  public String toString() {
    return String
                 .format("%5s | %30.30s | %10s | %10s | %s", getId(), this.name, this.introduced,
                         this.discontinued,
                         (this.company != null) ? this.company.toString()
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

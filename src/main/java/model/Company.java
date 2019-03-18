package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Model class for database company entity
 *
 * @author bbinet
 */
public class Company extends Entity {

  /**
   * The Name of the Company.
   */
  private String name;

  static final Logger LOG = LoggerFactory.getLogger(Company.class);

  /**
   * Basic parametered constructor.
   *
   * @param id
   *             The id of the Company
   * @param name
   *             The name of the company to create
   */
  public Company(Integer id, String name) {
    super();
    setId(id);
    setName(name);
  }

  /**
   * Base constructor.
   */
  public Company() {
  }

  /**
   * @return The Name of the Company
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name
   *             The name of the Company to set
   */
  public void setName(String name) {
    if (name != null) {
      if (name.trim().isEmpty()) {
        LOG.warn("Tried to set the name of the company to an empty string");
        throw new IllegalArgumentException();
      }
      this.name = name.trim();
    }
    else {
      this.name = null;
    }
  }

  @Override
  public String toString() {
    return String.format("%5s | %30s", getId(), getName());
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Company)) {
      return false;
    }
    else {
      Company company = (Company) obj;
      return getId() == company.getId() && getName() == company.getName();
    }
  }

  @Override
  public int hashCode() {
    // TODO Auto-generated method stub
    return getId().hashCode() + getName().hashCode();
  }

}

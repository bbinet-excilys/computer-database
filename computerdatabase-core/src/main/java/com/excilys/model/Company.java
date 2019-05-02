package com.excilys.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Model class for database company entity
 *
 * @author bbinet
 */
public class Company {
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

  static final Logger LOG = LoggerFactory.getLogger(Company.class);

  public Company() {}

  private Company(CompanyBuilder builder) {
    id   = builder.id;
    name = builder.name;
  }

  public Company(Long id, String name) {
    super();
    this.id   = id;
    this.name = name;
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
    return getId().hashCode() + getName().hashCode();
  }

  public static CompanyBuilder builder() {
    return new CompanyBuilder();
  }

  public static final class CompanyBuilder {
    private Long   id;
    private String name;

    private CompanyBuilder() {}

    public CompanyBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public CompanyBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public Company build() {
      return new Company(this);
    }
  }

}

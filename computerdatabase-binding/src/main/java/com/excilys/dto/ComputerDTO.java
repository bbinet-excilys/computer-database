package com.excilys.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ComputerDTO {
  private Long   id;
  private String name;
  private String introduced;
  private String discontinued;
  private Long   companyId;
  private String companyName;

  private ComputerDTO(ComputerDTOBuilder builder) {
    id           = builder.id;
    name         = builder.name;
    introduced   = builder.introduced;
    discontinued = builder.discontinued;
    companyId    = builder.companyId;
    companyName  = builder.companyName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIntroduced() {
    return introduced;
  }

  public void setIntroduced(String introduced) {
    this.introduced = introduced;
  }

  public String getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(String discontinued) {
    this.discontinued = discontinued;
  }

  public Long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(Long companyId) {
    this.companyId = companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public static ComputerDTOBuilder builder() {
    return new ComputerDTOBuilder();
  }

  public static final class ComputerDTOBuilder {
    private Long   id;
    private String name;
    private String introduced;
    private String discontinued;
    private Long   companyId;
    private String companyName;

    private ComputerDTOBuilder() {}

    public ComputerDTOBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public ComputerDTOBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public ComputerDTOBuilder withIntroduced(String introduced) {
      this.introduced = introduced;
      return this;
    }

    public ComputerDTOBuilder withDiscontinued(String discontinued) {
      this.discontinued = discontinued;
      return this;
    }

    public ComputerDTOBuilder withCompanyId(Long companyId) {
      this.companyId = companyId;
      return this;
    }

    public ComputerDTOBuilder withCompanyName(String companyName) {
      this.companyName = companyName;
      return this;
    }

    public ComputerDTO build() {
      return new ComputerDTO(this);
    }
  }

}

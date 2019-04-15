package dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import ui.EntityToStringStyle;

public class CompanyDTO {

  private Long   id;
  private String name;

  private CompanyDTO(CompanyDTOBuilder builder) {
    id   = builder.id;
    name = builder.name;
  }

  public long getId() {
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

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, new EntityToStringStyle());
  }

  public static CompanyDTOBuilder builder() {
    return new CompanyDTOBuilder();
  }

  public static final class CompanyDTOBuilder {
    private Long   id;
    private String name;

    private CompanyDTOBuilder() {}

    public CompanyDTOBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public CompanyDTOBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public CompanyDTO build() {
      return new CompanyDTO(this);
    }
  }

}

package dto;

public class CompanyDTO {

  private long   id;
  private String name;

  private CompanyDTO(CompanyDTOBuilder builder) {
    this.id   = builder.id;
    this.name = builder.name;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static CompanyDTOBuilder builder() {
    return new CompanyDTOBuilder();
  }

  public static final class CompanyDTOBuilder {
    private long   id;
    private String name;

    private CompanyDTOBuilder() {}

    public CompanyDTOBuilder withId(long id) {
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

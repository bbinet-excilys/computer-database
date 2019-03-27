package dto;

public class ComputerDTO {
  private long   id;
  private String name;
  private String introduced;
  private String discontinued;
  private long   companyId;
  private String companyName;

  private ComputerDTO(ComputerDTOBuilder builder) {
    id           = builder.id;
    name         = builder.name;
    introduced   = builder.introduced;
    discontinued = builder.discontinued;
    companyId    = builder.companyId;
    companyName  = builder.companyName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  public long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(long companyId) {
    this.companyId = companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public static ComputerDTOBuilder builder() {
    return new ComputerDTOBuilder();
  }

  public static final class ComputerDTOBuilder {
    private long   id;
    private String name;
    private String introduced;
    private String discontinued;
    private long   companyId;
    private String companyName;

    private ComputerDTOBuilder() {}

    public ComputerDTOBuilder withId(long id) {
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

    public ComputerDTOBuilder withCompanyId(long companyId) {
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

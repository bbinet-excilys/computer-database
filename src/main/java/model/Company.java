package model;

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
        throw new IllegalArgumentException();
      }
    }
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("%5s | %30s", getId(), getName());
  }

}

package model;

/**
 * Base class for database entity.
 *
 * @author bbinet
 */
public abstract class Entity {
  /**
   * The id of the entity in the database.
   */
  protected Long id;

  /**
   * The name of the computer.
   */
  protected String name;

  /**
   * @return The Entity ID
   */
  public Long getId() {
    return this.id;
  }

  /**
   * @param id
   *           The Entity ID to set
   */
  public void setId(Long id) {
    if (id != null) {
      if (id < 0) {
        throw new IllegalArgumentException("The id of an Entity should be positive (>0)");
      }
      else {
        this.id = id;
      }
    }
    else {
      throw new IllegalArgumentException("The Id of an Entity shouldn't be null");
    }
  }

  /**
   * @return The name of the Computer
   */
  public String getName() {
    return this.name;
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
   * Basic toString override to force subclasses to implement it.
   */
  @Override
  public abstract String toString();

}

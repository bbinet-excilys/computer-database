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
  private Integer id;

  /**
   * @return The Entity ID
   */
  public Integer getId() {
    return this.id;
  }

  /**
   * @param id
   *           The Entity ID to set
   */
  public void setId(Integer id) {
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
   * Basic toString override to force subclasses to implement it.
   */
  @Override
  public abstract String toString();

}

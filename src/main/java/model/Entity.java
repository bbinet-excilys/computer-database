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
  Integer id;

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
    this.id = id;
  }

  /**
   * Basic toString override.
   */
  @Override
  public abstract String toString();

}

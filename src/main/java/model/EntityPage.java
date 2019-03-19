package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Pagination class for database entity.
 *
 * @author bbinet
 * @param <T>
 *        Class of the entity (must extend Entity)
 */
public abstract class EntityPage<T extends Entity> {

  /**
   * The number of elements a page displays.
   */
  private Integer pageSize;
  /**
   * The current offset of the page.
   */
  private Integer offset = 0;
  /**
   * The list of entities to paginate.
   */
  private List<T> entities;

  public EntityPage(Integer pageSize, Integer offset, List<T> entities) {
    super();
    setPageSize(pageSize);
    setOffset(offset);
    setEntities(entities);
  }

  public EntityPage() {
    super();
  }

  /**
   * @return The list of entities paginated
   */
  public List<T> getEntities() {
    return this.entities;
  }

  /**
   * @return The current offset in the List of Entities
   */
  public Integer getOffset() {
    return this.offset;
  }

  /**
   * @return The number of the current "page" (starting at 1)
   */
  public int getPage() {
    return 1 + ((this.offset % this.entities.size() + this.entities.size()) % this.entities.size()) / this.pageSize;
  }

  /**
   * @param direction
   *                  Whether the page to display will be :
   *                  <ul>
   *                  <li>0 : the current page</li>
   *                  <li>1 : the next page</li>
   *                  <li>-1 : the previous page</li>
   *                  </ul>
   * @return The List of Entities to display
   */
  public List<T> getPage(int direction) {
    List<T> rEntityList = new ArrayList<T>();
    this.offset = this.offset + (direction * this.pageSize);
    int index = (this.offset % this.entities.size() + this.entities.size()) % this.entities.size();
    for (int i = 0; i < this.pageSize; i++) {
      rEntityList.add(this.entities.get(index));
      index = (index + 1) % this.entities.size();
    }
    return rEntityList;
  }

  public abstract List<T> getPageN(int page);

  /**
   * @return The size of a page (number of elements displayed at the same time)
   */
  public Integer getPageSize() {
    return this.pageSize;
  }

  /**
   * @param entities
   *                 The list of entities to paginate
   */
  public void setEntities(List<T> entities) {
    if (entities == null) {
      throw new IllegalArgumentException("List of Entities can't be null");
    }
    else if (entities.size() == 0) {
      throw new IllegalArgumentException("List of Entities should contain at least one element");
    }
    this.entities = entities;
  }

  /**
   * @param offset
   *               The current offset in the List of Entities
   */
  public void setOffset(Integer offset) {
    if (offset == null) {
      throw new IllegalArgumentException("Offset can't be null");
    }
    this.offset = offset;
  }

  /**
   * @param pageSize
   *                 The size of a page (number of elements displayed at the same
   *                 time)
   */
  public void setPageSize(Integer pageSize) {
    if (pageSize == null) {
      throw new IllegalArgumentException("PageSize can't be null");
    }
    else if (pageSize < 1) {
      throw new IllegalArgumentException("PageSize must be at least 1");
    }
    this.pageSize = pageSize;
  }

}

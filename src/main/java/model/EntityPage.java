package model;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author bbinet Pagination class for database entity
 * @param <T> Class of the entity (must extend Entity)
 */
public class EntityPage<T extends Entity> {

    Integer pageSize, offset;
    List<T> entities;

    /**
     * 
     * @return The size of a page (number of elements displayed at the same time)
     */
    public Integer getPageSize() {
        return this.pageSize;
    }

    /**
     * 
     * @param pageSize The size of a page (number of elements displayed at the same
     *                 time)
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 
     * @return The current offset in the List of Entities
     */
    public Integer getOffset() {
        return this.offset;
    }

    /**
     * 
     * @param offset The current offset in the List of Entities
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * 
     * @return The list of entities paginated
     */
    public List<T> getEntities() {
        return this.entities;
    }

    /**
     * 
     * @param entities The list of entities to paginate
     */
    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    /**
     * 
     * @return The number of the current "page" (starting at 1)
     */
    public int getPage() {
        return 1 + ((this.offset % this.entities.size() + this.entities.size()) % this.entities.size()) / this.pageSize;
    }

    /**
     * 
     * @param direction Whether the page to display will be :
     * <ul>
     *      <li> 0 : the current page</li>
     *      <li> 1 : the next page</li>
     *      <li>-1 : the previous page</li>
     * </ul>
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

}

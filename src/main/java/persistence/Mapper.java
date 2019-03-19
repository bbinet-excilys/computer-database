package persistence;

import java.sql.ResultSet;
import java.util.List;

import model.Entity;

/**
 * Base class for Entity Mappers.
 */

//TODO: Create a Mapper factory to remove calls to 'new' in classes.
public interface Mapper<T extends Entity> {

  /**
   * Basic method to map a single line result set to an object.
   *
   * @return An instance of the mapped object.
   */
  public T map(ResultSet resultSet);

  /**
   * Maps a result set to a list of objects.
   *
   * @return A List instance containing the mapped objects.
   */
  public List<T> mapList(ResultSet resultSet);

}

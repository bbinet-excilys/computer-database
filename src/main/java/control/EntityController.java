package control;

import java.util.List;

import model.Entity;
import persistence.DAO;
import ui.EntityUI;

/**
 * Base class for entity controller contains CRUD and list/pagedlist methods. In
 * charge of calling View and Model methods (UI and DAO).
 *
 * @param <T>
 *        The type of entity used by the Controller, must extend Entity.
 * @author bbinet
 */
public abstract class EntityController implements Controller {

  /**
   * The dao of the Controller.
   */
  DAO      dao;
  /**
   * The UI manager object for the Entity.
   */
  EntityUI entityUI = new EntityUI();

  /**
   * Basic create method.
   */
  public abstract void create();

  /**
   * Basic delete method.
   */
  public abstract void delete();

  /**
   * Behavior for printing the whole list of entities.
   */
  public void list() {
    List<Entity> entityList = this.dao.list();
    this.entityUI.printList(entityList);
  }

  /**
   * Manages paged lists behavior.
   */
//  public void pagedList() {
//    Integer    size            = UIHelper.promptInt("How many items per page ?");
//    EntityPage pagedEntityList = new EntityPage();
//    pagedEntityList.setEntities(this.dao.list());
//    pagedEntityList.setPageSize(size);
//    pagedEntityList.setOffset(0);
//    List<T> entityList = pagedEntityList.getPage(0);
//    do {
//      this.entityUI.printList(entityList);
//      int direction = UIHelper.promptPage(pagedEntityList.getPage());
//      if (direction == 0) {
//        break;
//      }
//      entityList = pagedEntityList.getPage(direction);
//    } while (true);
//  }

  /**
   * Basic read method.
   */
  public abstract void read();

  /**
   * Basic update method.
   */
  public abstract void update();

  public abstract void pagedList();
}

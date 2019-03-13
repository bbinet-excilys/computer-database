package control;

import java.util.List;

import model.Entity;
import model.EntityPage;
import persistence.DAO;
import ui.EntityUI;
import ui.UIHelper;

/**
 *
 * Base class for entity controller contains CRUD and list/pagedlist methods. In
 * charge of calling View and Model methods (UI and DAO).
 *
 * @param <T> The type of entity used by the Controller, must extend Entity.
 *
 * @author bbinet
 */
public abstract class EntityController<T extends Entity> implements Controller {

    /**
     * The dao of the Controller.
     */
    DAO<T>      dao;
    /**
     * The UI manager object for the Entity.
     */
    EntityUI<T> entityUI = new EntityUI<T>();

    /**
     * Basic create method.
     */
    public abstract void create();

    /**
     * Basic read method.
     */
    public abstract void read();

    /**
     * Basic update method.
     */
    public abstract void update();

    /**
     * Basic delete method.
     */
    public abstract void delete();

    /**
     * Behavior for printing the whole list of entities.
     */
    public void list() {
        List<T> entityList = this.dao.list();
        this.entityUI.printList(entityList);
    }

    /**
     * Manages paged lists behavior.
     */
    public void pagedList() {
        Integer       size            = UIHelper.promptInt("How many items per page ?");
        EntityPage<T> pagedEntityList = new EntityPage<T>();
        pagedEntityList.setEntities(this.dao.list());
        pagedEntityList.setPageSize(size);
        pagedEntityList.setOffset(0);
        List<T> entityList = pagedEntityList.getPage(0);
        do {
            this.entityUI.printList(entityList);
            int direction = UIHelper.promptPage(pagedEntityList.getPage());
            if (direction == 0)
                break;
            entityList = pagedEntityList.getPage(direction);
        } while (true);
    }

}

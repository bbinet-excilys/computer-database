package com.excilys.cdb.main.control;

import java.util.List;

import com.excilys.cdb.main.model.Entity;
import com.excilys.cdb.main.model.EntityPage;
import com.excilys.cdb.main.persistence.DAO;
import com.excilys.cdb.main.ui.EntityUI;
import com.excilys.cdb.main.ui.UIHelper;

/**
 * 
 * Base class for entity controller
 * contains CRUD and list/pagedlist methods
 *
 * @param <T> The type of Entity used by the Controller
 *
 * @author bbinet
 */
public abstract class EntityController<T extends Entity> {

    DAO<T> dao;
    EntityUI<T> entityUI = new EntityUI<T>();

    public abstract void create();

    public abstract void read();

    public abstract void update();

    public abstract void delete();

    /**
     * Prints the whole list of entity
     */
    public void list() {
        List<T> entityList = this.dao.list();
        this.entityUI.printList(entityList);
    }
    
    /**
     * Manages Input and Output
     */
    public void pagedList() {
        Integer        size          = UIHelper.promptInt("How many items per page ?");
        EntityPage<T> pagedEntityList = new EntityPage<T>();
        pagedEntityList.setEntities(this.dao.list());
        pagedEntityList.setPageSize(size);
        pagedEntityList.setOffset(0);
        List<T> entityList = pagedEntityList.getPage(0);
        do {
            this.entityUI.printList(entityList);
            int direction = UIHelper.promptPage(pagedEntityList.getPage());
            if(direction == 0)
                break;
            entityList = pagedEntityList.getPage(direction);
        }while(true);
    }

}

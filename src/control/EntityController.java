package control;

import java.sql.Connection;
import java.util.List;

import model.Computer;
import model.Entity;
import model.EntityPage;
import persistence.DAO;
import ui.EntityUI;
import ui.UIHelper;

public abstract class EntityController<T extends Entity> {

    DAO<T> dao;
    EntityUI<T> entityUI = new EntityUI<T>();

    public abstract void create();

    public abstract void read();

    public abstract void update();

    public abstract void delete();

    public void list() {
        List<T> entityList = this.dao.list();
        this.entityUI.printList(entityList);
    }
    
    public void pagedList() {
        Integer        size          = UIHelper.promptInt("How many items per page ?");
        EntityPage<T> pagedEntityList = new EntityPage<T>();
        pagedEntityList.setEntities(this.dao.list());
        pagedEntityList.setPageSize(size);
        pagedEntityList.setOffset(0);
        List<T> entityList = pagedEntityList.getPage(0);
        do {
            this.entityUI.printList(entityList);
            int direction = UIHelper.promptPage(pagedEntityList.getOffset());
            if(direction == 0)
                break;
            entityList = pagedEntityList.getPage(direction);
        }while(true);
    }

}

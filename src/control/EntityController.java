package control;

import java.sql.Connection;

import persistence.DAO;

public abstract class EntityController<T> {

    DAO<T> dao;

    public abstract void create();

    public abstract void read();

    public abstract void update();

    public abstract void delete();

    public abstract void list();

}

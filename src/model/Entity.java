package model;

public abstract class Entity {
    
    private Integer id;
    
    public abstract String toString();
    
    public abstract Integer getId();
    
    public abstract void setId(Integer id);

}

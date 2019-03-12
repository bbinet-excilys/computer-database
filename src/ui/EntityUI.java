package ui;

import java.util.List;

import model.Computer;
import model.Entity;

public class EntityUI<T extends Entity> {
    
    public void print(T object) {
        System.out.println(object.toString());
    }
    
    public void printList(List<T> objectList) {
        for(T tObject : objectList) {
            print(tObject);
        }
    }
    
}

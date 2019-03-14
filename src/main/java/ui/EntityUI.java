package ui;

import java.util.List;

import model.Entity;

/**
 *
 * UserInterface class for entity.
 *
 * @param <T> The type of Entity to create.
 *
 * @author bbinet
 */
public class EntityUI<T extends Entity> {

    /**
     * Prints an Object extending Entity. Calls its toString method.
     *
     * @param object The object to print.
     */
    public void print(T object) {
        System.out.println(object.toString());
    }

    /**
     * Prints a list of entities.
     *
     * @param objectList The list of objects to print. These object must extend
     *                   Entity.
     */
    public void printList(List<T> objectList) {
        for (T tObject : objectList) {
            print(tObject);
        }
    }

}
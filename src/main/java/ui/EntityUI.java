package ui;

import java.util.List;

import model.Company;
import model.Computer;
import model.Entity;

/**
 * UserInterface class for entity.
 *
 * @param <T>
 *        The type of Entity to create.
 * @author bbinet
 */
public class EntityUI {

  /**
   * Prints an Object extending Entity. Calls its toString method.
   *
   * @param object
   *               The object to print.
   */
  public static void print(Entity object) {
    System.out.println(object.toString());
  }

  /**
   * Prints a list of entities.
   *
   * @param objectList
   *                   The list of objects to print. These object must extend
   *                   Entity.
   */
  public void printList(List<Entity> objectList) {
    for (Entity tObject : objectList) {
      print(tObject);
    }
  }

  public static void printComputerList(List<Computer> computerList) {
    computerList.stream().forEach(EntityUI::print);
  }

  public static void printCompanyList(List<Company> companylist) {
    companylist.stream().forEach(EntityUI::print);
  }

}

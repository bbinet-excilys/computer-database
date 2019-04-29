package ui;

import java.util.List;

import dto.CompanyDTO;
import dto.ComputerDTO;

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
  public static void print(Object object) {
    System.out.println(object.toString());
  }

  /**
   * Prints a list of entities.
   *
   * @param objectList
   *                   The list of objects to print. These object must extend
   *                   Entity.
   */
  public void printList(List<Object> objectList) {
    objectList.stream().forEach(toPrint -> print(toPrint));
  }

  public static void printComputerList(List<ComputerDTO> lComputer) {
    lComputer.stream().forEach(computerDTO -> {
      System.out.println(computerDTO.toString());
    });
  }

  public static void printCompanyList(List<CompanyDTO> companylist) {
    companylist.stream().forEach(companyDTO -> {
      System.out.println(companyDTO.toString());
    });
  }

}

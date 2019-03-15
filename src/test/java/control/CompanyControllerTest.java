package control;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import persistence.DAOFactory;

/**
 * Computer entity controller with specific DAO of type company.
 *
 * @author bbinet
 */

public class CompanyControllerTest {

  CompanyController cController = new CompanyController();

  @Test
  public void controllerConstructorTest() {
    assertEquals(DAOFactory.INSTANCE.getDao(DAOFactory.DAO_COMPANY).getClass(), this.cController.dao.getClass());
  }

}

package control;

import persistence.DAOFactory;

/**
 * Computer entity controller with specific DAO of type company.
 *
 * @author bbinet
 */

// TODO : Implement CRUD methods

public class CompanyController extends EntityController {

  /**
   * Basic Controller for Company entities. Sets the dao attribute using the dao
   * factory.
   */
  public CompanyController() {
    this.dao = DAOFactory.COMPANY.getDAO();
  }

  @Override
  public void create() {
    // TODO Auto-generated method stub

  }

  @Override
  public void delete() {
    // TODO Auto-generated method stub

  }

  @Override
  public void read() {
    // TODO Auto-generated method stub

  }

  @Override
  public void update() {
    // TODO Auto-generated method stub

  }

  @Override
  public void pagedList() {
    // TODO Auto-generated method stub

  }

}

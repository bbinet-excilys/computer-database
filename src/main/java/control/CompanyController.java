package control;

import persistence.DAOCompany;

/**
 * Computer entity controller with specific DAO of type company.
 *
 * @author bbinet
 */

// TODO : Implement CRUD methods

public class CompanyController {

  private DAOCompany dao;

  /**
   * Basic Controller for Company entities. Sets the dao attribute using the dao
   * factory.
   */
  public CompanyController() {
    this.dao = new DAOCompany();
  }

  public void create() {
    // TODO Auto-generated method stub

  }

  public void delete() {
    // TODO Auto-generated method stub

  }

  public void read() {
    // TODO Auto-generated method stub

  }

  public void update() {
    // TODO Auto-generated method stub

  }

  public void pagedList() {
    // TODO Auto-generated method stub

  }

  public void list() {
    // TODO
  }

}

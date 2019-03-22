package service;

import java.util.List;

import model.Company;
import model.CompanyPage;
import persistence.DAOCompany;
import ui.EntityUI;
import ui.UIHelper;

/**
 * Computer entity controller with specific DAO of type company.
 *
 * @author bbinet
 */

// TODO : Implement CRUD methods

public class CompanyService {

  private DAOCompany dao;

  /**
   * Basic Controller for Company entities. Sets the dao attribute using the dao
   * factory.
   */
  public CompanyService() {
    this.dao = new DAOCompany();
  }

  public void list() {
    List<Company> lCompany = this.dao.list();
    EntityUI.printCompanyList(lCompany);
  }

  public void pagedList() {
    UIHelper.promptInt("How many companies per page?").ifPresent(pageSize -> {
      CompanyPage cPage = new CompanyPage(pageSize);
      whileLoop: do {
        EntityUI.printCompanyList(cPage.getCurrentPage());
        Integer choice = UIHelper.promptPage(cPage.getPage());
        switch (choice) {
          case -1:
            cPage.previousPage();
            break;
          case 1:
            cPage.nextPage();
            break;
          default:
            break whileLoop;
        }

      } while (true);
    });
  }

}

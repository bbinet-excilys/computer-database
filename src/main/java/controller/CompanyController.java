package controller;

import java.util.List;

import model.Company;
import model.CompanyPage;
import service.CompanyService;
import service.ServiceFactory;
import ui.EntityUI;
import ui.UIHelper;

public class CompanyController {

  CompanyService companyService = ServiceFactory.INSTANCE.getCompanyService();

  public void list() {
    List<Company> lCompany = this.companyService.list();
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

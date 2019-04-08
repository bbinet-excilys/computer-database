package controller;

import model.CompanyPage;
import service.CompanyService;
import service.ServiceFactory;
import ui.EntityUI;
import ui.UIHelper;

public class CompanyController {

  CompanyService companyService = ServiceFactory.INSTANCE.getCompanyService();

  public void list() {
    EntityUI.printCompanyList(companyService.list());
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

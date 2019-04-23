package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.CompanyPage;
import service.CompanyService;
import ui.EntityUI;
import ui.UIHelper;

public class CompanyCLIController {

  private CompanyService companyService;

  public void setCompanyService(CompanyService companyService) {
    this.companyService = companyService;
  }

  private static Logger logger = LoggerFactory.getLogger(CompanyCLIController.class);

  public void list() {
    EntityUI.printCompanyList(companyService.list());
  }

  public void pagedList() {
    UIHelper.promptInt("How many companies per page?").ifPresent(pageSize -> {
      CompanyPage cPage = new CompanyPage(pageSize);
      cPage.setCompanies(companyService.list());
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

  public void delete() {
    UIHelper.promptLong("Enter the ID of the company to delete").ifPresent(companyId -> {
      companyService.read(companyId).ifPresentOrElse(companyDTO -> {
        companyService.deleteCompany(companyDTO);
      }, () -> {
        UIHelper.displayError("No such company exists in database.");
      });
    });
  }

}

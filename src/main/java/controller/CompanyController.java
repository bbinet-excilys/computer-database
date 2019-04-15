package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.DAOUnexecutedQuery;
import exception.PropertiesNotFoundException;
import model.CompanyPage;
import service.CompanyService;
import ui.EntityUI;
import ui.UIHelper;

public class CompanyController {

  private CompanyService companyService;

  public void setCompanyService(CompanyService companyService) {
    this.companyService = companyService;
  }

  private static Logger logger = LoggerFactory.getLogger(CompanyController.class);

  public void list() {
    try {
      EntityUI.printCompanyList(companyService.list());
    }
    catch (PropertiesNotFoundException e) {
      logger.error("Connection error, couldn't connect to database");
    }
  }

  public void pagedList() {
    UIHelper.promptInt("How many companies per page?").ifPresent(pageSize -> {
      try {
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
      }
      catch (PropertiesNotFoundException e) {
        logger.error("Connection error, couldn't connect to database");
      }
    });
  }

  public void delete() {
    UIHelper.promptLong("Enter the ID of the company to delete").ifPresent(companyId -> {
      try {
        companyService.read(companyId).ifPresentOrElse(companyDTO -> {
          try {
            companyService.deleteCompany(companyDTO);
          }
          catch (DAOUnexecutedQuery e) {
            UIHelper.displayError("Couldn't delete company : " + e.getCause().getMessage() + "\n");
          }
          catch (PropertiesNotFoundException e) {
            logger.error("Connection error, couldn't conenct to database");
          }
        }, () -> {
          UIHelper.displayError("No such company exists in database.");
        });
      }
      catch (PropertiesNotFoundException e) {
        logger.error("Connection error, couldn't conenct to database");
      }
    });
  }

}

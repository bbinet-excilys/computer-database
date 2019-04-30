package com.excilys.controller;

import com.excilys.ui.UIHelper;

/**
 * Basic class describing the behavior of the menu and calling corresponding
 * controllers methods.
 */
public class MenuController {

  private ComputerCLIController computerController;
  private CompanyCLIController  companyController;

  /**
   * Basic method looping until the user wants to exit. Get Controllers from the
   * Factory and calls corresponding methods.
   */
  public void runMenu() {
    boolean loop = true;
    do {
      UIHelper.displayMenu();
      MenuEnum choice;
      do {
        Integer index = UIHelper.promptInt("Please choose an item :")
                                .filter(val -> val < MenuEnum.values().length && val >= 0)
                                .orElse(null);
        if (index != null) {
          choice = MenuEnum.values()[index];
        }
        else {
          choice = null;
          UIHelper.displayMenu();
        }
      } while (choice == null);
      switch (choice) {
        case EXIT:
          loop = false;
          break;
        case LIST_COMPANIES:
          companyController.list();
          break;
        case LIST_COMPUTERS:
          computerController.list();
          break;
        case CREATE_COMPUTER:
          computerController.create();
          break;
        case READ_COMPUTER:
          computerController.read();
          break;
        case UPDATE_COMPUTER:
          computerController.update();
          break;
        case DELETE_COMPUTER:
          computerController.delete();
          break;
        case PAGED_COMPANIES:
          companyController.pagedList();
          break;
        case PAGED_COMPUTERS:
          computerController.pagedList();
          break;
        case DELETE_COMPANY:
          companyController.delete();
          break;
        default:
          UIHelper.displayError("Not available");
          break;
      }
    } while (loop);
  }

  public void setComputerController(ComputerCLIController computerController) {
    this.computerController = computerController;
  }

  public void setCompanyController(CompanyCLIController companyController) {
    this.companyController = companyController;
  }

}

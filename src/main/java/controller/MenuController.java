package controller;

import service.CompanyService;
import service.ComputerService;
import service.MenuEnum;
import service.ServiceFactory;
import ui.UIHelper;

/**
 * Basic class describing the behavior of the menu and calling corresponding
 * controllers methods.
 */
public class MenuController {

  /**
   * Basic method looping until the user wants to exit. Get Controllers from the
   * Factory and calls corresponding methods.
   */
  public void runMenu() {
    boolean         loop             = true;
    ComputerService mComputerService = ServiceFactory.INSTANCE.getComputerService();
    CompanyService  mCompanyService  = ServiceFactory.INSTANCE.getCompanyService();
    do {
      UIHelper.displayMenu();
      MenuEnum choice;
      do {
        Integer index = UIHelper.promptInt("Please choose an item :")
            .filter(val -> val < MenuEnum.values().length && val >= 0).orElse(null);
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
          mCompanyService.list();
          break;
        case LIST_COMPUTERS:
          mComputerService.list();
          break;
        case CREATE_COMPUTER:
          mComputerService.create();
          break;
        case READ_COMPUTER:
          mComputerService.read();
          break;
        case UPDATE_COMPUTER:
          mComputerService.update();
          break;
        case DELETE_COMPUTER:
          mComputerService.delete();
          break;
        case PAGED_COMPANIES:
          mCompanyService.pagedList();
          break;
        case PAGED_COMPUTERS:
          mComputerService.pagedList();
          break;
        default:
          UIHelper.displayError("Not available");
          break;
      }
    } while (loop);
  }

}

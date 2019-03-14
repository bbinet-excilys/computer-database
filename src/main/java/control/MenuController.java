package control;

import ui.UIHelper;

/**
 * Basic class describing the behavior of the menu and calling corresponding
 * controllers methods.
 */
public class MenuController implements Controller {

    /**
     * Basic method looping until the user wants to exit. Get Controllers from the
     * Factory and calls corresponding methods.
     */
    public void runMenu() {
        boolean loop = true;
        ComputerController mComputerController = (ComputerController) ControllerFactory.INSTANCE
                .getController(ControllerFactory.COMPUTER_CONTROLLER);
        CompanyController mCompanyController = (CompanyController) ControllerFactory.INSTANCE
                .getController(ControllerFactory.COMPANY_CONTROLLER);
        do {
            UIHelper.displayMenu();
            MenuEnum choice;
            do {
                choice = MenuEnum.values()[UIHelper.promptInt("Please choose an item :")];
            } while (choice == null);
            switch (choice) {
            case EXIT:
                loop = false;
                break;
            case LIST_COMPANIES:
                mCompanyController.list();
                break;
            case LIST_COMPUTERS:
                mComputerController.list();
                break;
            case CREATE_COMPUTER:
                mComputerController.create();
                break;
            case READ_COMPUTER:
                mComputerController.read();
                break;
            case UPDATE_COMPUTER:
                mComputerController.update();
                break;
            case DELETE_COMPUTER:
                mComputerController.delete();
                break;
            case PAGED_COMPANIES:
                mCompanyController.pagedList();
                break;
            case PAGED_COMPUTERS:
                mComputerController.pagedList();
                break;
            default:
                UIHelper.displayError("Not available");
                break;
            }
        } while (loop);
    }

}

package control;

import ui.UIHelper;

/**
 * Basic class describing the behavior of the menu and calling corresponding
 * controllers methods.
 */
public class MenuController implements Controller {

    private final static int EXIT            = 0;
    private final static int LIST_COMPANIES  = 1;
    private final static int LIST_COMPUTERS  = 2;
    private final static int CREATE_COMPUTER = 3;
    private final static int READ_COMPUTER   = 4;
    private final static int UPDATE_COMPUTER = 5;
    private final static int DELETE_COMPUTER = 6;
    private final static int PAGED_COMPANIES = 7;
    private final static int PAGED_COMPUTERS = 8;

    /**
     * Basic method looping until the user wants to exit. Get Controllers from the
     * Factory and calls corresponding methods.
     */
    public void runMenu() {
        boolean            loop                = true;
        ComputerController mComputerController = (ComputerController) ControllerFactory.INSTANCE
                .getController(ControllerFactory.COMPUTER_CONTROLLER);
        CompanyController  mCompanyController  = (CompanyController) ControllerFactory.INSTANCE
                .getController(ControllerFactory.COMPANY_CONTROLLER);
        do {
            UIHelper.displayMenu();
            Integer choice;
            do {
                choice = UIHelper.promptInt("Please choose an item :");
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

package control;

public enum ControllerFactory {

    /**
     * The unique instance of ControllerFactory.
     */
    INSTANCE;

    /**
     * Value for a ComputerController.
     */
    public static final int COMPUTER_CONTROLLER = 0;
    /**
     * Value for a CompanyController.
     */
    public static final int COMPANY_CONTROLLER = 1;
    /**
     * Value for a MenuController.
     */
    public static final int MENU_CONTROLLER = 2;

    /**
     * A private constructor to avoid external instanciation.
     */
    private ControllerFactory() {
    }

    /**
     * The get method for the Factory.
     */
    public Controller getController(int type) {
        Controller rController = null;
        switch (type) {
        case COMPUTER_CONTROLLER:
            rController = new ComputerController();
            break;
        case COMPANY_CONTROLLER:
            rController = new CompanyController();
            break;
        case MENU_CONTROLLER:
            rController = new MenuController();
            break;
        default:
            break;
        }
        return rController;
    }

}

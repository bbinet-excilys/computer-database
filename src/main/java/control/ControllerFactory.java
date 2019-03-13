package control;
public enum ControllerFactory {

    INSTANCE;

    public static final int COMPUTER_CONTROLLER = 0;
    public static final int COMPANY_CONTROLLER  = 1;
    public static final int MENU_CONTROLLER     = 2;

    private ControllerFactory() {

    }
    
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

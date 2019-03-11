import org.apache.catalina.valves.JDBCAccessLogValve;

import control.CompanyController;
import control.ComputerController;
import persistence.JDBCHelper;
import ui.UIHelper;

public class Main {

    public static void main(String[] args) {
        boolean loop = true;
        System.out.println(JDBCHelper.getConnection());
        ComputerController mComputerController = new ComputerController(JDBCHelper.getConnection());
        CompanyController  mCompanyController  = new CompanyController(JDBCHelper.getConnection());
        do {
            UIHelper.displayMenu();
            Integer choice;
            do {
                choice = UIHelper.promptInt("Please choose an item :");
            } while (choice == null);
            switch (choice) {
            case 0:
                loop = false;
                break;
            case 1:
                mCompanyController.list();
                break;
            case 2:
                mComputerController.list();
                break;
            case 3:
                mComputerController.create();
                break;
            case 4:
                mComputerController.read();
                break;
            case 5:
                mComputerController.update();
                break;
            case 6:
                mComputerController.delete();
                break;
            case 7:
                mComputerController.pagedList();
                break;
            case 8:
                mCompanyController.pagedList();
                break;

            default:
                UIHelper.displayError("Not available");
                break;
            }
        } while (loop);
        JDBCHelper.closeConnection();
    }

}

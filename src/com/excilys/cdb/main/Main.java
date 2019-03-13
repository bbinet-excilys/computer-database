package com.excilys.cdb.main;
import com.excilys.cdb.main.control.CompanyController;
import com.excilys.cdb.main.control.ComputerController;
import com.excilys.cdb.main.control.ControllerFactory;
import com.excilys.cdb.main.control.MenuController;
import com.excilys.cdb.main.persistence.JDBCSingleton;
import com.excilys.cdb.main.ui.UIHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class of the application.
 * 
 * @author bbinet
 *
 */
public class Main {
    
    static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.trace("Starting main method");
        MenuController menuController = (MenuController) ControllerFactory.INSTANCE.getController(ControllerFactory.MENU_CONTROLLER);
        menuController.runMenu();
        JDBCSingleton.INSTANCE.closeConnection();
    }

}

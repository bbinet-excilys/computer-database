
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import control.ControllerFactory;
import control.MenuController;
import persistence.JDBCSingleton;

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

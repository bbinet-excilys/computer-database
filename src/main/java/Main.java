
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.MenuController;
import persistence.JDBCSingleton;
import service.ServiceFactory;

/**
 * Main class of the application.
 *
 * @author bbinet
 */
public class Main {

  /**
   * The logger for the main class.
   */
  static final Logger LOG = LoggerFactory.getLogger(Main.class);

  /**
   * Entrypoint of the computer database application.
   */
  public static void main(String[] args) {
    LOG.trace("Starting main method");
    MenuController menuController = ServiceFactory.INSTANCE.getMenuService();
    menuController.runMenu();
    JDBCSingleton.INSTANCE.closeConnection();
  }

}


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.ControllerFactory;
import controller.MenuController;

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
    MenuController menuController = ControllerFactory.INSTANCE.getMenuController();
    menuController.runMenu();
  }

}

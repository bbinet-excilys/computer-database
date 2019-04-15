
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

  private static MenuController menuController;

  public static void setMenuController(MenuController menuController) {
    Main.menuController = menuController;
  }

  /**
   * Entrypoint of the computer database application.
   */
  public static void main(String[] args) {
    ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
    menuController.runMenu();
  }

}

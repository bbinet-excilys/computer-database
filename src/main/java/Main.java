
import java.io.File;
import java.io.IOException;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.querydsl.jpa.codegen.JPADomainExporter;

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
  private static SessionFactory sessionFactory;

  public static void setSessionFactory(SessionFactory sessionFactory) {
    Main.sessionFactory = sessionFactory;
  }

  public static void setMenuController(MenuController menuController) {
    Main.menuController = menuController;
  }

  /**
   * Entrypoint of the computer database application.
   */
  public static void main(String[] args) {
    ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext.xml");
    LOG.debug("sessionFactory : " + sessionFactory);
    JPADomainExporter export = new JPADomainExporter(new File("src/main/java"), sessionFactory.getMetamodel());
    try {
      export.execute();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    menuController.runMenu();
  }

}

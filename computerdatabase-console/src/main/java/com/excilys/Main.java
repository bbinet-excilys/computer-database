package com.excilys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.controller.MenuController;

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
    ApplicationContext appContext     = new ClassPathXmlApplicationContext("classpath:/spring/cliContext.xml");
    MenuController     menuController = (MenuController) appContext.getBean("MenuController");
    menuController.runMenu();
  }

}

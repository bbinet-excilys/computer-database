package service;

import controller.MenuController;

public enum ServiceFactory {

  /**
   * The unique instance of ControllerFactory.
   */
  INSTANCE;

  public CompanyService getCompanyService() {
    return new CompanyService();
  }

  public ComputerService getComputerService() {
    return new ComputerService();
  }

  public MenuController getMenuService() {
    return new MenuController();
  }

}

package controller;

public enum ControllerFactory {

  INSTANCE;

  public MenuController getMenuController() {
    return new MenuController();
  }

  public CompanyController getCompanyController() {
    return new CompanyController();
  }

  public ComputerController getComputerController() {
    return new ComputerController();
  }

}

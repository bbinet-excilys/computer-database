package control;

public enum ControllerFactory {

  /**
   * The unique instance of ControllerFactory.
   */
  INSTANCE;

  public CompanyController getCompanyController() {
    return new CompanyController();
  }

  public ComputerController getComputerController() {
    return new ComputerController();
  }

}

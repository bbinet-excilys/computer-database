package service;

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

}

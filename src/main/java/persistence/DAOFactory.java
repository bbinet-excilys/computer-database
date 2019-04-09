package persistence;

/**
 * Singleton implementation of the Factory pattern for DAOs.
 *
 * @author bbinet
 */
public enum DAOFactory {

  INSTANCE;

  public ComputerDAO getDAOComputer() {
    return new ComputerDAO();
  }

  public CompanyDAO getDAOCompany() {
    return new CompanyDAO();
  }

}

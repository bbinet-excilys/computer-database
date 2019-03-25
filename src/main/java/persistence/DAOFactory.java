package persistence;

/**
 * Singleton implementation of the Factory pattern for DAOs.
 *
 * @author bbinet
 */
public enum DAOFactory {

  INSTANCE;

  public DAOComputer getDAOComputer() {
    return new DAOComputer();
  }

  public DAOCompany getDAOCompany() {
    return new DAOCompany();
  }

}

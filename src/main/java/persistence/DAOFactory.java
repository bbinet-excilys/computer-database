package persistence;

/**
 * Singleton implementation of the Factory pattern for DAOs.
 *
 * @author bbinet
 */
public enum DAOFactory {
  /**
   * The unique instance of DAOFactory.
   */
  INSTANCE;

  /**
   * Value for a ComputerDAO.
   */
  public static final byte DAO_COMPUTER = 0;
  /**
   * Value for a CompanyDAO.
   */
  public static final byte DAO_COMPANY  = 1;

  /**
   * Private constructor to avoid external instanciation.
   */
  private DAOFactory() {
  }

  /**
   * Getter for the right DAO.
   */
  public DAO getDao(int type) {
    DAO rDAO = null;
    switch (type) {
      case DAO_COMPUTER:
        rDAO = new DAOComputer();
        break;
      case DAO_COMPANY:
        rDAO = new DAOCompany();
      default:
        break;
    }
    return rDAO;
  }
}

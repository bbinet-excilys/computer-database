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
  COMPUTER {
    private DAO dao = null;

    @Override
    public DAO getDAO() {
      if (this.dao == null) {
        this.dao = new DAOComputer();
      }
      return this.dao;
    }
  },
  COMPANY {
    private DAO dao = null;

    @Override
    public DAO getDAO() {
      if (this.dao == null) {
        this.dao = new DAOCompany();
      }
      return this.dao;
    }
  };

  public abstract DAO getDAO();

}

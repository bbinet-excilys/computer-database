package persistence;

import model.Entity;

/**
 * 
 * Singleton implementation of the Factory pattern for DAOs
 *
 * @author bbinet
 */
public enum DAOFactory {

    INSTANCE;

    public static final byte DAO_COMPUTER = 0;
    public static final byte DAO_COMPANY  = 1;

    private DAOFactory() {}

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

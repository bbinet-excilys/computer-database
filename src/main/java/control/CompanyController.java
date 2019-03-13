package control;

import model.Company;
import persistence.DAOFactory;

/**
 * Computer entity controller with specific DAO of type company.
 *
 * @author bbinet
 */

// TODO : Implement CRUD methods

public class CompanyController extends EntityController<Company> {

    /**
     * Basic Controller for Company entities. Sets the dao attribute using the dao
     * factory.
     */
    public CompanyController() {
        this.dao = DAOFactory.INSTANCE.getDao(DAOFactory.DAO_COMPANY);
    }

    @Override
    public void create() {
        // TODO Auto-generated method stub

    }

    @Override
    public void read() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub

    }

}

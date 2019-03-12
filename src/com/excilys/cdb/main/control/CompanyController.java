package com.excilys.cdb.main.control;

import com.excilys.cdb.main.model.Company;
import com.excilys.cdb.main.persistence.DAOCompany;
import com.excilys.cdb.main.persistence.DAOFactory;

/**
 * 
 * @author bbinet
 * Computer entity controller with specific DAO of type company
 */

//TODO : Implement CRUD methods

public class CompanyController extends EntityController<Company> {

    public CompanyController() {
        this.dao = (DAOCompany)DAOFactory.INSTANCE.getDao(DAOFactory.DAO_COMPANY);
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

package control;

import java.sql.Connection;
import java.util.List;

import model.Company;
import model.Computer;
import persistence.DAOCompany;
import ui.UIHelper;

public class CompanyController extends EntityController<Company> {

    public CompanyController(Connection connection) {
        this.dao = new DAOCompany(connection);
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

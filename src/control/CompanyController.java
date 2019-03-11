package control;

import java.sql.Connection;
import java.util.List;

import model.Company;
import persistence.DAOCompany;
import ui.CompanyUI;

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

    @Override
    public void list() {
        List<Company> mCompanyList = this.dao.list();
        CompanyUI.printList(mCompanyList);
    }

}

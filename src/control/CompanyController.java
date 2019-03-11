package control;

import java.sql.Connection;
import java.util.List;

import model.Company;
import model.Computer;
import persistence.DAOCompany;
import ui.CompanyUI;
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

    @Override
    public void list() {
        List<Company> mCompanyList = this.dao.list();
        CompanyUI.printList(mCompanyList);
    }

    public void pagedList() {
        Integer       size         = UIHelper.promptInt("How many companies per page ?");
        Integer       offset       = 0;
        List<Company> mCompanyList = null;
        do {
            mCompanyList = this.dao.list(size, offset * size);
            if (mCompanyList.size() <= 0) {
                break;
            }
            CompanyUI.printList(mCompanyList);
            Integer cpt = UIHelper.promptPage(offset);
            if (cpt != 0) {
                offset = (offset + cpt <= 0) ? 0 : offset + cpt;
            }
            else {
                break;
            }
        } while (true);
    }

}

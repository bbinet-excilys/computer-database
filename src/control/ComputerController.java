package control;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import model.Company;
import model.Computer;
import persistence.DAOCompany;
import persistence.DAOComputer;
import ui.ComputerUI;
import ui.UIHelper;

public class ComputerController extends EntityController<Computer> {

    Connection connection;

    public ComputerController(Connection connection) {
        this.dao        = new DAOComputer(connection);
        this.connection = connection;
    }

    @Override
    public void create() {
        String  cName       = null;
        Date    cIntroduced = null, cDiscontinued = null;
        Integer cCompanyId  = null;
        do {
            cName = UIHelper.promptString("Enter computer name :");
        } while (cName == null);
        cIntroduced   = UIHelper.promptDate("Enter date of introduction (YYYY-MM-DD) :");
        cDiscontinued = UIHelper.promptDate("Enter date of discontinuation (YYYY-MM-DD) :");
        cCompanyId    = UIHelper.promptInt("Enter Company ID :");
        cCompanyId    = verifyCompanyId(cCompanyId);
        Computer tComputer = new Computer();
        tComputer.setName(cName);
        tComputer.setIntroduced(cIntroduced);
        tComputer.setDiscontinued(cDiscontinued);
        tComputer.setCompanyId(cCompanyId);
        this.dao.create(tComputer);
    }

    public Integer verifyCompanyId(Integer companyId) {
        DAOCompany daoCompany = new DAOCompany(this.connection);
        Company    vCompany   = daoCompany.read(companyId);
        if (vCompany != null)
            return vCompany.getId();
        return null;
    }

    @Override
    public void read() {
        Integer cId = null;
        do {
            cId = UIHelper.promptInt("Enter the ID of the computer to display :");
        } while (cId == null);
        Computer cComputer = this.dao.read(cId);
        if (cComputer != null)
            ComputerUI.print(cComputer);
        else
            UIHelper.displayError("This computer doesn't exist in the database");
    }

    @Override
    public void update() {
        Integer cId = null;
        do {
            cId = UIHelper.promptInt("Enter the ID of the computer to update :");
        } while (cId == null);
        Computer cComputer   = this.dao.read(cId);
        String   cName       = null;
        Date     cIntroduced = null, cDiscontinued = null;
        Integer  cCompanyId  = null;
        ComputerUI.print(cComputer);
        cName         = UIHelper.promptString("Enter computer name :");
        cIntroduced   = UIHelper.promptDate("Enter date of introduction (YYYY-MM-DD) :");
        cDiscontinued = UIHelper.promptDate("Enter date of discontinuation (YYYY-MM-DD) :");
        cCompanyId    = UIHelper.promptInt("Enter Company ID :");

        cComputer.setName(cName);
        cComputer.setIntroduced(cIntroduced);
        cComputer.setDiscontinued(cDiscontinued);
        cComputer.setCompanyId(cCompanyId);
        this.dao.update(cComputer);
    }

    @Override
    public void delete() {
        Integer cId = null;
        do {
            cId = UIHelper.promptInt("Enter the ID of the computer to delete :");
        } while (cId == null);
        Computer cComputer = this.dao.read(cId);
        if (cComputer != null) {
            if (UIHelper.promptValidation("Are you sure ? (y/N)")) {
                this.dao.delete(cComputer);
            }
        }
        else {
            UIHelper.displayError("This computer doesn't exist");
        }
    }

    @Override
    public void list() {
        List<Computer> mComputerList = this.dao.list();
        ComputerUI.printList(mComputerList);
    }

    public void pagedList() {
        Integer        size          = UIHelper.promptInt("How many computers per page ?");
        Integer        offset        = 0;
        List<Computer> mComputerList = null;
        do {
            mComputerList = this.dao.list(size, offset * size);
            if (mComputerList.size() <= 0) {
                break;
            }
            ComputerUI.printList(mComputerList);
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

package control;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Computer;
import model.Entity;
import persistence.DAO;
import persistence.DAOFactory;
import ui.UIHelper;

/**
 * Controller for Computers (basic CRUD and list methods).
 *
 * @author bbinet Computer entity controller with specific DAO of type computer
 */
public class ComputerController extends EntityController {

  /**
   * Logger for the ComputerController class.
   */
  static final Logger LOG = LoggerFactory.getLogger(ComputerController.class);

  /**
   * Basic constructor for ComputerController. Get a DAOComputer from the
   * DAOFactory.
   */
  public ComputerController() {
    this.dao = DAOFactory.COMPUTER.getDAO();
  }

  @Override
  public void create() {
    String  cName         = null;
    Date    cIntroduced   = null;
    Date    cDiscontinued = null;
    Integer cCompanyId    = null;
    do {
      cName = UIHelper.promptString("Enter computer name :");
    } while (cName == null);
    cIntroduced = UIHelper.promptDate("Enter date of introduction (YYYY-MM-DD) :");
    if (cIntroduced != null) {
      do {
        cDiscontinued = UIHelper.promptDate("Enter date of discontinuation (YYYY-MM-DD) :");
      } while (!cDiscontinued.after(cIntroduced));

    }
    cCompanyId = UIHelper.promptInt("Enter Company ID :");
    cCompanyId = verifyCompanyId(cCompanyId);
    Computer tComputer = new Computer();
    tComputer.setName(cName);
    tComputer.setIntroduced(cIntroduced);
    tComputer.setDiscontinued(cDiscontinued);
    tComputer.setCompanyId(cCompanyId);
    this.dao.create(tComputer);
  }

  @Override
  public void delete() {
    Integer cId = null;
    do {
      cId = UIHelper.promptInt("Enter the ID of the computer to delete :");
    } while (cId == null);
    Entity cComputer = this.dao.read(cId);
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
  public void read() {
    Integer cId = null;
    do {
      cId = UIHelper.promptInt("Enter the ID of the computer to display :");
    } while (cId == null);
    Entity cComputer = this.dao.read(cId);
    if (cComputer != null) {
      this.entityUI.print(cComputer);
    }
    else {
      UIHelper.displayError("This computer doesn't exist in the database");
    }
  }

  @Override
  public void update() {
    Integer cId = null;
    do {
      cId = UIHelper.promptInt("Enter the ID of the computer to update :");
    } while (cId == null);
    Entity  cComputer     = this.dao.read(cId);
    String  cName         = null;
    Date    cIntroduced   = null;
    Date    cDiscontinued = null;
    Integer cCompanyId    = null;
    this.entityUI.print(cComputer);
    cName       = UIHelper.promptString("Enter computer name :");
    cIntroduced = UIHelper.promptDate("Enter date of introduction (YYYY-MM-DD) :");
    if (cIntroduced != null) {
      do {
        cDiscontinued = UIHelper.promptDate("Enter date of discontinuation (YYYY-MM-DD) :");
      } while (!cDiscontinued.after(cIntroduced));

    }
    cCompanyId = UIHelper.promptInt("Enter Company ID :");
    if (cComputer instanceof Computer) {
      Computer computer = (Computer) cComputer;
      computer.setName(cName);
      computer.setIntroduced(cIntroduced);
      computer.setDiscontinued(cDiscontinued);
      computer.setCompanyId(cCompanyId);
      this.dao.update(computer);
    }
  }

  /**
   * Checks if the companyId exists in the database.
   *
   * @param companyId
   *                  The id that requires checking.
   * @return Either the companyId or null
   */
  public Integer verifyCompanyId(Integer companyId) {
    DAO    daoCompany = DAOFactory.COMPANY.getDAO();
    Entity vCompany   = daoCompany.read(companyId);
    if (vCompany != null) {
      return vCompany.getId();
    }
    return null;
  }

  @Override
  public void pagedList() {
    // TODO Auto-generated method stub

  }

}

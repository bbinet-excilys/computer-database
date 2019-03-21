package control;

import java.sql.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company.CompanyBuilder;
import model.Computer;
import model.Computer.ComputerBuilder;
import model.Entity;
import persistence.DAOCompany;
import persistence.DAOComputer;
import persistence.DAOFactory;
import ui.EntityUI;
import ui.UIHelper;

/**
 * Controller for Computers (basic CRUD and list methods).
 *
 * @author bbinet Computer entity controller with specific DAO of type computer
 */
public class ComputerController {

  /**
   * Logger for the ComputerController class.
   */
  static final Logger LOG = LoggerFactory.getLogger(ComputerController.class);

  private DAOComputer dao      = new DAOComputer();
  private EntityUI    entityUI = new EntityUI();

  public void create() {
    String         cName         = null;
    Date           cIntroduced   = null;
    Date           cDiscontinued = null;
    Optional<Long> cCompanyId    = null;
    do {
      cName = UIHelper.promptString("Enter computer name :");
    } while (cName == null);
    cIntroduced = UIHelper.promptDate("Enter date of introduction (YYYY-MM-DD) :");
    if (cIntroduced != null) {
      do {
        cDiscontinued = UIHelper.promptDate("Enter date of discontinuation (YYYY-MM-DD) :");
      } while (!cDiscontinued.after(cIntroduced));

    }
//    Computer tComputer = new Computer();
    ComputerBuilder computerBuilder = new ComputerBuilder();
    CompanyBuilder  companyBuilder  = new CompanyBuilder();
    cCompanyId = UIHelper.promptLong("Enter Company ID :");
    cCompanyId.ifPresent(companyId -> {
      computerBuilder.setCompany(DAOFactory.INSTANCE.getDAOCompany().read(companyId));

    });
    computerBuilder.setName(cName);
    computerBuilder.setIntroduced(cIntroduced);
    computerBuilder.setDiscontinued(cDiscontinued);
    this.dao.create(computerBuilder.build());
  }

  public void delete() {
    Long cId = null;
    do {
      cId = UIHelper.promptLong("Enter the ID of the computer to delete :");
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

  public void read() {
    Long cId = null;
    do {
      cId = UIHelper.promptLong("Enter the ID of the computer to display :");
    } while (cId == null);
    Computer cComputer = this.dao.read(cId);
    if (cComputer != null) {
      this.entityUI.print(cComputer);
    }
    else {
      UIHelper.displayError("This computer doesn't exist in the database");
    }
  }

  public void update() {
    Long cId = null;
    do {
      cId = UIHelper.promptLong("Enter the ID of the computer to update :");
    } while (cId == null);
    Entity cComputer     = this.dao.read(cId);
    String cName         = null;
    Date   cIntroduced   = null;
    Date   cDiscontinued = null;
    Long   cCompanyId    = null;
    this.entityUI.print(cComputer);
    cName       = UIHelper.promptString("Enter computer name :");
    cIntroduced = UIHelper.promptDate("Enter date of introduction (YYYY-MM-DD) :");
    if (cIntroduced != null) {
      do {
        cDiscontinued = UIHelper.promptDate("Enter date of discontinuation (YYYY-MM-DD) :");
      } while (!cDiscontinued.after(cIntroduced));

    }
    cCompanyId = UIHelper.promptLong("Enter Company ID :");
    if (cComputer instanceof Computer) {
      ComputerBuilder computerBuilder = new ComputerBuilder();

      computerBuilder.setName(cName);
      computerBuilder.setIntroduced(cIntroduced);
      computerBuilder.setDiscontinued(cDiscontinued);
      CompanyController companyController = new CompanyController();
      computerBuilder.setCompany(DAOFactory.INSTANCE.getDAOCompany().read(cCompanyId));
      this.dao.update(computerBuilder.build());
    }
  }

  /**
   * Checks if the companyId exists in the database.
   *
   * @param companyId
   *                  The id that requires checking.
   * @return Either the companyId or null
   */
  public Long verifyCompanyId(Long companyId) {
    DAOCompany daoCompany = DAOFactory.INSTANCE.getDAOCompany();
    Entity     vCompany   = daoCompany.read(companyId);
    if (vCompany != null) {
      return vCompany.getId();
    }
    return null;
  }

  public void list() {
    // TODO Auto-generated method stub

  }

  public void pagedList() {
    // TODO Auto-generated method stub

  }

}

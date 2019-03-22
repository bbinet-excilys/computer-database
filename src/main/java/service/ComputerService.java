package service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.DAOUnexecutedQuery;
import model.Company;
import model.Computer;
import model.Computer.ComputerBuilder;
import model.ComputerPage;
import persistence.DAOComputer;
import persistence.DAOFactory;
import ui.EntityUI;
import ui.UIHelper;

/**
 * Controller for Computers (basic CRUD and list methods).
 *
 * @author bbinet Computer entity controller with specific DAO of type computer
 */
public class ComputerService {

  /**
   * Logger for the ComputerController class.
   */
  static final Logger LOG = LoggerFactory.getLogger("service");

  private DAOComputer dao      = new DAOComputer();
  private EntityUI    entityUI = new EntityUI();

  public void create() {
    Optional<String> oName = UIHelper.promptString("Enter computer name :");
    oName.ifPresentOrElse(name -> {
      ComputerBuilder computerBuilder = new ComputerBuilder();
      computerBuilder.setName(name);

      Optional<Date> oIntroduced = UIHelper.promptDate("Enter introduction date (YYYY-MM-DD) :");
      oIntroduced.ifPresent(introduced -> {
        computerBuilder.setIntroduced(introduced);
        Optional<Date> oDiscontinued = Optional.empty();
        oDiscontinued = UIHelper.promptDate("Enter discontinuation date (YYYY-MM-DD) :");
        oDiscontinued.ifPresent(discontinued -> computerBuilder.setDiscontinued(discontinued));
      });

      Optional<Long> oCompanyId = UIHelper.promptLong("Enter company ID :");
      oCompanyId.ifPresent(companyId -> {
        Company company = DAOFactory.INSTANCE.getDAOCompany().read(companyId).orElse(null);
        computerBuilder.setCompany(company);
      });
      this.dao.create(computerBuilder.build());
    }, () -> {
      UIHelper.displayError("The computer must have a name");
    });
  }

  public void delete() {
    Optional<Long> oId = UIHelper.promptLong("Enter the ID of the computer to delete :");
    oId.ifPresentOrElse(id -> {
      Optional<Computer> oComputer = this.dao.read(id);
      oComputer.ifPresentOrElse(computer -> {
        try {
          this.dao.delete(computer);
        }
        catch (DAOUnexecutedQuery e) {
          UIHelper.displayError("The computer has not been deleted.\n" + e.getMessage());
        }
      }, () -> {
        UIHelper.displayError("This computer ID doesn't exist");
      });
    }, () -> {
      UIHelper.displayError("Please enter a valid ID.");
    });
  }

  public void read() {
    Optional<Long> oId = UIHelper.promptLong("Enter the ID of the computer to display :");
    oId.ifPresentOrElse(id -> {
      Optional<Computer> oComputer;
      oComputer = this.dao.read(id);
      oComputer.ifPresentOrElse(computer -> {
        EntityUI.print(computer);
      }, () -> {
        UIHelper.displayError("No computer with this ID exist in database.");
      });
    }, () -> {
      UIHelper.displayError("Please enter à valid ID.");
    });
  }

  public void update() {

    Optional<Long> oId = UIHelper.promptLong("Enter the ID of the computer to update :");
    oId.ifPresentOrElse(id -> {
      Optional<Computer> oComputer = this.dao.read(id);
      oComputer.ifPresentOrElse(computer -> {
        EntityUI.print(computer);
        computer.setName(
            UIHelper.promptString("Enter the new name of the computer (or empty to keep it) :")
                .orElseGet(computer::getName));
        computer.setIntroduced(UIHelper
            .promptDate("Enter the new introduction date of the computer (or empty to keep it) :")
            .orElseGet(computer::getIntroduced));
        computer.setDiscontinued(UIHelper
            .promptDate(
                "Enter the new date of discontinuation of the computer (or empty to keep it) :")
            .orElseGet(computer::getDiscontinued));
        Long companyId = UIHelper
            .promptLong("Enter the id of the new company (or empty to keep it):")
            .orElse(computer.getCompany().getId());
        computer.setCompany(
            DAOFactory.INSTANCE.getDAOCompany().read(companyId).orElseGet(computer::getCompany));
        this.dao.update(computer);
      }, () -> {
        UIHelper.displayError("This computer ID doesn't exist in database");
      });
    }, () -> {
      UIHelper.displayError("Please enter a valid ID.");
    });
  }

  public void list() {
    List<Computer> lComputer = this.dao.list();
    EntityUI.printComputerList(lComputer);
  }

  public void pagedList() {
    UIHelper.promptInt("How many computers per page?").ifPresent(pageSize -> {
      ComputerPage cPage = new ComputerPage(pageSize);
      whileLoop: do {
        EntityUI.printComputerList(cPage.getCurrentPage());
        Integer choice = UIHelper.promptPage(cPage.getPage());
        switch (choice) {
          case -1:
            cPage.previousPage();
            break;
          case 1:
            cPage.nextPage();
            break;
          default:
            break whileLoop;
        }
      } while (true);
    });
  }

}

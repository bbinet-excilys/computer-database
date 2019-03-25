package controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import exception.DAOUnexecutedQuery;
import model.Company;
import model.Computer;
import model.Computer.ComputerBuilder;
import model.ComputerPage;
import persistence.DAOFactory;
import service.ComputerService;
import service.ServiceFactory;
import ui.EntityUI;
import ui.UIHelper;

public class ComputerController {

  private ComputerService computerService = ServiceFactory.INSTANCE.getComputerService();
  private EntityUI        entityUI        = new EntityUI();

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
      try {
        this.computerService.create(computerBuilder.build());
      }
      catch (DAOUnexecutedQuery e) {
        UIHelper.displayError(e.getMessage());
      }
    }, () -> {
      UIHelper.displayError("The computer must have a name");
    });
  }

  public void delete() {
    Optional<Long> oId = UIHelper.promptLong("Enter the ID of the computer to delete :");
    oId.ifPresentOrElse(id -> {
      Optional<Computer> oComputer;
      try {
        oComputer = this.computerService.read(id);
        oComputer.ifPresentOrElse(computer -> {
          try {
            this.computerService.delete(computer);
          }
          catch (DAOUnexecutedQuery e) {
            UIHelper.displayError("The computer has not been deleted.\n" + e.getMessage());
          }
        }, () -> {
          UIHelper.displayError("This computer ID doesn't exist");
        });
      }
      catch (DAOUnexecutedQuery e1) {
        UIHelper.displayError(e1.getMessage());
      }
    }, () -> {
      UIHelper.displayError("Please enter a valid ID.");
    });
  }

  public void read() {
    Optional<Long> oId = UIHelper.promptLong("Enter the ID of the computer to display :");
    oId.ifPresentOrElse(id -> {
      Optional<Computer> oComputer;
      try {
        oComputer = this.computerService.read(id);
        oComputer.ifPresentOrElse(computer -> {
          EntityUI.print(computer);
        }, () -> {
          UIHelper.displayError("No computer with this ID exist in database.");
        });
      }
      catch (DAOUnexecutedQuery e) {
        UIHelper.displayError(e.getMessage());
      }
    }, () -> {
      UIHelper.displayError("Please enter à valid ID.");
    });
  }

  public void update() {

    Optional<Long> oId = UIHelper.promptLong("Enter the ID of the computer to update :");
    oId.ifPresentOrElse(id -> {
      Optional<Computer> oComputer;
      try {
        oComputer = this.computerService.read(id);
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
          this.computerService.update(computer);
        }, () -> {
          UIHelper.displayError("This computer ID doesn't exist in database");
        });
      }
      catch (DAOUnexecutedQuery e) {
        UIHelper.displayError(e.getMessage());
      }
    }, () -> {
      UIHelper.displayError("Please enter a valid ID.");
    });
  }

  public void list() {
    List<Computer> lComputer = this.computerService.list();
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
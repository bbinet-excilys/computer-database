package controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.ComputerDTO;
import dto.ComputerDTO.ComputerDTOBuilder;
import exception.DAOUnexecutedQuery;
import exception.PropertiesNotFoundException;
import model.Company;
import model.ComputerPage;
import persistence.DAOFactory;
import service.ComputerService;
import service.ServiceFactory;
import ui.EntityUI;
import ui.UIHelper;

public class ComputerController {

  Logger logger = LoggerFactory.getLogger(ComputerController.class);

  private ComputerService computerService = ServiceFactory.INSTANCE.getComputerService();

  public void create() {
    Optional<String> oName = UIHelper.promptString("Enter computer name :");
    oName.ifPresentOrElse(name -> {
      ComputerDTOBuilder computerBuilder = ComputerDTO.builder();
      computerBuilder.withName(name);

      Optional<Date> oIntroduced = UIHelper.promptDate("Enter introduction date (YYYY-MM-DD) :");
      oIntroduced.ifPresent(introduced -> {
        computerBuilder.withIntroduced(introduced.toString());
        Optional<Date> oDiscontinued = Optional.empty();
        oDiscontinued = UIHelper.promptDate("Enter discontinuation date (YYYY-MM-DD) :");
        oDiscontinued.ifPresent(discontinued -> computerBuilder.withDiscontinued(discontinued.toString()));
      });

      Optional<Long> oCompanyId = UIHelper.promptLong("Enter company ID :");
      oCompanyId.ifPresent(companyId -> {
        Company company;
        try {
          company = DAOFactory.INSTANCE.getDAOCompany().read(companyId).orElse(null);
          computerBuilder.withCompanyId(company.getId());
          computerBuilder.withCompanyName(company.getName());
        }
        catch (PropertiesNotFoundException e) {
          logger.error("Connection error, couldn't conenct to database");
        }
      });
      try {
        computerService.create(computerBuilder.build());
      }
      catch (DAOUnexecutedQuery e) {
        UIHelper.displayError(e.getMessage());
      }
      catch (IllegalArgumentException e) {
        logger.warn(e.getMessage());
        UIHelper.displayError("One argument passed for creation was wrong : " + e.getMessage());
      }
      catch (PropertiesNotFoundException e) {
        UIHelper.displayError(e.getMessage());
      }
    }, () -> {
      UIHelper.displayError("The computer must have a name");
    });
  }

  public void delete() {
    Optional<Long> oId = UIHelper.promptLong("Enter the ID of the computer to delete :");
    oId.ifPresentOrElse(id -> {
      Optional<ComputerDTO> oComputer;
      try {
        oComputer = computerService.read(id);
        oComputer.ifPresentOrElse(computer -> {
          try {
            computerService.delete(computer);
          }
          catch (DAOUnexecutedQuery e) {
            UIHelper.displayError("The computer has not been deleted.\n" + e.getMessage());
          }
          catch (PropertiesNotFoundException e) {
            UIHelper.displayError(e.getMessage());
          }
        }, () -> {
          UIHelper.displayError("This computer ID doesn't exist");
        });
      }
      catch (DAOUnexecutedQuery e) {
        UIHelper.displayError(e.getMessage());
      }
      catch (PropertiesNotFoundException e) {
        UIHelper.displayError(e.getMessage());
      }
    }, () -> {
      UIHelper.displayError("Please enter a valid ID.");
    });
  }

  public void read() {
    Optional<Long> oId = UIHelper.promptLong("Enter the ID of the computer to display :");
    oId.ifPresentOrElse(id -> {
      Optional<ComputerDTO> oComputer;
      try {
        oComputer = computerService.read(id);
        oComputer.ifPresentOrElse(computer -> {
          EntityUI.print(computer);
        }, () -> {
          UIHelper.displayError("No computer with this ID exist in database.");
        });
      }
      catch (DAOUnexecutedQuery e) {
        UIHelper.displayError(e.getMessage());
      }
      catch (PropertiesNotFoundException e) {
        UIHelper.displayError(e.getMessage());
      }
    }, () -> {
      UIHelper.displayError("Please enter à valid ID.");
    });
  }

  public void update() {

    Optional<Long> oId = UIHelper.promptLong("Enter the ID of the computer to update :");
    oId.ifPresentOrElse(id -> {
      Optional<ComputerDTO> oComputer;
      try {
        oComputer = computerService.read(id);
        oComputer.ifPresentOrElse(computer -> {

          EntityUI.print(computer);

          computer.setName(UIHelper.promptString("Enter the new name of the computer (or empty to keep it) :")
                                   .orElseGet(computer::getName));

          computer.setIntroduced(UIHelper.promptDate("Enter the new introduction date of the computer (or empty to keep it) :")
                                         .map(Date::toString)
                                         .orElseGet(computer::getIntroduced));

          computer.setDiscontinued(UIHelper.promptDate("Enter the new date of discontinuation of the computer (or empty to keep it) :")
                                           .map(Date::toString)
                                           .orElseGet(computer::getDiscontinued));

          Long companyId = UIHelper.promptLong("Enter the id of the new company (or empty to keep it):")
                                   .orElse(computer.getCompanyId());

          try {
            computer.setCompanyId(DAOFactory.INSTANCE.getDAOCompany()
                                                     .read(companyId)
                                                     .map(Company::getId)
                                                     .orElseGet(computer::getCompanyId));
            computerService.update(computer);
          }
          catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            UIHelper.displayError(e.getMessage());
          }
          catch (PropertiesNotFoundException e) {
            UIHelper.displayError(e.getMessage());
          }
        }, () -> {
          UIHelper.displayError("This computer ID doesn't exist in database");
        });
      }
      catch (DAOUnexecutedQuery e) {
        UIHelper.displayError(e.getMessage());
      }
      catch (IllegalArgumentException e) {
        logger.warn(e.getMessage());
        UIHelper.displayError("One argument passed for update was wrong : " + e.getMessage());
      }
      catch (PropertiesNotFoundException e) {
        UIHelper.displayError(e.getMessage());
      }
    }, () -> {
      UIHelper.displayError("Please enter a valid ID.");
    });
  }

  public void list() {
    List<ComputerDTO> lComputer;
    try {
      lComputer = computerService.list();
      EntityUI.printComputerList(lComputer);
    }
    catch (PropertiesNotFoundException e) {
      UIHelper.displayError(e.getMessage());
    }
  }

  public void pagedList() {
    UIHelper.promptInt("How many computers per page?").ifPresent(pageSize -> {
      try {
        ComputerPage cPage = new ComputerPage(pageSize);
        cPage.setComputers(computerService.list());
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
      }
      catch (PropertiesNotFoundException e) {
        UIHelper.displayError(e.getMessage());
      }
    });
  }

}

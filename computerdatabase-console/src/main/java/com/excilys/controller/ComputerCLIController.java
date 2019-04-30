package com.excilys.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import com.excilys.mapping.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.pagination.ComputerPage;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.ui.EntityUI;
import com.excilys.ui.UIHelper;

public class ComputerCLIController {

  private final Logger LOGGER = LoggerFactory.getLogger(ComputerCLIController.class);

  private ComputerService computerService;
  private CompanyService  companyService;

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
        company = companyService.read(companyId).map(CompanyMapper::companyFromDTO).orElse(null);
        computerBuilder.withCompanyId(company.getId());
        computerBuilder.withCompanyName(company.getName());
      });
      try {
        computerService.create(computerBuilder.build());
      }
      catch (IllegalArgumentException e) {
        LOGGER.warn(e.getMessage());
        UIHelper.displayError("One argument passed for creation was wrong : " + e.getMessage());
      }
    }, () -> {
      UIHelper.displayError("The computer must have a name");
    });
  }

  public void setCompanyService(CompanyService companyService) {
    this.companyService = companyService;
  }

  public void setComputerService(ComputerService computerService) {
    this.computerService = computerService;
  }

  public void delete() {
    Optional<Long> oId = UIHelper.promptLong("Enter the ID of the computer to delete :");
    oId.ifPresentOrElse(id -> {
      Optional<ComputerDTO> oComputer;
      oComputer = computerService.read(id);
      oComputer.ifPresentOrElse(computer -> {
        computerService.delete(computer);
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
      Optional<ComputerDTO> oComputer;
      oComputer = computerService.read(id);
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
      Optional<ComputerDTO> oComputer;
      try {
        oComputer = computerService.read(id);
        oComputer.ifPresentOrElse(computerDTO -> {

          EntityUI.print(computerDTO);

          computerDTO.setName(UIHelper.promptString("Enter the new name of the computer (or empty to keep it) :")
                                      .orElseGet(computerDTO::getName));

          computerDTO.setIntroduced(UIHelper.promptDate("Enter the new introduction date of the computer (or empty to keep it) :")
                                            .map(Date::toString)
                                            .orElseGet(computerDTO::getIntroduced));

          computerDTO.setDiscontinued(UIHelper.promptDate("Enter the new date of discontinuation of the computer (or empty to keep it) :")
                                              .map(Date::toString)
                                              .orElseGet(computerDTO::getDiscontinued));

          Long companyId = UIHelper.promptLong("Enter the id of the new company (or empty to keep it):")
                                   .orElse(computerDTO.getCompanyId());

          try {
            companyService.read(companyId).ifPresent(companyDTO -> {
              computerDTO.setCompanyId(companyId);
              computerDTO.setCompanyName(companyDTO.getName());
            });
            computerService.update(computerDTO);
          }
          catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            UIHelper.displayError(e.getMessage());
          }
        }, () -> {
          UIHelper.displayError("This computer ID doesn't exist in database");
        });
      }
      catch (IllegalArgumentException e) {
        LOGGER.warn(e.getMessage());
        UIHelper.displayError("One argument passed for update was wrong : " + e.getMessage());
      }
    }, () -> {
      UIHelper.displayError("Please enter a valid ID.");
    });
  }

  public void list() {
    List<ComputerDTO> lComputer;
    lComputer = computerService.list();
    EntityUI.printComputerList(lComputer);
  }

  public void pagedList() {
    UIHelper.promptInt("How many computers per page?").ifPresent(pageSize -> {
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
    });
  }

}

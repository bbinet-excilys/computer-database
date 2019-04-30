package com.excilys.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.DashboardDTO;
import com.excilys.pagination.ComputerPage;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validation.ComputerValidator;

@Controller
@RequestMapping("/computers")
public class ComputerController {

  private Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);

  private final String MESSAGE_ERROR   = "danger";
  private final String MESSAGE_SUCCESS = "success";

  private ComputerService                       computerService;
  private CompanyService                        companyService;
  private ComputerValidator                     computerValidator;
  private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;
  private SessionLocaleResolver                 localeResolver;

  public ComputerController(ComputerService computerService, CompanyService companyService,
      ComputerValidator computerValidator,
      ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource,
      SessionLocaleResolver localResolver) {
    LOGGER.error("Controller created");
    this.companyService                        = companyService;
    this.computerService                       = computerService;
    this.computerValidator                     = computerValidator;
    this.reloadableResourceBundleMessageSource = reloadableResourceBundleMessageSource;
    localeResolver                             = localResolver;
  }

  @GetMapping("")
  public String getDashboard(
      @ModelAttribute("dashboardDTO") DashboardDTO<ComputerDTO> dashboardDTO,
      Model model) {
    ComputerPage cPage = new ComputerPage(dashboardDTO.getPageSize());
    if ("".equals(dashboardDTO.getSearchName())) {
      cPage.setComputers(computerService.list());
    }
    else {
      cPage.setComputers(computerService.paginatedSearchByNameList(dashboardDTO.getSearchName()));
    }
    cPage.setPage(dashboardDTO.getPage());
    Double  computerCount = (double) cPage.getComputers().size();
    Integer pageMax       = (int) Math.ceil((computerCount / dashboardDTO.getPageSize()));
    dashboardDTO.setPageMax(pageMax);
    dashboardDTO.setCount(computerCount.intValue());
    dashboardDTO.setSearchName(dashboardDTO.getSearchName());
    model.addAttribute("dashboardDTO", dashboardDTO);
    model.addAttribute("computers", cPage.getCurrentPage());
    return "dashboard";
  }

  @GetMapping("/{id:[0-9]+}")
  public String getComputerDetails(Model model, @PathVariable String id) {
    Long                  computerId   = Long.parseLong(id);
    Optional<ComputerDTO> oComputerDTO = computerService.read(computerId);
    LOGGER.debug("Get details on id : " + computerId);
    if (oComputerDTO.isPresent()) {
      model.addAttribute("computerDTO", oComputerDTO.get());
      return "detailsComputer";
    }
    else {
      return "redirect:/404";
    }
  }

  @PostMapping("")
  public String addNewComputer(Model model, @ModelAttribute("computerDTO") @Validated ComputerDTO computerDTO, BindingResult bindingResult,
      @RequestParam(name = "submitted", defaultValue = "false", required = false) String submittedString) {
    Boolean submitted = Boolean.parseBoolean(submittedString);
    LOGGER.debug("Submitted = " + submitted);
    model.addAttribute("companies", companyService.list());
    if (submitted) {
      computerValidator.validate(computerDTO, bindingResult);
      if (!bindingResult.hasErrors()) {
        Optional.ofNullable(computerDTO.getCompanyId())
                .filter(oCompanyId -> oCompanyId != 0)
                .ifPresent(companyId -> {
                  companyService.read(companyId).ifPresent(companyDTO -> computerDTO.setCompanyName(companyDTO.getName()));
                });
        computerService.create(computerDTO);
        return "redirect:/computers";
      }
    }
    return "addComputer";
  }

  @PostMapping("/{id:[0-9]+}")
  public String updateComputer(Model model, @ModelAttribute("computerDTO") @Validated ComputerDTO computerDTO,
      BindingResult bindingResult, @PathVariable String id,
      @RequestParam(name = "submitted", defaultValue = "false", required = false) String submittedString) {
    Boolean               submitted    = Boolean.parseBoolean(submittedString);
    Long                  computerId   = Long.parseLong(id);
    Optional<ComputerDTO> oComputerDTO = computerService.read(computerId);
    model.addAttribute("companies", companyService.list());
    if (oComputerDTO.isPresent()) {
      if (submitted) {
        computerValidator.validate(computerDTO, bindingResult);
        if (bindingResult.hasErrors()) {
          model.addAttribute(bindingResult);
        }
        else {
          Optional.ofNullable(computerDTO.getCompanyId())
                  .filter(oCompanyId -> oCompanyId != 0)
                  .ifPresent(companyId -> {
                    companyService.read(companyId).ifPresent(companyDTO -> computerDTO.setCompanyName(companyDTO.getName()));
                  });
          model.addAttribute("computerDTO", computerDTO);
          computerService.update(computerDTO);
          return "redirect:/computers";
        }
      }
      else {
        model.addAttribute("computerDTO", oComputerDTO.get());
      }
    }
    else {
      return "redirect:/404";
    }
    return "editComputer";
  }

  @PostMapping({ "/delete/{id:[0-9]*}", "/delete" })
  public String deleteComputer(Model model,
      @RequestParam(name = "submitted", defaultValue = "false", required = false) String submittedString,
      @ModelAttribute("computerDTO") ComputerDTO computerDTO, @PathVariable(required = false) String id) {
    Boolean submitted = Boolean.parseBoolean(submittedString);
    if (submitted) {
      Optional<ComputerDTO> oComputerDTO = computerService.read(computerDTO.getId());
      if (oComputerDTO.isPresent()) {
        computerService.delete(oComputerDTO.get());
        return "redirect:/computers";
      }
      else {
        return "redirect:/404";
      }
    }
    else {
      try {
        Optional<ComputerDTO> oComputerDTO = computerService.read(Long.parseLong(id));
        if (oComputerDTO.isPresent()) {
          computerDTO = oComputerDTO.get();
        }
      }
      catch (NumberFormatException e) {
      }
    }
    model.addAttribute("computers", computerService.list());
    return "deleteComputer";
  }

  @GetMapping("/404")
  public String get404(Model model) {
    return "404";
  }
}

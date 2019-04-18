package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import dto.CompanyDTO;
import dto.ComputerDTO;
import model.ComputerPage;
import service.CompanyService;
import service.ComputerService;

@Controller
public class ComputerController {

  private Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);

  private ComputerService computerService;
  private CompanyService  companyService;

  public ComputerController(ComputerService computerService, CompanyService companyService) {
    this.companyService  = companyService;
    this.computerService = computerService;
  }

  @GetMapping("/dashboard")
  public ModelAndView getDashboard(
      @RequestParam(value = "page", required = false, defaultValue = "1") String pageString,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSizeString,
      @RequestParam(value = "searchName", required = false, defaultValue = "") String searchName) {
    ModelAndView model    = new ModelAndView("dashboard");
    Integer      page     = Integer.parseInt(pageString);
    Integer      pageSize = Integer.parseInt(pageSizeString);
    ComputerPage cPage    = new ComputerPage(pageSize);
    if ("".equals(searchName)) {
      cPage.setComputers(computerService.list());
    }
    else {
      cPage.setComputers(computerService.paginatedSearchByNameList(searchName));
    }
    cPage.setPage(page);
    Double  computerCount = (double) cPage.getComputers().size();
    Integer pageMax       = (int) Math.ceil((computerCount / pageSize));
    model.addObject("page", page);
    model.addObject("pageSize", pageSize);
    model.addObject("pageMax", pageMax);
    model.addObject("searchName", searchName);
    model.addObject("count", computerCount.intValue());
    model.addObject("computers", cPage.getCurrentPage());
    return model;
  }

  @PostMapping("/dashboard")
  public ModelAndView postDashboard(
      @RequestParam(value = "page", required = false, defaultValue = "1") String pageString,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSizeString,
      @RequestParam(value = "searchName", required = false, defaultValue = "") String searchName, Model model) {
    return getDashboard(pageString, pageSizeString, searchName);
  }

  @GetMapping("/addcomputer")
  public ModelAndView getAddComputer(ModelAndView model) {
    model.setViewName("addComputer");
    model.addObject("addComputerForm", ComputerDTO.builder().build());
    model.addObject("companies", companyService.list());
    return model;
  }

  @PostMapping("/addcomputer")
  public RedirectView postAddComputer(ModelAndView model, @ModelAttribute(
    "addComputerForm"
  ) @Validated ComputerDTO computerDTO, BindingResult bindingResult) {
    LOGGER.error("posted DTO : " + computerDTO.toString());
    computerDTO.setCompanyName(companyService.read(computerDTO.getCompanyId())
                                             .map(CompanyDTO::getName)
                                             .orElse(""));
    computerService.create(computerDTO);
    return new RedirectView("dashboard");
  }

  @GetMapping("/deletecomputer")
  public ModelAndView getDeleteComputer(ModelAndView model) {
    model.setViewName("deleteComputer");
    model.addObject("computers", computerService.list());
    model.addObject("computerDTOModel", ComputerDTO.builder().build());
    return model;
  }

  @PostMapping("/deletecomputer")
  public RedirectView postDeleteComputer(ModelAndView model, @ModelAttribute(
    "deleteComputerForm"
  ) @Validated ComputerDTO computerDTO) {
    model.setViewName("deleteComputer");
    computerService.read(computerDTO.getId()).ifPresent(cDTO -> computerService.delete(cDTO));
    return new RedirectView("dashboard");
  }

  @GetMapping("/editcomputer")
  public ModelAndView getEditComputer(ModelAndView model, @RequestParam(
    value = "computerId",
    required = false,
    defaultValue = "1"
  ) String computerIdString) {
    model.setViewName("editComputer");
    LOGGER.debug(model.getModel().toString());
    model.addObject("editComputerForm", computerService.read(Long.parseLong(computerIdString)));
    model.addObject("companies", companyService.list());
    return model;
  }

  @PostMapping("/editcomputer")
  public RedirectView postEditComputer(ModelAndView model, @ModelAttribute(
    "editComputerForm"
  ) @Validated ComputerDTO computerDTO, BindingResult bindingResult) {
    model.setViewName("editComputer");
    LOGGER.debug(computerDTO.toString());
    computerService.update(computerDTO);
    return new RedirectView("dashboard");
  }

  @GetMapping("/detailscomputer")
  public ModelAndView getDetailsComputer(ModelAndView model) {
    model.setViewName("detailsComputer");
    return model;
  }

  @PostMapping("/detailscomputer")
  public ModelAndView postDetailsComputer(ModelAndView model) {
    return getDetailsComputer(model);
  }

}

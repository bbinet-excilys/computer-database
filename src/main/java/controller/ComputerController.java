package controller;

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

import dto.ComputerDTO;
import dto.DashboardDTO;
import model.ComputerPage;
import service.CompanyService;
import service.ComputerService;
import validation.ComputerValidator;

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

  public ComputerController(ComputerService computerService, CompanyService companyService, ComputerValidator computerValidator,
      ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource, SessionLocaleResolver localResolver) {
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
          LOGGER.debug(computerDTO.toString());
          model.addAttribute("computerDTO", computerDTO);
          computerService.update(computerDTO);
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

//  @GetMapping("/addcomputer")
//  public String getAddComputer(Model model, MessageDTO message, BindingResult bindingResult, ComputerDTO computerDTO) {
//    LOGGER.debug("PLOP : " + bindingResult.getAllErrors().toString());
//    model.addAttribute("addComputerForm", ComputerDTO.builder().build());
//    model.addAttribute("companies", companyService.list());
//    return "addComputer";
//  }
//
//  @PostMapping("/addcomputer")
//  public String postAddComputer(ModelAndView model, @ModelAttribute(
//    "addComputerForm"
//  ) @Validated ComputerDTO computerDTO, BindingResult bindingResult, RedirectAttributes attributes, HttpServletRequest request,
//      HttpServletResponse response) {
//    computerDTO.setCompanyName(companyService.read(computerDTO.getCompanyId())
//                                             .map(CompanyDTO::getName)
//                                             .orElse(""));
//    computerValidator.validate(computerDTO, bindingResult);
//    if (bindingResult.hasErrors()) {
//      attributes.addFlashAttribute("bindingResult", bindingResult);
//      attributes.addFlashAttribute("computerDTO", computerDTO);
//      return "redirect:/addcomputer";
//    }
//    computerService.create(computerDTO);
//    return "redirect:/addcomputer";
//  }
//
//  @GetMapping("/deletecomputer")
//  public ModelAndView getDeleteComputer(ModelAndView model) {
//    model.setViewName("deleteComputer");
//    model.addObject("computers", computerService.list());
//    model.addObject("computerDTOModel", ComputerDTO.builder().build());
//    return model;
//  }
//
//  @PostMapping("/deletecomputer")
//  public RedirectView postDeleteComputer(ModelAndView model, @ModelAttribute(
//    "deleteComputerForm"
//  ) @Validated ComputerDTO computerDTO) {
//    model.setViewName("deleteComputer");
//    computerService.read(computerDTO.getId()).ifPresent(cDTO -> computerService.delete(cDTO));
//    return new RedirectView("dashboard");
//  }
//
//  @GetMapping("/editcomputer")
//  public ModelAndView getEditComputer(ModelAndView model, @RequestParam(
//    value = "computerId",
//    required = false,
//    defaultValue = "1"
//  ) String computerIdString) {
//    model.setViewName("editComputer");
//    LOGGER.debug(model.getModel().toString());
//    model.addObject("editComputerForm", computerService.read(Long.parseLong(computerIdString)));
//    model.addObject("companies", companyService.list());
//    return model;
//  }
//
//  @PostMapping("/editcomputer")
//  public RedirectView postEditComputer(ModelAndView model, @ModelAttribute(
//    "editComputerForm"
//  ) @Validated ComputerDTO computerDTO, BindingResult bindingResult) {
//    model.setViewName("editComputer");
//    LOGGER.debug(computerDTO.toString());
//    computerService.update(computerDTO);
//    return new RedirectView("dashboard");
//  }
//
//  @GetMapping("/detailscomputer")
//  public ModelAndView getDetailsComputer(ModelAndView model,
//      @RequestParam(name = "computerId", required = true, defaultValue = "") String computerIdString) {
//    model.setViewName("detailsComputer");
//    try {
//      Long computerId = Long.parseLong(computerIdString);
//      computerService.read(computerId).ifPresent(computerDTO -> {
//        model.addObject("computer", computerDTO);
//      });
//
//    }
//    catch (NumberFormatException e) {
//    }
//    return model;
//  }
//
//  @PostMapping("/detailscomputer")
//  public ModelAndView postDetailsComputer(ModelAndView model,
//      @RequestParam(name = "computerId", required = true, defaultValue = "") String computerIdString, RedirectAttributes attributes) {
//    model.addObject("computerId", computerIdString);
//    return getDetailsComputer(model, computerIdString);
//  }
//
//  private MessageDTO getMessage(String type, String titleCode, String messageCode, HttpServletRequest request) {
//    MessageDTOBuilder mDTOBuilder = MessageDTO.builder();
//    Locale            locale      = localeResolver.resolveLocale(request);
//    String            title       = reloadableResourceBundleMessageSource.getMessage(titleCode, null, locale);
//    String            message     = reloadableResourceBundleMessageSource.getMessage(messageCode, null, locale);
//    mDTOBuilder.withType(type);
//    mDTOBuilder.withTitle(title);
//    mDTOBuilder.withContent(message);
//    return mDTOBuilder.build();
//  }

}

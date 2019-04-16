package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
public class DashboardController {

  private Logger LOGGER = LoggerFactory.getLogger("DashboardController");

  @Autowired
  RequestMappingHandlerMapping handler;

  @GetMapping("/dashboard")
  public ModelAndView getDashboard(
      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
      @RequestParam(value = "searchName", required = false, defaultValue = "") String researchName) {
    ModelAndView model = new ModelAndView();
    model.setViewName("dashboard");
    model.addObject("page", page);
    handler.getHandlerMethods().forEach((info, method) -> {
      LOGGER.debug(String.format("%s : %s", info, method));
    });
    return model;
  }

  @PostMapping("/dashboard")
  public void postDashboard() {

  }

}

package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

  @GetMapping({ "/", "/dashboard" })
  public void doGet(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

  }

  @PostMapping
  public void doPost() {

  }

}

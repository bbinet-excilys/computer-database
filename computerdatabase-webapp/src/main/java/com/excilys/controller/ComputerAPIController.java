package com.excilys.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.dto.ComputerDTO;
import com.excilys.service.ComputerService;

@RestController
@RequestMapping("/api/computers")
public class ComputerAPIController {

  private ComputerService computerService;

  public ComputerAPIController(ComputerService computerService) {
    this.computerService = computerService;
  }

  @GetMapping("")
  public List<ComputerDTO> getAllComputers() {
    return computerService.list();
  }

  @GetMapping("/{id:[0-9]+}")
  public ComputerDTO getComputer(Model model, @PathVariable String id) {
    Long                  computerId = Long.parseLong(id);
    Optional<ComputerDTO> oComputer  = computerService.read(computerId);
    if (oComputer.isPresent()) {
      return oComputer.get();
    }
    return null;
  }

  @PostMapping("")
  public String addNewComputer() {
    return null;
  }

  @PatchMapping("/{id:[0-9]+}")
  public String updateComputer() {
    return null;
  }

  @DeleteMapping("/{id:[0-9]+}")
  public String deleteComputer() {
    return null;
  }

}

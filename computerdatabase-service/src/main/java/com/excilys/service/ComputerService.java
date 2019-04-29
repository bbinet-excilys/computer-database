package com.excilys.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapping.ComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.validation.CompanyValidator;
import com.excilys.validation.ComputerValidator;

/**
 * Controller for Computers (basic CRUD and list methods).
 *
 * @author bbinet Computer entity controller with specific DAO of type computer
 */
public class ComputerService {

  /**
   * Logger for the ComputerController class.
   */
  static final Logger LOGGER = LoggerFactory.getLogger("service");

  private ComputerDAO computerDAO;

  public void setComputerDAO(ComputerDAO computerDAO) {
    this.computerDAO = computerDAO;
  }

  public void create(ComputerDTO computerDTO) {
    Computer computer = ComputerMapper.computerFromDTO(computerDTO);
    LOGGER.debug(computer.toString());
    if (computer.getCompany().getName() != null) {
      CompanyValidator.companyIsValid(computer.getCompany());
    }
    ComputerValidator.computerIsValid(computer);
    computerDAO.create(computer);
  }

  public Optional<ComputerDTO> read(Long id) {
    return computerDAO.read(id).map(ComputerMapper::computerToDTO);
  }

  public void delete(ComputerDTO computerDTO) {
    Computer computer = ComputerMapper.computerFromDTO(computerDTO);
    if (computer.getCompany().getName() != null) {
      CompanyValidator.companyIsValid(computer.getCompany());
    }
    ComputerValidator.computerIsValid(computer);
    computerDAO.delete(computer);
  }

  public void update(ComputerDTO computerDTO) {
    LOGGER.debug("Updating from DTO " + computerDTO);
    Computer computer = ComputerMapper.computerFromDTO(computerDTO);
    if (computer.getCompany().getName() != null) {
      CompanyValidator.companyIsValid(computer.getCompany());
    }
    ComputerValidator.computerIsValid(computer);
    LOGGER.debug("Updating computer " + computer.toString());
    computerDAO.update(computer);
  }

  public List<ComputerDTO> list() {
    return computerDAO.list()
                      .stream()
                      .map(ComputerMapper::computerToDTO)
                      .collect(Collectors.toList());
  }

  public List<ComputerDTO> paginatedSearchByNameList(String name) {
    return computerDAO.searchByName(name)
                      .stream()
                      .map(ComputerMapper::computerToDTO)
                      .collect(Collectors.toList());
  }

  public void deleteCompany(Company company) {
    computerDAO.deleteCompany(company);
  }

}

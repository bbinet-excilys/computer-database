package service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.ComputerDTO;
import exception.DAOUnexecutedQuery;
import exception.PropertiesNotFoundException;
import mapping.ComputerMapper;
import model.Company;
import model.Computer;
import persistence.ComputerDAO;
import validation.CompanyValidator;
import validation.ComputerValidator;

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

  public void create(ComputerDTO computerDTO)
    throws DAOUnexecutedQuery, IllegalArgumentException, PropertiesNotFoundException {
    Computer computer = ComputerMapper.computerFromDTO(computerDTO);
    LOGGER.debug(computer.toString());
    if (computer.getCompany().getName() != null) {
      CompanyValidator.companyIsValid(computer.getCompany());
    }
    ComputerValidator.computerIsValid(computer);
    computerDAO.create(computer);
  }

  public Optional<ComputerDTO> read(Long id)
    throws DAOUnexecutedQuery, PropertiesNotFoundException {
    return Optional.ofNullable(computerDAO.read(id)).map(ComputerMapper::computerToDTO);
  }

  public void delete(ComputerDTO computerDTO)
    throws DAOUnexecutedQuery, PropertiesNotFoundException, IllegalArgumentException {
    Computer computer = ComputerMapper.computerFromDTO(computerDTO);
    ComputerValidator.computerIsValid(computer);
    CompanyValidator.companyIsValid(computer.getCompany());
    computerDAO.delete(computer);
  }

  public void update(ComputerDTO computerDTO)
    throws IllegalArgumentException, PropertiesNotFoundException {
    Computer computer = ComputerMapper.computerFromDTO(computerDTO);
    if (computer.getCompany() != null) {
      CompanyValidator.companyIsValid(computer.getCompany());
    }
    ComputerValidator.computerIsValid(computer);
    computerDAO.update(computer);
  }

  public List<ComputerDTO> list() throws PropertiesNotFoundException {
    return computerDAO.list()
                      .stream()
                      .map(ComputerMapper::computerToDTO)
                      .collect(Collectors.toList());
  }

  public List<ComputerDTO> paginatedSearchByNameList(String name)
    throws PropertiesNotFoundException, DAOUnexecutedQuery {
    return computerDAO.searchByName(name)
                      .stream()
                      .map(ComputerMapper::computerToDTO)
                      .collect(Collectors.toList());
  }

  public void deleteCompany(Company company) {
    computerDAO.deleteCompany(company);
  }

}

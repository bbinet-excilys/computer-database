package service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.ComputerDTO;
import exception.DAOUnexecutedQuery;
import exception.PropertiesNotFoundException;
import mapping.ComputerMapper;
import model.Computer;
import persistence.DAOComputer;
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
  static final Logger LOG = LoggerFactory.getLogger("service");

  private DAOComputer dao = new DAOComputer();

  public void create(Computer computer)
      throws DAOUnexecutedQuery, IllegalArgumentException, PropertiesNotFoundException {
    if (computer.getCompany() != null) {
      CompanyValidator.companyIsValid(computer.getCompany());
    }
    ComputerValidator.computerIsValid(computer);
    dao.create(computer);
  }

  public Optional<ComputerDTO> read(Long id)
      throws DAOUnexecutedQuery, PropertiesNotFoundException {
    Optional<Computer>    oComputer = dao.read(id);
    Optional<ComputerDTO> oCDTO     = Optional.empty();
    if (oComputer.isPresent()) {
      Computer computer = oComputer.get();
      oCDTO = Optional.of(ComputerMapper.computerToDTO(computer));
    }
    return oCDTO;
  }

  public void delete(Computer computer) throws DAOUnexecutedQuery, PropertiesNotFoundException {
    ComputerValidator.computerIsValid(computer);
    dao.delete(computer);
  }

  public void update(Computer computer)
      throws IllegalArgumentException, PropertiesNotFoundException {
    if (computer.getCompany() != null) {
      CompanyValidator.companyIsValid(computer.getCompany());
    }
    ComputerValidator.computerIsValid(computer);
    dao.update(computer);
  }

  public List<Computer> list() throws PropertiesNotFoundException {
    return dao.list();
  }

}

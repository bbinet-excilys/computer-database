package service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.DAOUnexecutedQuery;
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

  public void create(Computer computer) throws DAOUnexecutedQuery, IllegalArgumentException {
    if (computer.getCompany() != null) {
      CompanyValidator.companyIsValid(computer.getCompany());
    }
    ComputerValidator.computerIsValid(computer);
    dao.create(computer);
  }

  public Optional<Computer> read(Long id) throws DAOUnexecutedQuery {
    return dao.read(id);

  }

  public void delete(Computer computer) throws DAOUnexecutedQuery {
    dao.delete(computer);

  }

  public void update(Computer computer) throws IllegalArgumentException {
    if (computer.getCompany() != null) {
      CompanyValidator.companyIsValid(computer.getCompany());
    }
    ComputerValidator.computerIsValid(computer);
    dao.update(computer);
  }

  public List<Computer> list() {
    return dao.list();
  }

}

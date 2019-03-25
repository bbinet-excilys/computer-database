package service;

import java.util.List;

import model.Company;
import persistence.DAOCompany;
import persistence.DAOFactory;

/**
 * Computer entity controller with specific DAO of type company.
 *
 * @author bbinet
 */

// TODO : Implement CRUD methods

public class CompanyService {

  private DAOCompany dao = DAOFactory.INSTANCE.getDAOCompany();

  public List<Company> list() {
    return this.dao.list();
  }

}

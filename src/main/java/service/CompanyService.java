package service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dto.CompanyDTO;
import exception.DAOUnexecutedQuery;
import exception.PropertiesNotFoundException;
import mapping.CompanyMapper;
import model.Company;
import persistence.CompanyDAO;
import persistence.ComputerDAO;
import persistence.DAOFactory;

/**
 * Computer entity controller with specific DAO of type company.
 *
 * @author bbinet
 */

public class CompanyService {

  private CompanyDAO dao = DAOFactory.INSTANCE.getDAOCompany();

  public List<CompanyDTO> list() throws PropertiesNotFoundException {
    return dao.list()
              .stream()
              .map(CompanyMapper::companyToDTO)
              .collect(Collectors.toList());
  }

  public Optional<CompanyDTO> read(Long id) throws PropertiesNotFoundException {
    return dao.read(id).map(CompanyMapper::companyToDTO);
  }

  public List<CompanyDTO> paginatedList(Integer pageSize, Integer offset)
    throws PropertiesNotFoundException {
    return dao.paginatedList(pageSize, offset)
              .stream()
              .map(CompanyMapper::companyToDTO)
              .collect(Collectors.toList());
  }

  public void deleteCompany(CompanyDTO companyDTO)
    throws DAOUnexecutedQuery, PropertiesNotFoundException {
    ComputerDAO daoComputer = new ComputerDAO();
    Company     company     = CompanyMapper.companyFromDTO(companyDTO);
    daoComputer.deleteCompany(company);
    dao.delete(company);
  }

}

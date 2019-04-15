package service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.CompanyDTO;
import exception.DAOUnexecutedQuery;
import exception.PropertiesNotFoundException;
import mapping.CompanyMapper;
import model.Company;
import persistence.CompanyDAO;

/**
 * Computer entity controller with specific DAO of type company.
 *
 * @author bbinet
 */

public class CompanyService {

  private CompanyDAO      companyDAO;
  private ComputerService computerService;
  private Logger          LOGGER = LoggerFactory.getLogger(CompanyService.class);

  public void setCompanyDAO(CompanyDAO companyDAO) {
    this.companyDAO = companyDAO;
  }

  public void setComputerService(ComputerService computerService) {
    this.computerService = computerService;
  }

  public List<CompanyDTO> list() throws PropertiesNotFoundException {
    return companyDAO.list()
                     .stream()
                     .map(CompanyMapper::companyToDTO)
                     .collect(Collectors.toList());
  }

  public Optional<CompanyDTO> read(Long id) throws PropertiesNotFoundException {
    return companyDAO.read(id).map(CompanyMapper::companyToDTO);
  }

  public void deleteCompany(CompanyDTO companyDTO)
    throws DAOUnexecutedQuery, PropertiesNotFoundException {
    Company company = CompanyMapper.companyFromDTO(companyDTO);
    LOGGER.debug(company.toString());
    computerService.deleteCompany(company);
    companyDAO.delete(company);
  }

}

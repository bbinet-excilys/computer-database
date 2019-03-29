package service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dto.CompanyDTO;
import mapping.CompanyMapper;
import persistence.DAOCompany;
import persistence.DAOFactory;

/**
 * Computer entity controller with specific DAO of type company.
 *
 * @author bbinet
 */

public class CompanyService {

  private DAOCompany dao = DAOFactory.INSTANCE.getDAOCompany();

  public List<CompanyDTO> list() {
    return dao.list()
              .stream()
              .map(CompanyMapper::companyToDTO)
              .collect(Collectors.toList());
  }

  public Optional<CompanyDTO> read(Long id) {
    return dao.read(id).map(CompanyMapper::companyToDTO);
  }

}

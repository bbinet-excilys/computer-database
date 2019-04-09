package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.CompanyDTO;
import exception.PropertiesNotFoundException;
import service.CompanyService;

public class CompanyPage {

  Logger logger = LoggerFactory.getLogger(CompanyPage.class);

  private Integer          pageSize    = 10;
  private Integer          offset      = 0;
  private Integer          page        = 0;
  private CompanyService   cService    = new CompanyService();
  private List<CompanyDTO> companies   = new ArrayList<>();
  private List<CompanyDTO> currentPage = new ArrayList<>();

  public CompanyPage(Integer pageSize) {
    super();
    Optional.of(pageSize).filter(val -> val >= 1).ifPresent(size -> setPageSize(size));
    computeOffset();
    computePage();
    logger.debug("Instanciate companyPage");
  }

  public void nextPage() {
    if (page < companies.size() / pageSize) {
      page++;
    }
    logger.debug("Next page. Now :" + page);
  }

  public void previousPage() {
    if (page > 0) {
      page--;
    }
    logger.info("Prev page. Now :" + page);
  }

  public List<CompanyDTO> getCurrentPage() {
    computeOffset();
    computePage();
    logger.debug(currentPage.size() + "");
    return currentPage;
  }

  private void computeOffset() {
    offset = pageSize * page;
    logger.debug("Update offset :" + offset);
  }

  private void computePage() {
    logger.debug("Offset " + offset);
    if (offset < companies.size() && offset >= 0) {
      logger.debug("Computing company page");
      try {
        currentPage = cService.paginatedList(pageSize, offset);
      }
      catch (PropertiesNotFoundException e) {
        logger.error("Connection error, couldn't conenct to database");
      }
      logger.debug("Page size " + currentPage.size());
    }
  }

  public Integer getOffset() {
    return offset;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPage() {
    return page;
  }

  public List<CompanyDTO> getCompanies() {
    return companies;
  }

  public void setCompanies(List<CompanyDTO> companies) {
    this.companies = companies;
  }

}

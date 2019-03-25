package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistence.DAOCompany;
import persistence.DAOFactory;

public class CompanyPage {

  Logger logger = LoggerFactory.getLogger(CompanyPage.class);

  private Integer       pageSize    = 10;
  private Integer       offset      = 0;
  private Integer       page        = 0;
  private DAOCompany    dao         = DAOFactory.INSTANCE.getDAOCompany();
  private List<Company> currentPage = new ArrayList<Company>();

  public CompanyPage(Integer pageSize) {
    super();
    Optional.of(pageSize).filter(val -> val >= 1).ifPresent(size -> setPageSize(size));
    computeOffset();
    computePage();
    this.logger.debug("Instanciate companyPage");
  }

  public void nextPage() {
    if (this.page < this.dao.count() / this.pageSize) {
      this.page++;
    }
    this.logger.debug("Next page. Now :" + this.page);
  }

  public void previousPage() {
    if (this.page > 0) {
      this.page--;
    }
    this.logger.info("Prev page. Now :" + this.page);
  }

  public List<Company> getCurrentPage() {
    computeOffset();
    computePage();
    this.logger.debug(this.currentPage.size() + "");
    return this.currentPage;
  }

  private void computeOffset() {
    this.offset = this.pageSize * this.page;
    this.logger.debug("Update offset :" + this.offset);
  }

  private void computePage() {
    this.logger.debug("Offset " + this.offset);
    if (this.offset < this.dao.count() && this.offset >= 0) {
      this.logger.debug("Computing company page");
      this.currentPage = this.dao.paginatedList(this.pageSize, this.offset);
      this.logger.debug("Page size " + this.currentPage.size());
    }
  }

  public Integer getOffset() {
    return this.offset;
  }

  public Integer getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPage() {
    return this.page;
  }

}

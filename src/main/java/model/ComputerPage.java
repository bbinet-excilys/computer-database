package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.PropertiesNotFoundException;
import persistence.DAOComputer;

public class ComputerPage {

  private Logger logger = LoggerFactory.getLogger(ComputerPage.class);

  private Integer        pageSize    = 10;
  private Integer        offset      = 0;
  private Integer        page        = 1;
  private DAOComputer    dao         = new DAOComputer();
  private List<Computer> currentPage = new ArrayList<>();

  public ComputerPage(int pageSize) throws PropertiesNotFoundException {
    super();
    Optional.of(pageSize).filter(val -> val >= 1).ifPresent(size -> setPageSize(size));
    computeOffset();
    computePage();
    logger.debug("Instanciate companyPage");
  }

  public void nextPage() throws PropertiesNotFoundException {
    if (page < dao.count() / pageSize) {
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

  public List<Computer> getCurrentPage() throws PropertiesNotFoundException {
    computeOffset();
    computePage();
    logger.debug(currentPage.size() + "");
    return currentPage;
  }

  private void computeOffset() {
    offset = pageSize * (page - 1);
    logger.debug("Update offset :" + offset);
  }

  private void computePage() throws PropertiesNotFoundException {
    logger.debug("Offset " + offset);
    if (offset < dao.count() && offset >= 0) {
      logger.debug("Computing company page");
      currentPage = dao.paginatedList(pageSize, offset);
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

  public void setPage(Integer page) {
    this.page = page;
  }

}

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.ComputerDTO;
import exception.PropertiesNotFoundException;
import service.ComputerService;

public class ComputerPage {

  private Logger logger = LoggerFactory.getLogger(ComputerPage.class);

  private Integer           pageSize    = 10;
  private Integer           offset      = 0;
  private Integer           page        = 1;
  private ComputerService   cService    = new ComputerService();
  private List<ComputerDTO> currentPage = new ArrayList<>();
  private List<ComputerDTO> computers;

  public ComputerPage(Integer pageSize) {
    super();
    this.pageSize = pageSize;
  }

  public void nextPage() throws PropertiesNotFoundException {
    if (page < computers.size() / pageSize) {
      page++;
    }
    logger.debug("Next page. Now :" + page);
  }

  public void previousPage() {
    if (page > 0) {
      page--;
    }
    logger.debug("Prev page. Now :" + page);
  }

  public List<ComputerDTO> getCurrentPage() throws PropertiesNotFoundException {
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
    if (offset < computers.size() && offset >= 0) {
      logger.debug("Computing company page");
      currentPage = computers.stream().skip(offset).limit(pageSize).collect(Collectors.toList());
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

  public List<ComputerDTO> getComputers() {
    return computers;
  }

  public void setComputers(List<ComputerDTO> computers) {
    this.computers = computers;
  }

}

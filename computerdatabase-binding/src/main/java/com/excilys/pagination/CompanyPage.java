package com.excilys.pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.CompanyDTO;

public class CompanyPage {

  Logger logger = LoggerFactory.getLogger(CompanyPage.class);

  private Integer          pageSize    = 10;
  private Integer          offset      = 0;
  private Integer          page        = 0;
  private List<CompanyDTO> companies   = new ArrayList<>();
  private List<CompanyDTO> currentPage = new ArrayList<>();

  public CompanyPage(Integer pageSize) {
    super();
    Optional.of(pageSize).filter(val -> val >= 1).ifPresent(size -> setPageSize(size));
    computeOffset();
    computePage();
  }

  public void nextPage() {
    if (page < companies.size() / pageSize) {
      page++;
    }
  }

  public void previousPage() {
    if (page > 0) {
      page--;
    }
  }

  public List<CompanyDTO> getCurrentPage() {
    computeOffset();
    computePage();
    return currentPage;
  }

  private void computeOffset() {
    offset = pageSize * page;
  }

  private void computePage() {
    if (offset < companies.size() && offset >= 0) {
      currentPage = companies.stream().skip(offset).limit(pageSize).collect(Collectors.toList());
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

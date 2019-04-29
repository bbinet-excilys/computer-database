package com.excilys.dto;

public class DashboardDTO<T> {

  private Integer page       = 1;
  private Integer pageSize   = 10;
  private Integer pageMax;
  private Integer count;
  private String  searchName = "";

  public DashboardDTO() {
    super();
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Integer getPageMax() {
    return pageMax;
  }

  public void setPageMax(Integer pageMax) {
    this.pageMax = pageMax;
  }

  public String getSearchName() {
    return searchName;
  }

  public void setSearchName(String searchName) {
    this.searchName = searchName;
  }

}

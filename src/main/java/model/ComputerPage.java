package model;

import java.util.ArrayList;
import java.util.List;

import persistence.DAOComputer;
import persistence.DAOFactory;

public class ComputerPage {

  private Integer pageSize;
  private Integer offset;

  public List<Computer> getPageN(int page) {
    List<Computer> lComputer = new ArrayList<Computer>();
    DAOComputer    dao       = DAOFactory.INSTANCE.getDAOComputer();
    Integer        offset    = getPageSize() * (page - 1);
    if (offset < dao.count()) {
      lComputer = dao.paginatedList(getPageSize(), offset);
    }
    return lComputer;
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(Integer pageSize) {
    if (pageSize == null) {
      throw new IllegalArgumentException("PageSize can't be null");
    }
    else if (pageSize < 1) {
      throw new IllegalArgumentException("PageSize must be at least 1");
    }
    this.pageSize = pageSize;
  }

}

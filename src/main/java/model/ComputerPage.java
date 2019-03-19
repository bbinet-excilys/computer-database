package model;

import java.util.ArrayList;
import java.util.List;

import persistence.DAOComputer;
import persistence.DAOFactory;

public class ComputerPage extends EntityPage {

  @Override
  public List getPageN(int page) {
    List<Computer> lComputer = new ArrayList<Computer>();
    DAOComputer    dao       = (DAOComputer) DAOFactory.INSTANCE.getDao(DAOFactory.DAO_COMPUTER);
    Integer        offset    = getPageSize() * (page - 1);
    if (offset < dao.count()) {
      lComputer = dao.paginatedList(getPageSize(), offset);
    }
    return lComputer;
  }

}

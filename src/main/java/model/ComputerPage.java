package model;

import java.util.ArrayList;
import java.util.List;

import persistence.DAO;
import persistence.DAOFactory;

public class ComputerPage extends EntityPage {

  @Override
  public List<Entity> getPageN(int page) {
    List<Entity> lComputer = new ArrayList<Entity>();
    DAO          dao       = DAOFactory.COMPUTER.getDAO();
    Integer      offset    = getPageSize() * (page - 1);
    if (offset < dao.count()) {
      lComputer = dao.paginatedList(getPageSize(), offset);
    }
    return lComputer;
  }

}

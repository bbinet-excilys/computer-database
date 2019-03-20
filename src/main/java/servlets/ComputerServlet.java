package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ComputerPage;
import model.Entity;
import persistence.DAOFactory;

/**
 * Servlet implementation class ComputerServlet
 */
@WebServlet("/ComputerServlet")
public class ComputerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public ComputerServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Entity> computers = DAOFactory.COMPUTER.getDAO().list();
    Integer      page      = (request.getParameter("page") == null) ? 1
        : Integer.parseInt(request.getParameter("page"));
    Integer      pageSize  = (request.getParameter("pageSize") == null) ? 10
        : Integer.parseInt(request.getParameter("pageSize"));
    Integer      cCount    = DAOFactory.COMPUTER.getDAO().count();
    if (page * pageSize < cCount) {
      ComputerPage cPage = new ComputerPage();
      cPage.setPageSize(pageSize);
      computers = cPage.getPageN(page);
      Integer pageMax = (cCount / cPage.getPageSize()) + 1;
      request.setAttribute("computers", computers);
      request.setAttribute("page", page);
      request.setAttribute("pageMax", pageMax);
      getServletContext().getRequestDispatcher("/Views/dashboard.jsp").forward(request, response);
    }
    else {
      getServletContext().getRequestDispatcher("/Views/403.jsp").forward(request, response);
    }

  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    doGet(request, response);
  }

}

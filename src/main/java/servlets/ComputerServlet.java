package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Computer;
import model.ComputerPage;
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
    List<Computer> computers = DAOFactory.INSTANCE.getDao(DAOFactory.DAO_COMPUTER).list();
    String         sPage     = request.getParameter("page");
    Integer        page      = 1;
    if (sPage != null && !sPage.trim().isEmpty()) {
      page = Integer.parseInt(sPage);
    }
    ComputerPage cPage = new ComputerPage();
    cPage.setPageSize(10);
    computers = cPage.getPageN(page);
    request.setAttribute("computers", computers);
    request.setAttribute("page", page);
    getServletContext().getRequestDispatcher("/Views/dashboard.jsp").forward(request, response);
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

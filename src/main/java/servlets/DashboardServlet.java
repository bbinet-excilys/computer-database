package servlets;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
@WebServlet(
    name = "dashboard",
    urlPatterns = { "/Dashboard",
        "/dashboard" },
    description = "The main page of the WebUI")
public class DashboardServlet extends HttpServlet {

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DashboardServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    List<Computer> computers = DAOFactory.INSTANCE.getDAOComputer().list();
    Integer        page      = Integer
        .parseInt(Optional.ofNullable(request.getParameter("page")).orElseGet(() -> {
                                   return "1";
                                 }));
    Integer        pageSize  = Integer
        .parseInt(Optional.ofNullable(request.getParameter("pageSize")).orElseGet(() -> {
                                   return "10";
                                 }));
    Double         cCount    = (double) DAOFactory.INSTANCE.getDAOComputer().count();
    Integer        pageMax   = (int) Math.ceil((cCount / pageSize));
    if (page <= pageMax && page > 0) {
      ComputerPage cPage = new ComputerPage(pageSize);
      cPage.setPage(page);
      computers = cPage.getCurrentPage();
      request.setAttribute("computers", computers);
      request.setAttribute("page", page);
      request.setAttribute("pageMax", pageMax);
      request.setAttribute("pageSize", pageSize);
      getServletContext().getRequestDispatcher("/Views/dashboard.jsp").forward(request, response);
    }
    else {
      getServletContext().getRequestDispatcher("/Views/404.jsp").forward(request, response);
    }

  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    doGet(request, response);
  }

}
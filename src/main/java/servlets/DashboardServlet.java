package servlets;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.ComputerDTO;
import exception.DAOUnexecutedQuery;
import exception.PropertiesNotFoundException;
import model.ComputerPage;
import service.ComputerService;

/**
 * Servlet implementation class ComputerServlet.
 */
@WebServlet(
  name = "dashboard",
  urlPatterns = { "/Dashboard",
      "/dashboard" },
  description = "The main page of the WebUI")
public class DashboardServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DashboardServlet() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    List<ComputerDTO> computers;
    try {
      ComputerService computerService = new ComputerService();
      Integer         page            = Integer.parseInt(Optional.ofNullable(request.getParameter("page"))
                                                                 .orElse("1"));
      Integer         pageSize        = Integer.parseInt(Optional.ofNullable(request.getParameter("pageSize"))
                                                                 .orElse("10"));
      ComputerPage    computerPage    = new ComputerPage(pageSize);
      Optional.ofNullable(request.getParameter("searchName")).ifPresentOrElse(searchedName -> {
        try {
          computerPage.setComputers(computerService.paginatedSearchByNameList(searchedName));

        }
        catch (PropertiesNotFoundException e) {
          ServletUtils.setErrorMessage(request, "Connection error",
                                       "Couldn't connect to database, contact administrator");
        }
        catch (DAOUnexecutedQuery e) {
          ServletUtils.setErrorMessage(request, "Query error",
                                       "Couldn't execute select query " + e.getMessage());
        }
      }, () -> {
        try {
          computerPage.setComputers(computerService.list());
        }
        catch (PropertiesNotFoundException e) {
          ServletUtils.setErrorMessage(request, "Connection error",
                                       "Couldn't connect to database, contact administrator");
        }
      });
      computerPage.setPage(page);
      computers = computerPage.getCurrentPage();
      if (computers.size() != 0) {
        Double  computerCount = (double) computerPage.getComputers().size();
        Integer pageMax       = (int) Math.ceil((computerCount / pageSize));
        request.setAttribute("computers", computers);
        request.setAttribute("page", page);
        request.setAttribute("pageMax", pageMax);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("count", computerCount.intValue());
        getServletContext().getRequestDispatcher("/Views/dashboard.jsp")
                           .forward(request, response);
      }
      else {
        getServletContext().getRequestDispatcher("/Views/404.jsp").forward(request, response);
      }
    }
    catch (PropertiesNotFoundException e) {
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

    doGet(request, response);
  }

}

package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  urlPatterns = { "/Dashboard", "/dashboard" },
  description = "The main page of the WebUI"
)
public class DashboardServlet extends HttpServlet implements IServlet {

  private static final long serialVersionUID = 1L;

  private final String VIEW = "/Views/dashboard.jsp";

  private final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);

  private ComputerService computerService;

  public void setComputerService(ComputerService computerService) {
    this.computerService = computerService;
  }

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
    if (computerService == null) {
      computerService = getComputerService();
    }
    List<ComputerDTO> computers;
    try {
      Integer      page         = Integer.parseInt(Optional.ofNullable(request.getParameter("page"))
                                                           .orElse("1"));
      Integer      pageSize     = Integer.parseInt(Optional.ofNullable(request.getParameter("pageSize"))
                                                           .orElse("10"));
      ComputerPage computerPage = new ComputerPage(pageSize);
      Optional.ofNullable(request.getParameter("searchName")).ifPresentOrElse(searchedName -> {
        try {
          computerPage.setComputers(computerService.paginatedSearchByNameList(searchedName));

        }
        catch (PropertiesNotFoundException e) {
          setErrorMessage(request, MSG_TITLE_ERROR_CONNECTION, MSG_CONTENT_ERROR_CONNECTION);
        }
        catch (DAOUnexecutedQuery e) {
          setErrorMessage(request, MSG_TITLE_ERROR_QUERY, MSG_CONTENT_ERROR_QUERY);
        }
      }, () -> {
        try {
          List<ComputerDTO> computerList = computerService.list();
          computerPage.setComputers(computerList);
        }
        catch (PropertiesNotFoundException e) {
          setErrorMessage(request, MSG_TITLE_ERROR_CONNECTION, MSG_CONTENT_ERROR_CONNECTION);
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
        request.setAttribute("count", computerCount);
        getServletContext().getRequestDispatcher(VIEW)
                           .forward(request, response);
      }
      else {
        getServletContext().getRequestDispatcher(PATH_PAGE_404).forward(request, response);
      }
    }
    catch (PropertiesNotFoundException e) {
      getServletContext().getRequestDispatcher(PATH_PAGE_404).forward(request, response);
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

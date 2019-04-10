package servlet;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.ComputerDTO;
import exception.DAOUnexecutedQuery;
import exception.PropertiesNotFoundException;
import service.ComputerService;

/**
 * Servlet implementation class DetailsComputer
 */
@WebServlet(
  name = "detailsComputer",
  urlPatterns = { "/detailsComputer", "/detailscomputer" },
  description = "Computer details page"
)

public class ReadComputerServlet extends HttpServlet implements IServlet {
  private static final long serialVersionUID = 1L;

  private final String VIEW = "/Views/detailsComputer.jsp";

  ComputerService computerService;

  public void setComputerService(ComputerService computerService) {
    this.computerService = computerService;
  }

  public ReadComputerServlet() {}

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    Optional<Long> oComputerId = Optional.ofNullable(request.getParameter("computerId"))
                                         .filter(Predicate.not(String::isBlank))
                                         .map(Long::parseLong);
    oComputerId.ifPresentOrElse(computerId -> {
      try {
        Optional<ComputerDTO> oComputer = computerService.read(computerId);
        oComputer.ifPresent(computer -> {
          request.setAttribute("computer", computer);
        });
      }
      catch (DAOUnexecutedQuery e) {
        setErrorMessage(request, MSG_TITLE_ERROR_QUERY, MSG_CONTENT_ERROR_QUERY);
      }
      catch (PropertiesNotFoundException e) {
        setErrorMessage(request, MSG_TITLE_ERROR_CONNECTION, MSG_CONTENT_ERROR_CONNECTION);
      }
    }, () -> {
      setErrorMessage(request, MSG_TITLE_ERROR_PARAMETER,
                      String.format(MSG_CONTENT_ERROR_PARAMETER, "ID"));
    });
    getServletContext().getRequestDispatcher(VIEW)
                       .forward(request, response);
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

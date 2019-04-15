package servlet;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

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
 * Servlet implementation class DeleteComputer
 */
@WebServlet(
  name = "deleteComputer",
  urlPatterns = { "/deleteComputer", "/deletecomputer" },
  description = "Deletion computer page"
)
public class DeleteComputerServlet extends HttpServlet implements IServlet {

  private static final long serialVersionUID = 1L;

  private final String VIEW = "/Views/deleteComputer.jsp";

  private ComputerService computerService;

  public void setComputerService(ComputerService computerService) {
    this.computerService = computerService;
  }

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DeleteComputerServlet() {}

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
    try {
      request.setAttribute("computers", computerService.list());
    }
    catch (PropertiesNotFoundException e) {
      setErrorMessage(request, "Connection Error", "Couldn't set the connection to the database");
    }
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

    if (computerService == null) {
      computerService = getComputerService();
    }

    Optional<Long> oComputerId = Optional.of(request.getParameter("computerId"))
                                         .filter(Predicate.not(String::isBlank))
                                         .map(param -> Optional.of(Long.parseLong(param)))
                                         .get();
    oComputerId.ifPresent(computerId -> {
      Optional<ComputerDTO> oComputer;
      try {
        oComputer = computerService.read(computerId);
        oComputer.ifPresent(computer -> {
          try {
            computerService.delete(computer);
            setSuccessMessage(request, MSG_TITLE_SUCCESS, String.format(MSG_CONTENT_SUCCESS_DELETE,
                                                                        computer.getName()));
          }
          catch (DAOUnexecutedQuery e) {
            setErrorMessage(request, MSG_TITLE_ERROR_QUERY, MSG_CONTENT_ERROR_QUERY);

          }
          catch (PropertiesNotFoundException e) {
            setErrorMessage(request, MSG_TITLE_ERROR_CONNECTION, MSG_CONTENT_ERROR_CONNECTION);

          }
          catch (IllegalArgumentException e) {
            setErrorMessage(request, MSG_TITLE_ERROR_PARAMETER,
                            String.format(MSG_CONTENT_ERROR_PARAMETER, e.getMessage()));
          }
        });
      }
      catch (DAOUnexecutedQuery e) {
        setErrorMessage(request, MSG_TITLE_ERROR_QUERY, MSG_CONTENT_ERROR_QUERY);

      }
      catch (PropertiesNotFoundException e1) {
        setErrorMessage(request, MSG_TITLE_ERROR_CONNECTION, MSG_CONTENT_ERROR_CONNECTION);

      }
    });
    doGet(request, response);
  }
}

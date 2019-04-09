package servlets;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.ComputerDTO;
import dto.MessageDTO;
import dto.MessageDTO.MessageDTOBuilder;
import exception.DAOUnexecutedQuery;
import exception.PropertiesNotFoundException;
import service.ComputerService;

/**
 * Servlet implementation class DetailsComputer
 */
@WebServlet(
  name = "detailsComputer",
  urlPatterns = { "/detailsComputer", "/detailscomputer" },
  description = "Computer details page")

public class DetailsComputer extends HttpServlet {
  private static final long serialVersionUID = 1L;

  ComputerService computerService = new ComputerService();

  public DetailsComputer() {}

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
        setErrorMessage(request, "Database error",
                        String.format("The computer has not been found %s", e.getMessage()));
      }
      catch (PropertiesNotFoundException e) {
        setErrorMessage(request, "Connection error", "The connection has failed");
      }
    }, () -> {
      setErrorMessage(request, "Parameter error", "Couldn't parse or find the ID");
    });
    getServletContext().getRequestDispatcher("/Views/detailsComputer.jsp")
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

  public void setErrorMessage(HttpServletRequest request, String title, String message) {
    MessageDTOBuilder mDTOBuilder = MessageDTO.builder();
    mDTOBuilder.withType(MessageDTO.ERROR_TYPE);
    mDTOBuilder.withTitle(title);
    mDTOBuilder.withContent(message);
    request.setAttribute("message", mDTOBuilder.build());
  }

  public void setSuccessMessage(HttpServletRequest request, String title, String message) {
    MessageDTOBuilder mDTOBuilder = MessageDTO.builder();
    mDTOBuilder.withType(MessageDTO.SUCCESS_TYPE);
    mDTOBuilder.withTitle(title);
    mDTOBuilder.withContent(message);
    request.setAttribute("message", mDTOBuilder.build());
  }

}

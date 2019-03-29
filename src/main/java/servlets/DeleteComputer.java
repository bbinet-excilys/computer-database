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
 * Servlet implementation class DeleteComputer
 */
@WebServlet(
  name = "deleteComputer",
  urlPatterns = { "/deleteComputer", "/deletecomputer" },
  description = "Deletion computer page")
public class DeleteComputer extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private ComputerService computerService = new ComputerService();

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DeleteComputer() {}

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      request.setAttribute("computers", computerService.list());
    }
    catch (PropertiesNotFoundException e) {
      setErrorMessage(request, "Connection Error", "Couldn't set the connection to the database");
    }
    getServletContext().getRequestDispatcher("/Views/deleteComputer.jsp")
                       .forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

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
            setSuccessMessage(request, "Success", String.format("The computer %s has been deleted",
                                                                computer.getName()));
          }
          catch (DAOUnexecutedQuery e) {
            setErrorMessage(request, "Execution Error",
                            "Couldn't delete the computer " + e.getMessage());
          }
          catch (PropertiesNotFoundException e) {
            setErrorMessage(request, "Connection Error",
                            "The connection to the database could not be established");
          }
          catch (IllegalArgumentException e) {
            setErrorMessage(request, "Parameter error", e.getMessage());
          }
        });
      }
      catch (DAOUnexecutedQuery e) {
        setErrorMessage(request, "Execution Error",
                        "Couldn't find the computer to delete :" + e.getMessage());
      }
      catch (PropertiesNotFoundException e1) {
        setErrorMessage(request, "Connection Error",
                        "The connection to the database could not be established");
      }
    });
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

package servlets;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.MessageDTO;
import dto.MessageDTO.MessageDTOBuilder;
import exception.DAOUnexecutedQuery;
import model.Computer;
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
    request.setAttribute("computers", this.computerService.list());
    getServletContext()
                       .getRequestDispatcher("/Views/deleteComputer.jsp")
                       .forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Optional<Long>    oComputerId = Optional
                                            .of(request.getParameter("computerId"))
                                            .filter(Predicate.not(String::isBlank))
                                            .map(param -> Optional.of(Long.parseLong(param)))
                                            .get();
    MessageDTOBuilder mDTOBuilder = MessageDTO.builder();
    mDTOBuilder.withType(MessageDTO.ERROR_TYPE);
    mDTOBuilder.withTitle("Error");
    oComputerId.ifPresent(computerId -> {
      Optional<Computer> oComputer;
      try {
        oComputer = this.computerService.read(computerId);
        oComputer.ifPresent(computer -> {
          try {
            this.computerService.delete(computer);
            mDTOBuilder.withType(MessageDTO.SUCCESS_TYPE);
            mDTOBuilder.withTitle("Success");
            mDTOBuilder.withContent("Computer " + computer.getName()
                + " has successfully been deleted");
          }
          catch (DAOUnexecutedQuery e) {
            mDTOBuilder.withContent("Computer " + computer.getName() + " has not been deleted "
                + e.getMessage());
          }
        });
      }
      catch (DAOUnexecutedQuery e) {
        mDTOBuilder.withContent("Computer has not been found " + e.getMessage());
      }
    });
    doGet(request, response);
  }

}

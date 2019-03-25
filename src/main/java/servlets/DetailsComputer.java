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
 * Servlet implementation class DetailsComputer
 */
@WebServlet(
    name = "detailsComputer",
    urlPatterns = { "/detailsComputer", "/detailscomputer" },
    description = "Computer details page")

public class DetailsComputer extends HttpServlet {

  ComputerService computerService = new ComputerService();

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DetailsComputer() {}

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Optional<Long>    oComputerId = Optional
        .of(request.getParameter("computerId"))
        .filter(Predicate.not(String::isBlank))
        .map(Long::parseLong);
    MessageDTOBuilder mDTOBuilder = new MessageDTOBuilder();
    oComputerId.ifPresentOrElse(computerId -> {
      try {
        Optional<Computer> oComputer = this.computerService.read(computerId);
        oComputer.ifPresent(computer -> {
          request.setAttribute("computer", computer);
        });
      }
      catch (DAOUnexecutedQuery e) {
        mDTOBuilder.setType(MessageDTO.ERROR_TYPE);
        mDTOBuilder.setTitle("Database error");
        mDTOBuilder.setContent("The computer has not been found " + e.getMessage());
      }
    }, () -> {
      mDTOBuilder.setType(MessageDTO.ERROR_TYPE);
      mDTOBuilder.setTitle("Parameter error");
      mDTOBuilder.setContent("Couldn't parse the id or parameter not found");
    });
    getServletContext()
        .getRequestDispatcher("/Views/detailsComputer.jsp")
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

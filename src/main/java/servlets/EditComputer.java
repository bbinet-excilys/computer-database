package servlets;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.CompanyDTO;
import dto.ComputerDTO;
import dto.ComputerDTO.ComputerDTOBuilder;
import dto.MessageDTO;
import dto.MessageDTO.MessageDTOBuilder;
import exception.DAOUnexecutedQuery;
import exception.PropertiesNotFoundException;
import service.CompanyService;
import service.ComputerService;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet(
  name = "editComputer",
  urlPatterns = { "/editComputer", "/editcomputer" },
  description = "Edit computer page")
public class EditComputer extends HttpServlet {
  private static final long serialVersionUID = 1L;

  ComputerService computerService = new ComputerService();
  CompanyService  companyService  = new CompanyService();

  /**
   * @see HttpServlet#HttpServlet()
   */
  public EditComputer() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Optional<ComputerDTO> oComputer = getRequestComputer(request);
    oComputer.ifPresent(computer -> {
      request.setAttribute("computer", computer);
    });
    List<CompanyDTO> companies = companyService.list();
    request.setAttribute("companies", companies);
    if (request.getAttribute("computer") == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      setErrorMessage(request, "Computer not found", "No computer matches in database");
      getServletContext().getRequestDispatcher("/Views/404.jsp")
                         .forward(request, response);
    }
    else {
      getServletContext().getRequestDispatcher("/Views/editComputer.jsp")
                         .forward(request, response);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    ComputerDTOBuilder    cDTOBuilder = ComputerDTO.builder();
    Optional<ComputerDTO> oComputer   = getRequestComputer(request);
    oComputer.ifPresent(computer -> {
      cDTOBuilder.withId(computer.getId());
      cDTOBuilder.withName(Optional.ofNullable(request.getParameter("computerName"))
                                   .orElseGet(() -> computer.getName()));
      cDTOBuilder.withIntroduced(Optional.ofNullable(request.getParameter("computerIntroduced"))
                                         .filter(Predicate.not(String::isBlank))
                                         .orElseGet(() -> computer.getIntroduced()));
      cDTOBuilder.withDiscontinued(Optional.ofNullable(request.getParameter("computerDiscontinued"))
                                           .filter(Predicate.not(String::isBlank))
                                           .orElseGet(() -> computer.getDiscontinued()));
      Optional.ofNullable(request.getParameter("computerCompanyId"))
              .filter(Predicate.not(String::isBlank))
              .map(Long::parseLong)
              .ifPresent(companyId -> {
                cDTOBuilder.withCompanyId(companyId);
                cDTOBuilder.withCompanyName(companyService.read(companyId)
                                                          .map(CompanyDTO::getName)
                                                          .orElseGet(() -> null));
              });
      try {
        computerService.update(cDTOBuilder.build());
        setSuccessMessage(request, "Success", String.format("Computer %s successfully updated",
                                                            cDTOBuilder.build().getName()));
      }
      catch (IllegalArgumentException e) {
        setErrorMessage(request, "Parameter Error", e.getMessage());
      }
      catch (PropertiesNotFoundException e) {
        setErrorMessage(request, "Connection error", "Couldn't connect to the database");
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

  public Optional<ComputerDTO> getRequestComputer(HttpServletRequest request) {
    // Optional<Long> id = Optional.of(request.getParameter("computerId"));
    Long computerId = Optional.ofNullable(request.getParameter("computerId"))
                              .filter(Predicate.not(String::isBlank))
                              .map(Long::parseLong)
                              .get();

    try {
      return computerService.read(computerId);
    }
    catch (DAOUnexecutedQuery e) {
      setErrorMessage(request, "Query error", "Couldn't execute the query");
      return Optional.empty();
    }
    catch (PropertiesNotFoundException e) {
      setErrorMessage(request, "Connection error", "Couldn't connect to the database");
      return Optional.empty();
    }

  }
}

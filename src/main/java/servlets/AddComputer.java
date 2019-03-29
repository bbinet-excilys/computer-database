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

import org.apache.commons.validator.routines.DateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Servlet implementation class AddComputer
 */
@WebServlet(
  name = "addComputer",
  urlPatterns = { "/addComputer", "/addcomputer" },
  description = "Add computer page")
public class AddComputer extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(AddComputer.class);

  ComputerService computerService = new ComputerService();
  CompanyService  companyService  = new CompanyService();

  private static DateValidator dValidator = new DateValidator();

  /**
   * @see HttpServlet#HttpServlet()
   */
  public AddComputer() {}

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    List<CompanyDTO> companies = companyService.list();
    request.setAttribute("companies", companies);
    getServletContext().getRequestDispatcher("/Views/addComputer.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    ComputerDTOBuilder cDTOBuilder = ComputerDTO.builder();
    cDTOBuilder.withName(Optional.ofNullable(request.getParameter("computerName"))
                                 .orElseGet(() -> null));
    cDTOBuilder.withIntroduced(Optional.ofNullable(request.getParameter("computerIntroduced"))
                                       .filter(Predicate.not(String::isBlank))
                                       .orElseGet(() -> null));
    cDTOBuilder.withDiscontinued(Optional.ofNullable(request.getParameter("computerDiscontinued"))
                                         .filter(Predicate.not(String::isBlank))
                                         .orElseGet(() -> null));
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
      computerService.create(cDTOBuilder.build());
      setSuccessMessage(request, "Success", String.format("Computer %s successfully created",
                                                          cDTOBuilder.build().getName()));
    }
    catch (IllegalArgumentException e) {
      setErrorMessage(request, "Parameter Error", e.getMessage());
    }
    catch (PropertiesNotFoundException e) {
      setErrorMessage(request, "Connection error", "Couldn't connect to the database");
    }
    catch (DAOUnexecutedQuery e) {
      setErrorMessage(request, "Query Error", "Couldn't execute the query");
    }
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

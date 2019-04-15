package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.CompanyDTO;
import dto.ComputerDTO;
import dto.ComputerDTO.ComputerDTOBuilder;
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
  description = "Add computer page"
)
public class CreateComputerServlet extends HttpServlet implements IServlet {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(CreateComputerServlet.class);

  private final String VIEW = "/Views/addComputer.jsp";

  ComputerService computerService;
  CompanyService  companyService;

  public void setComputerService(ComputerService computerService) {
    this.computerService = computerService;
  }

  public void setCompanyService(CompanyService companyService) {
    this.companyService = companyService;
  }

  /**
   * @see HttpServlet#HttpServlet()
   */
  public CreateComputerServlet() {}

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    List<CompanyDTO> companies;
    if (companyService == null || computerService == null) {
      companyService  = getCompanyService();
      computerService = getComputerService();
    }
    try {
      companies = companyService.list();
      request.setAttribute("companies", companies);
    }
    catch (PropertiesNotFoundException e) {
      setErrorMessage(request, MSG_TITLE_ERROR_CONNECTION, MSG_CONTENT_ERROR_CONNECTION);
    }
    getServletContext().getRequestDispatcher(VIEW).forward(request, response);
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
              try {
                cDTOBuilder.withCompanyName(companyService.read(companyId)
                                                          .map(CompanyDTO::getName)
                                                          .orElseGet(() -> null));
              }
              catch (PropertiesNotFoundException e) {
                setErrorMessage(request, MSG_TITLE_ERROR_CONNECTION, MSG_CONTENT_ERROR_CONNECTION);
              }
            });
    try {
      computerService.create(cDTOBuilder.build());
      setSuccessMessage(request, MSG_TITLE_SUCCESS, String.format(MSG_CONTENT_SUCCESS_CREATE,
                                                                  cDTOBuilder.build().getName()));
    }
    catch (IllegalArgumentException e) {
      setErrorMessage(request, MSG_TITLE_ERROR_PARAMETER,
                      String.format(MSG_CONTENT_ERROR_PARAMETER, e.getMessage()));
    }
    catch (PropertiesNotFoundException e) {
      setErrorMessage(request, MSG_TITLE_ERROR_CONNECTION, MSG_CONTENT_ERROR_CONNECTION);
    }
    catch (DAOUnexecutedQuery e) {
      setErrorMessage(request, MSG_TITLE_ERROR_QUERY, MSG_CONTENT_ERROR_QUERY);
    }
    doGet(request, response);
  }

}

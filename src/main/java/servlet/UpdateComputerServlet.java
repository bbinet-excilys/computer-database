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

import dto.CompanyDTO;
import dto.ComputerDTO;
import dto.ComputerDTO.ComputerDTOBuilder;
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
  description = "Edit computer page"
)
public class UpdateComputerServlet extends HttpServlet implements IServlet {
  private static final long serialVersionUID = 1L;

  private final String VIEW = "/Views/editComputer.jsp";

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
  public UpdateComputerServlet() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    if (companyService == null || computerService == null) {
      companyService  = getCompanyService();
      computerService = getComputerService();
    }
    Optional<ComputerDTO> oComputer = getRequestComputer(request);
    oComputer.ifPresent(computer -> {
      request.setAttribute("computer", computer);
    });
    List<CompanyDTO> companies;
    try {
      companies = companyService.list();
      request.setAttribute("companies", companies);
    }
    catch (PropertiesNotFoundException e) {
      setErrorMessage(request, MSG_TITLE_ERROR_CONNECTION, MSG_CONTENT_ERROR_CONNECTION);
    }
    if (request.getAttribute("computer") == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      setErrorMessage(request, MSG_TITLE_NOT_FOUND,
                      String.format(MSG_CONTENT_NOT_FOUND, "a computer"));
      getServletContext().getRequestDispatcher(PATH_PAGE_404)
                         .forward(request, response);
    }
    else {
      getServletContext().getRequestDispatcher(VIEW)
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
                try {
                  cDTOBuilder.withCompanyName(companyService.read(companyId)
                                                            .map(CompanyDTO::getName)
                                                            .orElseGet(() -> null));
                }
                catch (PropertiesNotFoundException e) {
                  setErrorMessage(request, MSG_TITLE_ERROR_CONNECTION,
                                  MSG_CONTENT_ERROR_CONNECTION);
                }
              });
      try {
        computerService.update(cDTOBuilder.build());
        setSuccessMessage(request, MSG_TITLE_SUCCESS, String.format(MSG_CONTENT_SUCCESS_UPDATE,
                                                                    cDTOBuilder.build().getName()));
      }
      catch (IllegalArgumentException e) {
        setErrorMessage(request, MSG_TITLE_ERROR_PARAMETER,
                        String.format(MSG_CONTENT_ERROR_PARAMETER, e.getMessage()));
      }
      catch (PropertiesNotFoundException e) {
        setErrorMessage(request, MSG_TITLE_ERROR_CONNECTION, MSG_CONTENT_ERROR_CONNECTION);
      }
    });
    doGet(request, response);
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
      setErrorMessage(request, MSG_TITLE_ERROR_QUERY, MSG_CONTENT_ERROR_QUERY);
      return Optional.empty();
    }
    catch (PropertiesNotFoundException e) {
      setErrorMessage(request, MSG_CONTENT_ERROR_CONNECTION, MSG_CONTENT_ERROR_CONNECTION);
      return Optional.empty();
    }

  }

}

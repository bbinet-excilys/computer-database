package servlets;

import java.io.IOException;
import java.sql.Date;
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

import dto.MessageDTO;
import dto.MessageDTO.MessageDTOBuilder;
import exception.DAOUnexecutedQuery;
import model.Company;
import model.Computer;
import model.Computer.ComputerBuilder;
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

  MessageDTO message;

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
    List<Company> companies = companyService.list();
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
    Optional<Object> oName = Optional.ofNullable(request.getParameter("computerName"));
    oName.ifPresentOrElse(name -> {
      String          sName    = (String) name;
      ComputerBuilder cBuilder = Computer.builder();
      cBuilder.withName(sName);

      Optional<String> oIntroduced = Optional.ofNullable(request.getParameter("computerIntroduced"));
      oIntroduced.filter(Predicate.not(String::isBlank))
                 .ifPresent(sIntroduced -> {
                   Date introduced = new Date(dValidator.validate(sIntroduced, "yyyy-MM-dd")
                                                        .getTime());
                   cBuilder.withIntroduced(introduced);
                 });

      Optional<String> oDiscontinued = Optional.ofNullable(request.getParameter("computerDiscontinued"));
      oDiscontinued.filter(Predicate.not(String::isBlank))
                   .ifPresent(sDiscontinued -> {
                     Date discontinued = new Date(dValidator.validate(sDiscontinued, "yyyy-MM-dd")
                                                            .getTime());
                     cBuilder.withDiscontinued(discontinued);
                   });

      Optional<String> oCompanyId = Optional.ofNullable(request.getParameter("computerCompanyId"));
      oCompanyId.filter(Predicate.not(String::isBlank))
                .ifPresent(sCompanyId -> {
                  Long    id       = Long.parseLong(sCompanyId);
                  Optional<Company> oCompany = companyService.read(id);
                  oCompany.ifPresent(company -> {
                    cBuilder.withCompany(company);
                  });
                });
      ComputerService cService = new ComputerService();
      try {
        cService.create(cBuilder.build());
        setSuccessMessage("Success !",
                          String.format("Computer %s created", sName));
      }
      catch (DAOUnexecutedQuery e) {
        setErrorMessage("Unexecuted Query",
                        "An error occured, the computer has not been created : " + e.getMessage());
      }
      catch (IllegalArgumentException e) {
        setErrorMessage("Illegal Argument",
                        "An error occured, the computer has not been created : " + e.getMessage());
      }
    }, () -> {
      setErrorMessage("Computer not created",
                      "An error occured, the computer has not been created");
    });
    request.setAttribute("message", message);
    doGet(request, response);
  }

  public void setErrorMessage(String title, String message) {
    MessageDTOBuilder mDTOBuilder = MessageDTO.builder();
    mDTOBuilder.withType(MessageDTO.ERROR_TYPE);
    mDTOBuilder.withTitle(title);
    mDTOBuilder.withContent(message);
    this.message = mDTOBuilder.build();
  }

  public void setSuccessMessage(String title, String message) {
    MessageDTOBuilder mDTOBuilder = MessageDTO.builder();
    mDTOBuilder.withType(MessageDTO.SUCCESS_TYPE);
    mDTOBuilder.withTitle(title);
    mDTOBuilder.withContent(message);
    this.message = mDTOBuilder.build();
  }

}

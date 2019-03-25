package servlets;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import model.Company;
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

  ComputerService computerService = new ComputerService();
  CompanyService  companyService  = new CompanyService();

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
    List<Company> companies = this.companyService.list();
    request.setAttribute("companies", companies);
    String            name        = new String("" + request.getAttribute("name"));
    MessageDTOBuilder mDTOBuilder = new MessageDTOBuilder();
    if (name.isBlank()) {
      mDTOBuilder.setTitle(MessageDTO.ERROR_TYPE);
      mDTOBuilder.setContent("Couldn't create the computer.");
    }
    else {
      mDTOBuilder.setTitle(MessageDTO.SUCCESS_TYPE);
      mDTOBuilder.setContent("Successfully created the computer.");
    }
    getServletContext().getRequestDispatcher("/Views/addComputer.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Optional<Object>  oName       = Optional.ofNullable(request.getParameter("name"));
    MessageDTOBuilder mDTOBuilder = new MessageDTOBuilder();
    oName.ifPresentOrElse(name -> {
      String          sName    = (String) name;
      ComputerBuilder cBuilder = new ComputerBuilder();
      cBuilder.setName(sName);

      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      Optional<String> oIntroduced = Optional.ofNullable(request.getParameter("introduced"));
      oIntroduced
          .filter(Predicate.not(String::isBlank))
          .filter(str -> str.matches("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}"))
          .ifPresent(sIntroduced -> {
            LocalDate date   = LocalDate.parse(sIntroduced, dtf);
            Date  introduced = new Date(
                date.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000);
            cBuilder.setIntroduced(introduced);
          });

      Optional<String> oDiscontinued = Optional.ofNullable(request.getParameter("discontinued"));
      oDiscontinued
          .filter(Predicate.not(String::isBlank))
          .filter(str -> str.matches("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}"))
          .ifPresent(sDiscontinued -> {
            LocalDate parsedDate = LocalDate.parse(sDiscontinued, dtf);
            Date  discontinued = new Date(
                parsedDate.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000);
            cBuilder.setDiscontinued(discontinued);
          });

      Optional<String> oCompanyId = Optional.ofNullable(request.getParameter("companyId"));
      oCompanyId.filter(Predicate.not(String::isBlank)).ifPresent(sCompanyId -> {
        Long              id       = Long.parseLong(sCompanyId);
        Optional<Company> oCompany = this.companyService.read(id);
        oCompany.ifPresent(company -> {
          cBuilder.setCompany(company);
        });
      });
      ComputerService cService = new ComputerService();
      try {
        cService.create(cBuilder.build());
        mDTOBuilder.setType(MessageDTO.SUCCESS_TYPE);
        mDTOBuilder.setTitle(String.format("Computer %s created", sName));
        mDTOBuilder.setContent("A computer has successfully been created");
      }
      catch (DAOUnexecutedQuery e) {
        mDTOBuilder.setType(MessageDTO.ERROR_TYPE);
        mDTOBuilder.setTitle("Computer not created");
        mDTOBuilder
            .setContent("An error occured, the computer has not been created :" + e.getMessage());
      }
    }, () -> {
      mDTOBuilder.setType(MessageDTO.ERROR_TYPE);
      mDTOBuilder.setTitle("Computer not created");
      mDTOBuilder.setContent("An error occured, the computer has not been created");
    });
    request.setAttribute("message", mDTOBuilder.build());
    doGet(request, response);
  }

}

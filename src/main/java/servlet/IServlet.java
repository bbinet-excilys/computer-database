package servlet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dto.MessageDTO;
import dto.MessageDTO.MessageDTOBuilder;
import service.CompanyService;
import service.ComputerService;

public interface IServlet {

  String MSG_TITLE_ERROR_CONNECTION = "Connection Error";
  String MSG_TITLE_ERROR_QUERY      = "Query Error";
  String MSG_TITLE_ERROR_PARAMETER  = "Parameter Error";

  String MSG_TITLE_SUCCESS = "Success !!!";

  String MSG_TITLE_NOT_FOUND    = "Not Found";
  String MSG_TITLE_UNAUTHORIZED = "Unauthorized";

  String MSG_CONTENT_ERROR_CONNECTION = "Couldn't establish connection to the database, please contact the administrator";
  String MSG_CONTENT_ERROR_QUERY      = "Couldn't execute the query, please contact the administrator";
  String MSG_CONTENT_ERROR_PARAMETER  = "Error using parameter %s";

  String MSG_CONTENT_SUCCESS_CREATE = "Everything is fine, %s was successfully created";
  String MSG_CONTENT_SUCCESS_UPDATE = "Everything is fine, %s was successfully updated";
  String MSG_CONTENT_SUCCESS_DELETE = "Everything is fine, %s was successfully deleted";

  String MSG_CONTENT_NOT_FOUND    = "Couldn't find %s";
  String MSG_CONTENT_UNAUTHORIZED = "You do not have the permission to access %s";

  String PATH_PAGE_403 = "/Views/403.jsp";
  String PATH_PAGE_404 = "/Views/404.jsp";
  String PATH_PAGE_500 = "/Views/500.jsp";

  ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");

  default void setErrorMessage(HttpServletRequest request, String title, String message) {
    MessageDTOBuilder mDTOBuilder = MessageDTO.builder();
    mDTOBuilder.withType(MessageDTO.ERROR_TYPE);
    mDTOBuilder.withTitle(title);
    mDTOBuilder.withContent(message);
    request.setAttribute("message", mDTOBuilder.build());
  }

  default void setSuccessMessage(HttpServletRequest request, String title, String message) {
    MessageDTOBuilder mDTOBuilder = MessageDTO.builder();
    mDTOBuilder.withType(MessageDTO.SUCCESS_TYPE);
    mDTOBuilder.withTitle(title);
    mDTOBuilder.withContent(message);
    request.setAttribute("message", mDTOBuilder.build());
  }

  default CompanyService getCompanyService() {
    return (CompanyService) CONTEXT.getBean("CompanyService");
  }

  default ComputerService getComputerService() {
    return (ComputerService) CONTEXT.getBean("ComputerService");
  }

}

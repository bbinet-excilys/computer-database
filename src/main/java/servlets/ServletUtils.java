package servlets;

import javax.servlet.http.HttpServletRequest;

import dto.MessageDTO;
import dto.MessageDTO.MessageDTOBuilder;

public class ServletUtils {

  public static void setErrorMessage(HttpServletRequest request, String title, String message) {
    MessageDTOBuilder mDTOBuilder = MessageDTO.builder();
    mDTOBuilder.withType(MessageDTO.ERROR_TYPE);
    mDTOBuilder.withTitle(title);
    mDTOBuilder.withContent(message);
    request.setAttribute("message", mDTOBuilder.build());
  }

  public static void setSuccessMessage(HttpServletRequest request, String title, String message) {
    MessageDTOBuilder mDTOBuilder = MessageDTO.builder();
    mDTOBuilder.withType(MessageDTO.SUCCESS_TYPE);
    mDTOBuilder.withTitle(title);
    mDTOBuilder.withContent(message);
    request.setAttribute("message", mDTOBuilder.build());
  }

}

package dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageDTO {

  public final static String ERROR_TYPE   = "danger";
  public final static String SUCCESS_TYPE = "success";

  private String type;
  private String title;
  private String content;
  Logger         logger = LoggerFactory.getLogger(MessageDTO.class);

  public MessageDTO(String type, String title, String content) {
    super();
    this.type    = type;
    this.title   = title;
    this.content = content;
  }

  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

  public String getType() {
    return this.type;
  }

  public String toJSON() {
    ObjectMapper om = new ObjectMapper();
    try {
      return om.writeValueAsString(this);
    }
    catch (JsonProcessingException e) {
      this.logger.error("Error converting object to JSON :" + e.getMessage());
      e.printStackTrace();
    }
    return "";
  }

  public static class MessageDTOBuilder {
    private String type;
    private String title;
    private String content;

    public void setType(String type) {
      this.type = type;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public MessageDTO build() {
      return new MessageDTO(this.type, this.title, this.content);
    }
  }
}

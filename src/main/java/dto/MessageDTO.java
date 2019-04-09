package dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageDTO {

  public final static String ERROR_TYPE   = "danger";
  public final static String SUCCESS_TYPE = "success";

  private String type;
  private String title;
  private String content;
  Logger         logger = LoggerFactory.getLogger(MessageDTO.class);

  private MessageDTO(MessageDTOBuilder builder) {
    this.type    = builder.type;
    this.title   = builder.title;
    this.content = builder.content;
    this.logger  = builder.logger;
  }

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

  public static MessageDTOBuilder builder() {
    return new MessageDTOBuilder();
  }

  public static final class MessageDTOBuilder {
    private String type;
    private String title;
    private String content;
    private Logger logger;

    private MessageDTOBuilder() {}

    public MessageDTOBuilder withType(String type) {
      this.type = type;
      return this;
    }

    public MessageDTOBuilder withTitle(String title) {
      this.title = title;
      return this;
    }

    public MessageDTOBuilder withContent(String content) {
      this.content = content;
      return this;
    }

    public MessageDTOBuilder withLogger(Logger logger) {
      this.logger = logger;
      return this;
    }

    public MessageDTO build() {
      return new MessageDTO(this);
    }
  }

}

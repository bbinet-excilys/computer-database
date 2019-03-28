package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesNotFoundException extends Exception {

  private Logger logger = LoggerFactory.getLogger(PropertiesNotFoundException.class);

  public PropertiesNotFoundException(String message) {
    super(message);
    this.logger.error(message);
  }

}
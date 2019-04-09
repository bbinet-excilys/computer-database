package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyComputerNameException extends Exception {

  private static final long serialVersionUID = 1L;
  private Logger            logger           = LoggerFactory.getLogger(EmptyComputerNameException.class);

  public EmptyComputerNameException(String message) {
    super(message);
    this.logger.error(message);
  }
}

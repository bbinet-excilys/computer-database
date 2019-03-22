package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOUnexecutedQuery extends Exception {
  Logger logger = LoggerFactory.getLogger("exception");

  public DAOUnexecutedQuery(String message, Throwable cause) {
    super(message, cause);
    this.logger.error("{} {}", message, cause.getMessage());
  }
}

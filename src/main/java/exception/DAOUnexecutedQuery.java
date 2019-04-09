package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOUnexecutedQuery extends Exception {

  private static final long serialVersionUID = 1L;
  private Logger            logger           = LoggerFactory.getLogger(DAOUnexecutedQuery.class);

  public DAOUnexecutedQuery(String message, Throwable cause) {
    super(message, cause);
    this.logger.error("{} {}", message, cause.getMessage());
  }

}

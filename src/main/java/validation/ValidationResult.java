package validation;

public class ValidationResult {

  private boolean valid;
  private String  message;

  private ValidationResult(boolean valid, String message) {
    this.valid   = valid;
    this.message = message;
  }

  public static ValidationResult ok() {
    return new ValidationResult(true, "Valid");
  }

  public static ValidationResult fail(String message) {
    return new ValidationResult(false, message);
  }

  boolean isValid() {
    return this.valid;
  }

  public String getMessage() {
    return this.message;
  }

  public void throwIfInvalid(String attribute) {
    if (!isValid()) {
      throw new IllegalArgumentException(attribute + " : " + getMessage());
    }
  }

}

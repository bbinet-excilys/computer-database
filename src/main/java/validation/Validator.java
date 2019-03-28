package validation;

import java.util.function.Predicate;

public class Validator<K> implements IValidator<K> {

  private Predicate<K> predicate;
  private String       errorMessage;

  public Validator(Predicate<K> predicate, String errorMessage) {
    this.predicate    = predicate;
    this.errorMessage = errorMessage;
  }

  public static <K> Validator<K> from(Predicate<K> predicate, String errorMessage) {
    return new Validator<>(predicate, errorMessage);
  }

  @Override
  public ValidationResult test(K parameter) {
    return this.predicate.test(parameter) ? ValidationResult.ok()
        : ValidationResult.fail(this.errorMessage);
  }

}

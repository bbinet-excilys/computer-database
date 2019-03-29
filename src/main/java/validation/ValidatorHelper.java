package validation;

import java.sql.Date;

import org.apache.commons.validator.routines.DateValidator;

public class ValidatorHelper {

  private static DateValidator dValidator = new DateValidator();

  public static IValidator<String> strNotNull = Validator.from(str -> str != null,
                                                               "must not be null");

  public static IValidator<String> notEmptyOrBlank() {
    return Validator.from((str) -> !str.isBlank(),
                          "must not be blank.");
  }

  public static IValidator<String> stringShorter(int length) {
    return Validator.from((str) -> str.length() < length,
                          String.format("Must be shorter than %s", length));
  }

  public static IValidator<Long> longNotNull = Validator.from((value) -> value != null,
                                                              "Must not be null");

  public static IValidator<Long> lower(long compared) {
    return Validator.from((value) -> value < compared,
                          String.format("Must be lower than %s.", compared));
  }

  public static IValidator<Long> greaterEqual(long compared) {
    return Validator.from((value) -> value >= compared,
                          String.format("Must be greater than %s.", compared));
  }

  public static IValidator<Date> datesAreValid(Date discontinued) {
    return Validator.from((introduced) -> {
      if (discontinued != null && introduced != null) {
        return introduced.before(discontinued);
      }
      else if (discontinued != null && introduced == null) {
        return false;
      }
      else {
        return true;
      }
    }, "If discontinued is set, then introduced must be before.");
  }

}

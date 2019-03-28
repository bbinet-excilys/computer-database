package validation;

import java.sql.Date;

import org.apache.commons.validator.routines.DateValidator;

public class ValidatorHelper {

  private static DateValidator dValidator = new DateValidator();

  public static IValidator<String> notEmptyOrNullString() {
    return Validator.from((str) -> !("".equals(str.trim())),
                          "Must not be null or blank.");
  }

  public static IValidator<String> stringShorterThan(int length) {
    return Validator.from((str) -> str.length() < length,
                          String.format("Must be shorter than %s", length));
  }

  public static IValidator<Long> notNull() {
    return Validator.from((value) -> value != null, "Must not be null");
  }

  public static IValidator<Long> greaterThan(long compared) {
    return Validator.from((value) -> value > compared,
                          String.format("Must be greater than %s.", compared));
  }

  public static IValidator<Long> greaterEqual(long compared) {
    return Validator.from((value) -> value >= compared,
                          String.format("Must be greater than %s.", compared));
  }

  public static IValidator<Long> lowerThan(long compared) {
    return Validator.from((value) -> value < compared,
                          String.format("Must be lower than %s.", compared));
  }

  public static IValidator<Date> before(Date compared) {
    return Validator.from((date) -> date.before(compared),
                          String.format("Must be before %s.", compared.toString()));
  }

  public static IValidator<String> isISODate() {
    return Validator.from((str) -> {
      return dValidator.isValid(str, "yyyy-MM-dd");
    }, "Must be an ISO date (yyyy-mm-dd)");
  }

  public static IValidator<String> isBeforeISODate(String isoCompared) {
    return Validator.from((str) -> {
      if (isISODate().test(str).isValid() && isISODate().test(isoCompared).isValid()) {
        Date paramDate    = new Date(dValidator.validate(str, "yyyy-MM-dd").getTime());
        Date comparedDate = new Date(dValidator.validate(isoCompared, "yyyy-MM-dd").getTime());
        return paramDate.before(comparedDate);
      }
      else {
        return false;
      }
    }, String.format("Must be before %s", isoCompared));
  }

  public static IValidator<Date> datesAreValid(Date discontinued) {
    return Validator.from((introduced) -> {
      if (discontinued != null) {
        if (introduced != null) {
          return introduced.before(discontinued);
        }
        else {
          return false;
        }
      }
      return true;
    }, "If discontinued is set, then introduced must be before.");
  }

}

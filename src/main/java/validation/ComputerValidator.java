package validation;

import static validation.ValidatorHelper.datesAreValid;
import static validation.ValidatorHelper.greaterEqual;
import static validation.ValidatorHelper.longNotNull;
import static validation.ValidatorHelper.lower;
import static validation.ValidatorHelper.notEmptyOrBlank;
import static validation.ValidatorHelper.strNotNull;
import static validation.ValidatorHelper.stringShorter;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.validator.routines.DateValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dto.ComputerDTO;
import model.Computer;

public class ComputerValidator implements Validator {

  private DateValidator dateValidator = DateValidator.getInstance();
  private String        DATE_PATTERN  = "yyyy-MM-dd";

  public static void computerIsValid(Computer computer) {
    strNotNull.and(notEmptyOrBlank())
              .and(stringShorter(255))
              .test(computer.getName())
              .throwIfInvalid("name");

    datesAreValid(computer.getDiscontinued()).test(computer.getIntroduced())
                                             .throwIfInvalid("Introduced and Discontinued");
  }

  public static void computerWithIdIsValid(Computer computer) {
    longNotNull.and(greaterEqual(0L))
               .and(lower(Long.MAX_VALUE))
               .test(computer.getId())
               .throwIfInvalid("id");

    strNotNull.and(notEmptyOrBlank())
              .and(stringShorter(255))
              .test(computer.getName())
              .throwIfInvalid("name");

    datesAreValid(computer.getDiscontinued()).test(computer.getIntroduced())
                                             .throwIfInvalid("Introduced and Discontinued");
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return this.getClass().equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ComputerDTO      targetDTO = (ComputerDTO) target;
    Optional<String> name      = Optional.ofNullable(targetDTO.getName());
    name.ifPresentOrElse(nameString -> {
      if (nameString.trim().isBlank()) {
        errors.rejectValue("name", "error.label.name.empty");
      }
    }, () -> {
      errors.rejectValue("name", "error.label.name.empty");
    });
    Optional<Date> oIntroduced   = Optional.ofNullable(dateValidator.validate(targetDTO.getIntroduced(), DATE_PATTERN));
    Optional<Date> oDiscontinued = Optional.ofNullable(dateValidator.validate(targetDTO.getDiscontinued(), DATE_PATTERN));
    oDiscontinued.ifPresent(discontinuedDate -> {
      oIntroduced.ifPresentOrElse(introducedDate -> {
        if (discontinuedDate.before(introducedDate)) {
          errors.rejectValue("introduction", "error.label.date.precedence");
        }
      }, () -> {
        errors.reject("introduction", "error.label.date.bothexist");
      });
    });
  }

}

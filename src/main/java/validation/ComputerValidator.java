package validation;

import static validation.ValidatorHelper.datesAreValid;
import static validation.ValidatorHelper.greaterEqual;
import static validation.ValidatorHelper.longNotNull;
import static validation.ValidatorHelper.lower;
import static validation.ValidatorHelper.notEmptyOrBlank;
import static validation.ValidatorHelper.strNotNull;
import static validation.ValidatorHelper.stringShorter;

import model.Computer;

public class ComputerValidator {

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

}
